package splitter;

import java.time.LocalDate;

public class Operation {
    private LocalDate localDate;
    private Person partner;
    private int amount;
    private Type type;

    public Operation(LocalDate localDate, Type type, Person partner, int amount) {
        this.localDate = localDate;
        this.partner = partner;
        this.amount = amount;
        this.type = type;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public int getAmount() {
        return amount;
    }

    public Type getType() {
        return type;
    }

    public Person getPartner() {
        return partner;
    }
}