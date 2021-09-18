package org.aigiri.account;

import java.util.HashSet;
import java.util.Set;

public class AccountManager {

    Set<Account> accountSet = new HashSet();

    public boolean addAccount( Account account ){
        validateAccount( account );
        if( accountSet.contains(account) ){
            throw new RuntimeException("account already exists");
        }
        return accountSet.add(account);
    }

    public boolean addAccount( String name, String phone, int age, String address ){
        Account account = new Account( name, phone, address, age);
        return this.addAccount( account );
    }

    public Account getAccountByPhone( String phone ){
        return accountSet.stream().filter( a -> a.getPhone().equals( phone ) ).findAny().orElse( new Account() );
    }

    public boolean checkIfAccountExists( Account account ){
        return accountSet.contains( account );
    }

    public void validateAccount( Account account ){
        account.validateName();
        account.validateAge();
        account.validatephone();
    }

}
