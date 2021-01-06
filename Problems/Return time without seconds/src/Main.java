import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CharSequence text = scanner.nextLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        if (text.length() > 5) {
            formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        }

        LocalTime localTime = LocalTime.parse(text, formatter);
        System.out.println(localTime.withSecond(0));
        // put your code here
    }
}