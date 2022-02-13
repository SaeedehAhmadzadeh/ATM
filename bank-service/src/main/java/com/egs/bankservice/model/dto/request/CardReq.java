package com.egs.bankservice.model.dto.request;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CardReq {
    @NotBlank(message = "cardNumber cannot be empty")
    @Size(min = 6, max = 6 ,message = "cardNumber must be  6 characters")
    private String cardNumber;
}
