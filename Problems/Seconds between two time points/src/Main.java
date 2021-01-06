import java.time.LocalTime;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CharSequence text1  = scanner.nextLine();
        CharSequence text2 = scanner.nextLine();
        LocalTime localTime1 = LocalTime.parse(text1);
        LocalTime localTime2 = LocalTime.parse(text2);
        int dif = localTime1.toSecondOfDay() - localTime2.toSecondOfDay();
        System.out.println(Math.abs(dif));
        // write your code here
    }
}