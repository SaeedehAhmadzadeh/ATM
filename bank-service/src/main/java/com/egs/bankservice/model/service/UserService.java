package com.egs.bankservice.model.service;


import com.egs.bankservice.component.Global;
import com.egs.bankservice.exception.BadRequestException;
import com.egs.bankservice.exception.NotFoundException;
import com.egs.bankservice.exception.UnauthorizedException;
import com.egs.bankservice.model.dto.CashRes;
import com.egs.bankservice.model.dto.request.*;
import com.egs.bankservice.model.dto.response.CardRes;
import com.egs.bankservice.model.entity.EgsUser;
import com.egs.bankservice.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.egs.bankservice.common.Constants.MAX_TRY_COUNT;
import static com.egs.bankservice.common.ErrorMessages.*;


@Service
public class UserService {
    private final UserRepository userRepository;
    private final AuthenticationFactory authenticationFactory;

    @Autowired
    public UserService(UserRepository userRepository, AuthenticationFactory authenticationFactory) {
        this.userRepository = userRepository;
        this.authenticationFactory = authenticationFactory;
    }

    public EgsUser registerUser(RegisterUserReq userDto) {
        EgsUser user = authenticationFactory.getAuthenticationProvider(userDto.getPinType()).register();
        user = userRepository.save(user);
        return user;
    }

    public EgsUser login(LoginReq loginReq) {
        EgsUser user = findByCardNumber(loginReq.getCardNumber());
        checkUserIsBlocked(user);
        pinValidate(loginReq, user);
        return user;
    }

    private void checkUserIsBlocked(EgsUser user) {
        if (user.getBlocked())
            throw new UnauthorizedException(USER_IS_BLOCKED);

    }

    private EgsUser findByCardNumber(String loginReq) {
        Optional<EgsUser> optional = userRepository.findByCardNumber(loginReq);
        if (!optional.isPresent())
            throw new NotFoundException(NOT_FOUND);
        return optional.get();
    }

    private void pinValidate(LoginReq loginReq, EgsUser user) {
        if (!authenticationFactory.getAuthenticationProvider(user.getPinType()).pinValidate(user, loginReq)) {
            this.addTryCount(user);
            throw new BadRequestException(INVALID_PIN);
        }
    }

    private void addTryCount(EgsUser user) {
        user.setTryCount(user.getTryCount() + 1);
        if (user.getTryCount() >= MAX_TRY_COUNT) {
            user.setBlocked(true);
        }
        userRepository.save(user);
    }

    public CardRes checkCardNumber(CardReq cardReq) {
        EgsUser user = findByCardNumber(cardReq.getCardNumber());
        checkUserIsBlocked(user);
        return new CardRes(user.getPinType(), user.getCardNumber());

    }

    public CashRes balance() {
        EgsUser user = findByUserName();
        return new CashRes(user.getCash());

    }

    public void cashWithdrawal(CashWithdrawalReq cashWithdrawalReq) {
        EgsUser user = findByUserName();
        long balance = user.getCash() - cashWithdrawalReq.getCash();
        if (balance < 0)
            throw new BadRequestException(DONT_HAVE_MONEY);
        user.setCash(balance);
        userRepository.save(user);
    }

    private EgsUser findByUserName() {
        EgsUser userInfo = Global.getUserInfo();
        Optional<EgsUser> byUsername = userRepository.findByUsername(userInfo.getUsername());
        if (!byUsername.isPresent())
            throw new NotFoundException(USER_NOT_FOUND);
        return byUsername.get();
    }

    public void cashDeposit(CashDepositReq cashDepositReq) {
        EgsUser user = findByUserName();
        long balance = user.getCash() + cashDepositReq.getCash();
        user.setCash(balance);
        userRepository.save(user);
    }
}
