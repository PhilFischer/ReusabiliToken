package com.example.philipp.cashierapp.Features.operations.actions;

import com.example.philipp.cashierapp.IAddress;

/**
 * Created by Tobias on 15.02.2018.
 */

/**
 * This class represents an action that is confirmed by a human in a store or restaurant, ...
 */
public abstract class AHumanConfirmableAction implements IAction {

    protected String description;
    protected IAddress customer_address;
    protected long store_id;
    protected int reward;

    public AHumanConfirmableAction() {
        // Nothing to do here
    }

    public AHumanConfirmableAction(String description) {
        this.description = description;
    }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public IAddress getCustomerAddress() { return customer_address; }
    public void setCustomerAddress(IAddress address) { customer_address = address; }
    public int getReward() { return reward; }
    public void setReward(int reward) { this.reward = reward; }
    public long getStoreID() { return store_id; }
    public void setStoreID(long id) { store_id = id; }

}
