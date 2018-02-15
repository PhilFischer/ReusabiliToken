package com.example.philipp.cashierapp;

/**
 * Created by Tobias on 14.02.2018.
 */

        import java.security.PublicKey;
        import java.util.HashMap;

/**
 * @brief A Database containing the IDs and public keys of all the stores
 *
 * This is just here for debugging purposes
 */
public class StoreDatabase {

    private static StoreDatabase instance = new StoreDatabase();
    private HashMap<Long, PublicKey> id_to_pubkey = new HashMap<>();

    private StoreDatabase() {
        // Nothing to do here
    }

    public static StoreDatabase getInstance() {
        return instance;
    }

    public PublicKey getPublicKey(long store_id) {
        return id_to_pubkey.get(new Long(store_id));
    }

    public void addStore(long store_id, PublicKey public_key) {
        id_to_pubkey.put(new Long(store_id), public_key);
    }

}
