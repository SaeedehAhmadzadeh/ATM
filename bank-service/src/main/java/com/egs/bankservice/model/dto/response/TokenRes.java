package com.egs.bankservice.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TokenRes {
    private String access_token;
    private String refresh_token;
}
