import java.math.BigInteger;

public class Main {



    public static void main(String[] args) {
//        int counter = 0;
//        Secret[] enums = Secret.values();
//        for (Secret emun : enums) {
//            if (emun.toString().startsWith("STAR")) {
//                counter++;
//            }
//        }
//        System.out.println(counter);


        BigInteger current = BigInteger.ZERO;
        current.add(BigInteger.valueOf(1000));
        current.subtract(BigInteger.valueOf(10));
        BigInteger result = current.add(BigInteger.ONE);
        System.out.println(result);
    }
}


