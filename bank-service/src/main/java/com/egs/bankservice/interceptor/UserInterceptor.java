package com.egs.bankservice.interceptor;

import com.egs.bankservice.component.Global;
import com.egs.bankservice.exception.UnauthorizedException;
import com.egs.bankservice.model.entity.EgsUser;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.egs.bankservice.common.ErrorMessages.USER_IS_BLOCKED;

@Component
public class UserInterceptor implements AsyncHandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        checkUserIsBLocked();
        return true;
    }

    private void checkUserIsBLocked() {
        if (Global.isAnonymousUser())
            return;
        EgsUser userInfo = Global.getUserInfo();
        if (userInfo.getBlocked())
            throw new UnauthorizedException(USER_IS_BLOCKED);
    }
}
