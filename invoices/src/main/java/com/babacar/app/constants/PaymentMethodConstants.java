package com.babacar.app.constants;

import java.util.Set;

public class PaymentMethodConstants {
    private PaymentMethodConstants(){}

    public static final String VISA="VISA";
    public static final String ESPECE="ESPECE";
    public static final String WAVE="WAVE";
    public static final String OM="OM";
    public static final Set<String> VALID_PAYMENTS=Set.of(VISA,ESPECE,WAVE,OM);
}
