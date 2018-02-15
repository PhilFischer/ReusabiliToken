package com.example.philipp.cashierapp;

import com.example.philipp.cashierapp.IAddress;

/**
 * Created by Tobias on 15.02.2018.
 */

public class Address implements IAddress {

    String address;

    public Address() {
        address = "0x0000000000000000000000000000000000000000";
    }

    public Address(String address) {
        this.address = address;
    }

    @Override
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
