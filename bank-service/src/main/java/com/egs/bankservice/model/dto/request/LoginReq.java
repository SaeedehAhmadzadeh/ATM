package com.egs.bankservice.model.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class LoginReq {
    @NotBlank(message = "Pin cannot be empty")
    @Size(min = 4, max = 4, message = "Pin must be  6 characters")
    private String pin;
    @NotBlank(message = "CardNumber cannot be empty")
    @Size(min = 6, max = 6, message = "CardNumber must be  6 characters")
    private String cardNumber;
}
