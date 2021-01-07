package splitter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Account {
    private int id = idGenerator(5);
    private int money;
    private List<Operation> operations = new ArrayList<>();

    public Account(int money) {
        this.money = money;
    }

    public void addOperation(Operation operation) {
        operations.add(operation);
    }

    public int getId() {
        return id;
    }

    public List<Operation> getOperationsOnDate(LocalDate localDate) {
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
