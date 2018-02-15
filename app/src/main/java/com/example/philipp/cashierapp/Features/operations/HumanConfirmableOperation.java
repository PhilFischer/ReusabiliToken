package com.example.philipp.cashierapp.Features.operations;

import java.security.PublicKey;
import java.security.Signature;
import java.util.ArrayList;
import java.util.List;

import com.example.philipp.cashierapp.Features.operations.actions.AHumanConfirmableAction;
import com.example.philipp.cashierapp.Features.operations.actions.BringOwnCupAction;
import com.example.philipp.cashierapp.Features.operations.actions.BringOwnPlateAction;
import com.example.philipp.cashierapp.Features.operations.actions.EActionType;
import com.example.philipp.cashierapp.Features.operations.actions.IAction;
import com.example.philipp.cashierapp.Repository.IRepository;
import com.example.philipp.cashierapp.Token.IToken;
import com.example.philipp.cashierapp.StoreDatabase;
import com.example.philipp.cashierapp.Features.operations.AOperation;

/**
 * Created by Tobias on 14.02.2018.
 */

public class HumanConfirmableOperation extends AOperation {

    public HumanConfirmableOperation(IRepository repo) {
        super(repo);
    }

    @Override
    public List<IAction> getActions() {
        List<IAction> returnList = new ArrayList();
        returnList.add(new BringOwnCupAction());
        returnList.add(new BringOwnPlateAction());
        return returnList;
    }

    @Override
    public IAction getNextAction() {
        return new BringOwnPlateAction();
    }

    @Override
    public boolean write(IToken token, IClaim claim) {
        // Verify whether the claim is of the right type
        if(claim.getAction()==null)
            return false;
        if(!(claim.getAction() instanceof AHumanConfirmableAction))
            return false;
        AHumanConfirmableAction action = (AHumanConfirmableAction) claim.getAction();
        // Verify the proof for the claim
        boolean valid;
        try {
            // Currently we know the action is an instance of BringOwnCupAction
            // This may and will change in the future, so a distinction between those has to be mad
            valid = verifyClaim(claim,
                                StoreDatabase.getInstance().getPublicKey(action.getStoreID()),
                                "DSA"); // Just use DSA for the moment
        }
        catch (Exception e) {
            System.out.print(e.getMessage());
            return false;
        }
        if (!valid)
            return false;

        // Give the reward
        if (repo.store(claim.getAction(), null /*claim.getProof()*/)) {
            // Give the reward
            token.generate(action.getCustomerAddress(), action.getReward());
            // Give reputation to the customer
            StoreDatabase.getInstance().getReputationToken(action.getStoreID()).generate(action.getCustomerAddress(), 1);
            return true;
        }
        return false;
    }

    @Override
    public void resetOperation() {
        // Should be right to leave this empty
    }

    @Override
    public boolean isSet() {
        //TODO: Implement
        return false;
    }

    /**
     * @brief Verifying a given claim with its proof
     *
     * The proof for the claim should be a digital signature
     * on the hash of the action object.
     * The Signature object is updated with the least significant byte first
     *
     * @param claim The claim object to verify.
     * @param public_key The public key of the instance that signed the data
     * @param signature_algorithm The signature algorithm to be used
     * @return Whether the proof is valid or not
     */
    private boolean verifyClaim(IClaim claim,
                                PublicKey public_key,
                                String signature_algorithm) throws Exception {
        int hash = claim.getAction().hashCode();
        Signature signature = Signature.getInstance(signature_algorithm);
        signature.initVerify(public_key);
        for (int i = 0 ; i < 4 ; ++i) {
            signature.update((byte)(hash >> 8*i));
        }
        if (!(claim.getProof() instanceof HumanConfirmableActionProof))
            return false;
        HumanConfirmableActionProof proof = (HumanConfirmableActionProof)claim.getProof();
        return signature.verify(proof.getSignatureBytes());
    }

}
