package com.babacar.app.constants;

import java.util.Set;

public class InvoiceStatus {
    private InvoiceStatus(){}

    public static final String DRAFT="DRAFT";
    public static final String PAID="PAID";
    public static final String UNPAID="UNPAID";
    public static final Set<String> VALID_STATUS=Set.of(DRAFT,PAID,UNPAID);
}
