package org.aigiri.account;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

public class AccountManagerTest {

    AccountManager accountManager;

    @BeforeAll
    public static void beforeAll(){
        System.out.println("before all - booting up");
    }

    @BeforeEach
    public void setup(){
        accountManager = new AccountManager();
        System.out.println("initing account manager: " + accountManager.hashCode());
    }

    @Test
    @DisplayName("account addition happy path")
    @Disabled
    public void addAccount(){
        Account account = new Account("abc", "0987654321", "some address", 21);
        accountManager.addAccount("abc", "0987654321", 21, "some address");
        Assertions.assertEquals( accountManager.checkIfAccountExists(accountManager.getAccountByPhone("0987654321")), true  );
    }

    @Test
    @DisplayName("account addition phone exists")
    public void addAccountWithPhone(){
        Account account = new Account("abc", "0987654321", "some address", 21);
        accountManager.addAccount("abc", "0987654321", 21, "some address");
        //Assertions.assertEquals( accountManager.checkIfAccountExists( account ), true  );
        Account accountByPhone = accountManager.getAccountByPhone("0987654321");
        Assertions.assertEquals( accountByPhone.getName(), account.getName());
        Assertions.assertEquals( accountByPhone.getAge(), account.getAge());
        Assertions.assertEquals( accountByPhone.getPhone(), account.getPhone());
        Assertions.assertEquals( accountByPhone.getAddress(), account.getAddress());

    }

    @Test
    @DisplayName("account addition name does not exists")
    @EnabledOnOs( {OS.MAC, OS.LINUX} )
    public void emptyNameException(){
        Assertions.assertThrows( RuntimeException.class,
                () -> accountManager.addAccount("", "0987654321", 21, "some address") );
    }

    @Nested
    class ParametrizedTestsCollection {

        @ParameterizedTest
        @ValueSource( strings = {"0987654321", "0123456789", "0918273643"} )
        public void getParamsFromValueSource(String phone){
            Account account = new Account("abc", phone, "some address", 21);
            accountManager.addAccount( "abc", phone, 21, "some address");
            Account phoneAccount = accountManager.getAccountByPhone( phone );
            Assertions.assertEquals( phoneAccount.getName(), account.getName() );
            Assertions.assertNotEquals( phoneAccount.getPhone(), "0" );
        }

        @ParameterizedTest
        @CsvFileSource( resources = "/data.csv")
        public void getDataFromCsv(String name, String phone, String address, int age){
            accountManager.addAccount( name, phone, age, address );
            Account phoneAccount = accountManager.getAccountByPhone( phone );
            Assertions.assertEquals( phoneAccount.getName(), name );
            Assertions.assertEquals( phoneAccount.getPhone(), phone );
        }


    }

    @ParameterizedTest
    @MethodSource("dummyAccountList")
    public void getParamsFromValueSource(Account account){
        accountManager.addAccount( account );
        Account phoneAccount = accountManager.getAccountByPhone( account.getPhone() );
        Assertions.assertEquals( phoneAccount.getName(), account.getName() );
        Assertions.assertEquals( phoneAccount.getPhone(), account.getPhone() );
    }

    public static Account[] dummyAccountList(){
        Account[] accounts = new Account[]{
                new Account("abc", "0987654321", "some address 1", 21),
                new Account("pqr", "0987654332", "some address 2", 22),
                new Account("abc", "0987654343", "some address 3", 23),
        };
        return accounts;
    }


    @AfterEach
    public void tearingSetup(){
        System.out.println("tearing account manager:" + accountManager.hashCode() );
    }

    @AfterAll
    public static void afterAll(){
        System.out.println("after all - closing down");
    }

}
