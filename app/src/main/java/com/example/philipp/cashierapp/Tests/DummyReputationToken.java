package com.example.philipp.cashierapp.Tests;

import java.util.Collection;
import java.util.Map;

import com.example.philipp.cashierapp.Features.operations.IClaim;
import com.example.philipp.cashierapp.Token.IToken;
import com.example.philipp.cashierapp.IAddress;

/**
 * Created by Tobias on 15.02.2018.
 */

public class DummyReputationToken implements IToken {
    @Override
    public long getUniqueID() {
        return 1;
    }

    @Override
    public String getName() {
        return "Dummy Reputation Token";
    }

    @Override
    public int getDecimals() {
        return 10;
    }

    @Override
    public String getSymbol() {
        return "DRT";
    }

    @Override
    public int getTotalSupply() {
        return -1;
    }

    @Override
    public boolean isCapped() {
        return false;
    }

    @Override
    public boolean preMined() {
        return false;
    }

    @Override
    public int preMinedAmount() {
        return 0;
    }

    @Override
    public int cappedAmount() {
        return 0;
    }

    @Override
    public int getBalanceOf(IAddress addr) {
        return 0;
    }

    @Override
    public Map<IAddress, Integer> getAllBalances() {
        return null;
    }

    @Override
    public boolean transfer(IAddress from, IAddress to, int value) {
        System.out.print("Moved ");
        System.out.print(value);
        System.out.print(getSymbol());
        System.out.print(" from address ");
        System.out.print(from.getAddress());
        System.out.print(" to address ");
        System.out.println(to);
        return true;
    }

    @Override
    public boolean generate(IAddress address, int value) {
        System.out.print("Generated ");
        System.out.print(value);
        System.out.print(getSymbol());
        System.out.print(" to address ");
        System.out.println(address.getAddress());
        return true;
    }

    @Override
    public Collection getUnderlyings() {
        return null;
    }

    @Override
    public Collection getProperties() {
        return null;
    }

    @Override
    public Collection getOperations() {
        return null;
    }

    @Override
    public boolean addClaim(IClaim claim) {
        return false;
    }
}
