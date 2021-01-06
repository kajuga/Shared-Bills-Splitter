import java.time.LocalTime;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int input = s.nextInt();
        LocalTime time = LocalTime.ofSecondOfDay(input);
        System.out.println(time);
    }
}