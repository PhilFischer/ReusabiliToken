package com.example.philipp.cashierapp.Features.operations.actions;

import com.example.philipp.cashierapp.IAddress;

/**
 * Created by Tobias on 14.02.2018.
 */

public class BringOwnCupAction extends AHumanConfirmableAction {

    public BringOwnCupAction() {
        super("Bring your own cup to a coffee shop.");
        this.reward = 1;
    }

    public BringOwnCupAction(IAddress customer_address, long store_id) {
        super("Bring your own cup to a coffee shop.");
        this.customer_address = customer_address;
        this.store_id = store_id;
        this.reward = 1;    // The reward for bringing the own cup should be 1 coin
    }

    @Override
    public EActionType getType() { return EActionType.BRING_OWN_CUP; }

}
