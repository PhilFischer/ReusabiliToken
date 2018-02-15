package com.example.philipp.cashierapp.Features.operations.actions;

import com.example.philipp.cashierapp.IAddress;

/**
 * Created by Tobias on 15.02.2018.
 */

public class BringOwnPlateAction extends AHumanConfirmableAction {

    public BringOwnPlateAction() {
        super("Bring your own plate instead of using a disposable one.");
        this.reward = 2;
    }

    public BringOwnPlateAction(IAddress customer_address, long store_id) {
        super("Bring your own plate instead of using a disposable one.");
        this.customer_address = customer_address;
        this.store_id = store_id;
        this.reward = 2;    // The reward for bringing the own cup should be 1 coin
    }

    @Override
    public EActionType getType() { return EActionType.BRING_OWN_PLATE; }

}
