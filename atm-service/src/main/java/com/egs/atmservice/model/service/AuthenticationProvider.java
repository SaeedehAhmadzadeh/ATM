package com.egs.atmservice.model.service;

import com.egs.atmservice.model.dto.request.LoginReq;
import com.egs.atmservice.model.dto.response.LoginRes;

public interface AuthenticationProvider {
     LoginRes authenticate(LoginReq loginReq);
}
