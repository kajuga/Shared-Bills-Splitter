// declare your enum here

public class Main {

    public enum Zodiac {
        ARIES,
        TAURUS,
        GEMINI,
        CANCER,
        LEO,
        VIRGO,
        LIBRA,
        SCORPIO,
        SAGITTARIUS,
        CAPRICORN,
        AQUARIUS,
        PISCES
    }
    public static void main(String[] args) {

        Zodiac capricorn = Zodiac.CAPRICORN;
        Zodiac leo = Zodiac.LEO;

//        Zodiac taurus = Zodiac.valueOf("TAURUS");
//        Zodiac virgo = Zodiac.valueOf("virgo");
//        System.out.println(leo.equals(Zodiac.TAURUS));
        System.out.println(leo.equals(Zodiac.TAURUS));

    }
}