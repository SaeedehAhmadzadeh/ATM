package com.egs.atmservice.model.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class BankServiceRes {
    private Date timestamp;
    private int status;
    private String error;
    private CardRes message;
    private String path;
}
