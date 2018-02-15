package com.example.philipp.cashierapp.operations;

import com.example.philipp.cashierapp.operations.actions.IAction;
import com.example.philipp.cashierapp.StandardClaim;

/**
 * Created by Tobias on 14.02.2018.
 */

public class HumanConfirmableClaim extends StandardClaim {

    public HumanConfirmableClaim(IAction action, IActionProof proof) {
        super(action, proof);
    }

}
