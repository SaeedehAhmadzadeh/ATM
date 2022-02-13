package com.egs.atmservice.model.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenRes {
    private String access_token;
    private String refresh_token;
}
