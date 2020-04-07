package com.brick.buster.main.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class AmountForm {
    @NotNull
    int amount = 0;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
