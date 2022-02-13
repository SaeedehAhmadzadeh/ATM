package com.egs.bankservice.component;

import com.egs.bankservice.exception.BadRequestException;
import com.egs.bankservice.model.entity.EgsUser;
import com.egs.bankservice.model.entity.security.CustomUserPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import static com.egs.bankservice.common.ErrorMessages.USER_NOT_FOUND;

public class Global {
    public static EgsUser getUserInfo() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof CustomUserPrincipal) {
            return ((CustomUserPrincipal) principal).getUser();
        }
        throw new BadRequestException(USER_NOT_FOUND);
    }

    public static boolean isAnonymousUser(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal.equals("anonymousUser");
    }
}
