import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class TestClass {




    public static void main(String[] args) {
        Clazz instance = new Clazz(10);
//        Clazz instance = Clazz::instanceMethod


        Clazz::instanceMethod

        Clazz::method

        Clazz::staticMethod

        instance::staticMethod

        Clazz::new

        instance::instanceMethod


//        Supplier<String> firstName = customer::getFirstName;
//        List<Customer> customers = new ArrayList<>();
//        customers.stream()
//                .map(c -> customer.getFirstName())
//        .collect(Collectors.joining());






    }
}


class Clazz {
    int magic;

    public Clazz(int magic) {
        this.magic = magic;
    }

    public static int staticMethod(int a) { return a + a; }

    public int instanceMethod(int b) { return b * magic; }
}