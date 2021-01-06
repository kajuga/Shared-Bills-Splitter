package splitter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class Operation {
    private LocalDate localDate;
    private Person from;
    private int amount;
    private Type type;


    public Operation(LocalDate localDate, Type type, Person from, int amount) {
        this.localDate = localDate;
        this.from = from;
        this.amount = amount;
        this.type = type;
    }


    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    public int getAmount() {
        return amount;
    }

    public Type getType() {
        return type;
    }

    public Person getFrom() {
        return from;
    }
}
