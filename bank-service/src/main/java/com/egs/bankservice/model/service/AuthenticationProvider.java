package com.egs.bankservice.model.service;

import com.egs.bankservice.model.dto.request.LoginReq;
import com.egs.bankservice.model.entity.EgsUser;

public interface AuthenticationProvider {
     EgsUser register();
     boolean pinValidate(EgsUser egsUser, LoginReq loginReq);
}
