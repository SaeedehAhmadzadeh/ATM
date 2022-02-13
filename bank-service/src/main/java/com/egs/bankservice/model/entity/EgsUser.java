package com.egs.bankservice.model.entity;

import com.egs.bankservice.enums.PinType;
import com.egs.bankservice.model.dto.response.TokenRes;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class EgsUser implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 1)
    private long id;

    @Column(unique = true)
    @JsonIgnore
    private String username;

    @Column
    @JsonIgnore
    private String password;

    @Column
    private String fingerprint;

    @Column
    private String pin;

    @Column(name = "pin_type")
    private PinType pinType;

    @Column(name = "card_number", unique = true)
    private String cardNumber;

    @Column(name = "cash")
    private Long cash = 0l;

    @Column(name = "try_count")
    private Integer tryCount = 0;

    @Column
    @JsonIgnore
    private Boolean blocked = false;

    @Column
    private Date created;

    @Column
    private Date updated;

    @Transient
    private TokenRes token;
}
