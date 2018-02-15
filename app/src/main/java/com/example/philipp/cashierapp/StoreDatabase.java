package com.example.philipp.cashierapp;

/**
 * Created by Tobias on 14.02.2018.
 */

import java.security.PublicKey;
import java.util.AbstractMap;
import java.util.HashMap;

import com.example.philipp.cashierapp.Token.IToken;

/**
 * @brief A Database containing the IDs and public keys of all the stores
 *
 * This is just here for debugging purposes
 */
public class StoreDatabase {

    private static StoreDatabase instance = new StoreDatabase();
    private HashMap<Long, AbstractMap.SimpleEntry<PublicKey, IToken>> id_to_pubkey_token = new HashMap<>();

    private StoreDatabase() {
        // Nothing to do here
    }

    public static StoreDatabase getInstance() {
        return instance;
    }

    public boolean containsStore(long store_id) {
        return id_to_pubkey_token.containsKey(new Long(store_id));
    }

    public PublicKey getPublicKey(long store_id) {
        if (!id_to_pubkey_token.containsKey(new Long(store_id)))
            return null;
        return id_to_pubkey_token.get(new Long(store_id)).getKey();
    }

    public IToken getReputationToken(long store_id) {
        if (!id_to_pubkey_token.containsKey(new Long(store_id)))
            return null;
        return id_to_pubkey_token.get(new Long(store_id)).getValue();
    }

    public void addStore(long store_id, PublicKey public_key, IToken reputation_token) {
        id_to_pubkey_token.put(new Long(store_id), new AbstractMap.SimpleEntry<>(public_key, reputation_token));
    }

}
