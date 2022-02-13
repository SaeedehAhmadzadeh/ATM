package com.egs.atmservice.model.service.state;

import com.egs.atmservice.model.entity.UserData;
import static com.egs.atmservice.common.Constants.ATM_CASH;

public class GlobalData {
    private static UserData userData ;
    private final static ATMMachine atmMachine = new ATMMachine(ATM_CASH);

    public static ATMMachine getAtmMachine() {
        return atmMachine;
    }

    public static UserData getUserData() {
        return userData;
    }

    public static void setUserData(UserData user) {
        userData = user;
    }
}
