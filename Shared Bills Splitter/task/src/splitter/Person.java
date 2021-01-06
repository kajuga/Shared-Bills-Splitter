package splitter;

import java.util.ArrayList;
import java.util.List;

public class Person {
    private final String name;
    private Account account;

    public Person(String name, Account account) {
        this.name = name;
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }


    //    public void addAccount(Account account) {
//        accounts.add(account);
//    }
}