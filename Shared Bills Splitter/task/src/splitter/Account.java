package splitter;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Account {
    private static SecureRandom rnd = new SecureRandom();
    private int id = idGenerator(5);
    private int money;
    private List<Operation> operations = new ArrayList<>();

    public Account(int money) {
        this.money = money;
    }

    public void addOperation(Operation operation) {
        operations.add(operation);
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getId() {
        return id;
    }

    public List<Operation> getOperationsByIdForCurrentDate (LocalDate localDate) {
        List<Operation> result = new ArrayList<>();
        for (Operation operation : this.operations) {
            if (operation.getLocalDate().isBefore(localDate) || operation.getLocalDate().equals(localDate))
                result.add(operation);
        }
        return result;
    }



    //ID generator
    private static Integer idGenerator(int amount) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < amount; i++) {
            sb.append((int) (Math.random() * amount));
        }
        return Integer.parseInt(sb.toString());
    }
}
