package com.egs.bankservice.model.service;

import com.egs.bankservice.exception.UnauthorizedException;
import com.egs.bankservice.model.entity.EgsUser;
import com.egs.bankservice.model.entity.security.CustomUserPrincipal;
import com.egs.bankservice.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Optional;
import static com.egs.bankservice.common.ErrorMessages.NOT_FOUND;
import static com.egs.bankservice.common.ErrorMessages.UNAUTHORIZED;


@Service("userDetailService")
public class UserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public UserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<EgsUser> optional = userRepository.findByCardNumber(username);
        if (!optional.isPresent())
            throw new UsernameNotFoundException(NOT_FOUND);
        EgsUser user = optional.get();
        if (user.getBlocked())
            throw new UnauthorizedException(UNAUTHORIZED);
        return new CustomUserPrincipal(optional.get());
    }
}
