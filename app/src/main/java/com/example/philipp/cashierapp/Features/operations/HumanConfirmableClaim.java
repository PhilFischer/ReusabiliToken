package com.example.philipp.cashierapp.Features.operations;

import com.example.philipp.cashierapp.Features.operations.actions.IAction;
import com.example.philipp.cashierapp.Features.operations.StandardClaim;

/**
 * Created by Tobias on 14.02.2018.
 */

public class HumanConfirmableClaim extends StandardClaim {

    public HumanConfirmableClaim(IAction action, IActionProof proof) {
        super(action, proof);
    }

}
