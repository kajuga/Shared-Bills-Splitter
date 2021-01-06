package splitter;

import splitter.exceptions.InvalidArgumentException;
import splitter.exceptions.UnknownCommandException;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static java.time.temporal.TemporalAdjusters.firstDayOfMonth;
import static splitter.Type.BORROW;
import static splitter.Type.REPAY;

public class Main {

    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
    static String DATE_FORMAT = "yyyy.MM.dd";
    static DateFormat df = new SimpleDateFormat(DATE_FORMAT);

    static List<Person> personList = new ArrayList<>();

    private static void createPerson(String name){
        Account account = new Account(0);
        Person person = new Person(name, account);
        personList.add(person);
    }

    public static void main(String[] args) throws ParseException, FileNotFoundException {
        createPerson("Ann");
        createPerson("Bob");
        createPerson("Ann");
        createPerson("Chuck");
        createPerson("Diana");
        createPerson("Elon");
        createPerson("Annabelle");
        createPerson("Billibob");
        createPerson("Carlos");
        createPerson("Finny");

//        Scanner scan = new Scanner(new File("/home/kajuga/projects/Shared Bills Splitter/Shared Bills Splitter/task/src/splitter/test.txt"));
        Scanner scan = new Scanner(System.in);
        String line = null;

        while (true) {
            line = scan.nextLine();
            if (line.equals(Commands.EXIT.getKey()) || line.isEmpty()) {
                break;
            }
            try {
                //TODO парсер строки -> [дата операция(дал/взял) клиент1 клиент2 сумма]
                String[] parsed = requestParserNew(line);

                // HELP
                if (parsed.length == 1 && parsed[0].equals(Commands.HELP.getKey())) {
                    System.out.println("balance\n" +
                            "borrow\n" +
                            "exit\n" +
                            "help\n" +
                            "repay");
                    break;
                }

                //TODO  >> 2020.09.30 borrow Ann Bob 20
                //TODO  >> 2020.10.01 repay Ann Bob 10
                if (parsed.length == 5) {
                    LocalDate localDate = LocalDate.parse(parsed[0], formatter);
                    Type operationType = Type.valueOf(parsed[1].toUpperCase());
                    Person firstPerson = findByName(parsed[2]);
                    Person secondPerson = findByName(parsed[3]);
                    int moneyInTransfer = Integer.parseInt(parsed[4]);
                    transactorSynchronizer(localDate, operationType, firstPerson, secondPerson, moneyInTransfer);
                }
                //TODO  >>repay Bob Ann 5
                if (parsed.length == 4) {
                    LocalDate localDate = LocalDate.now();
                    Type operationType = Type.valueOf(parsed[0].toUpperCase());
                    Person firstPerson = findByName(parsed[1]);
                    Person secondPerson = findByName(parsed[2]);
                    int moneyInTransfer = Integer.parseInt(parsed[3]);
                    transactorSynchronizer(localDate, operationType, firstPerson, secondPerson, moneyInTransfer);
                }

                //TODO  >>2020.10.30 balance open - WORKED
                //TODO  >>2020.10.20 balance close - WORKED
                if (parsed.length == 3) {
                    LocalDate localDate = LocalDate.parse(parsed[0], formatter);
                    if (parsed[2].equals("open")) {
                        LocalDate firstDayOfMonth = localDate.withDayOfMonth(1).minusDays(1);
                        System.out.println(balanceCloseChecker(personList, firstDayOfMonth));
//                        System.out.println(balanceTotalCloseChecker(personList, localDate));
                    }
                    if (parsed[2].equals("close")) {
                        System.out.println(balanceCloseChecker(personList, localDate));
//                        System.out.println(balanceTotalCloseChecker(personList, localDate));
                    }
                }

                //TODO 2020.09.25 balance
                //TODO balance close
                if (parsed.length == 2) {
                    if (parsed[0].equals("balance") & parsed[1].equals("close")) {
                        LocalDate localDate = LocalDate.now();
                        System.out.println(balanceCloseChecker(personList, localDate));
//                        System.out.println(balanceTotalCloseChecker(personList, localDate));
                        break;
                    } else if (isDateValid(parsed[0]) & parsed[1].equals("balance")) {
                        LocalDate localDate = LocalDate.parse(parsed[0], formatter);
                        System.out.println(balanceCloseChecker(personList, localDate));
                    }

                }

            } catch (InvalidArgumentException e) {
                System.out.println("Illegal command arguments");
                break;
            } catch (UnknownCommandException e) {
                System.out.println("Unknown command. Print help to show commands list");
                break;
            }
        }


    }


    //TODO подсчет [DATE balance close] до УКАЗАННОЙ ДАТЫ ()
    static String balanceCloseChecker(List<Person> personList, LocalDate date) {
        String result = "No repayments need";

        Map<String, Map<String, Integer>> info = new TreeMap<>();
        for (Person person : personList) {
            Map<String, Integer> balance = countBalanceForPerson(person, date);
            if (!balance.isEmpty()) {
                info.put(person.getName(), balance);
            }
        }

        StringBuilder builder = new StringBuilder();
        if (!info.isEmpty()) {
            for (Map.Entry<String, Map<String, Integer>> userInfo : info.entrySet()) {
                for (Map.Entry<String, Integer> balanceInfo : userInfo.getValue().entrySet()) {
                    if (balanceInfo.getValue() < 0) {
                        if (builder.length() > 0) {
                            builder.append(System.lineSeparator());
                        }

                        builder.append(userInfo.getKey())
                                .append(" ")
                                .append("owes")
                                .append(" ")
                                .append(balanceInfo.getKey())
                                .append(" ")
                                .append(-balanceInfo.getValue());
                    }
                }
            }
        }

        if (builder.length() > 0) {
            return builder.toString();
        } else {
            return result;
        }
    }

    private static Map<String, Integer> countBalanceForPerson(Person person, LocalDate date) {
        Map<String, Integer> balance = new TreeMap<>();
        List<Operation> operations = person.getAccount().getOperationsByIdForCurrentDate(date);
        for (Operation operation : operations) {
            int amount = 0;
            if (operation.getType().equals(BORROW)) {
                amount = -operation.getAmount();
            } else {
                amount = operation.getAmount();
            }

            String ownerName = operation.getFrom().getName();
            if (balance.containsKey(ownerName)) {
                balance.put(ownerName, balance.get(ownerName) + amount);
            } else {
                balance.put(ownerName, amount);
            }

        }
        return balance;
    }

    //TODO подсчет [DATE balance open] на КОНЕЦ предыдущего месяца, не включая указанный в DATE

    //TODO TRANSACTION Synchronizer - синхронизатор транзакции
    public static void transactorSynchronizer(LocalDate date, Type type, Person firstPerson, Person secondPerson, int cash) {
        Type typeForSecond = typeInverse(type);
        Operation operationForFirst = new Operation(date, type, secondPerson, cash);
        Operation operationForSecond = new Operation(date, typeForSecond, firstPerson, cash);
        firstPerson.getAccount().addOperation(operationForFirst);
        secondPerson.getAccount().addOperation(operationForSecond);
    }

    //TODO реверс входящего ENUMa
    static Type typeInverse(Type type) {
        if (type.equals(BORROW)) return REPAY;
        if (type.equals(REPAY)) return BORROW;
        else return null;
    }

    //TODO поиск Person по имени
    static Person findByName(String name) {
        Person person = null;
        for (Person prs : personList) {
            if (prs.getName().equals(name)) person = prs;
        }
        return person;
    }

    //TODO Find account id by user's name
    static int idFinder(String name) {
        int tempId = 0;
        for (Person person : personList) {
            if (person.getName().equals(name)) {
                tempId = person.getAccount().getId();
            }
        }
        return tempId;
    }

    //TODO парсер запроса - на выходе массив верифицированных строк запроса
    static String[] requestParserNew(String requestline) throws InvalidArgumentException, UnknownCommandException {
        String[] result = requestline.trim().split(" ");


        //TODO >> 2020.09.30  repay Bob Ann 5
        //TODO >> 2020.09.30  repay Ann
        //TODO >> 2020.09.30  borrow  Bob Ann 5
        //TODO >> 2020.09.30  borrow  Ann
        if (result.length > 1 && (result[1].equalsIgnoreCase(Commands.BORROW.getKey()) ||
                result[1].equalsIgnoreCase(Commands.REPAY.getKey()))) {
            result[1] = result[1].toLowerCase();
            if (result.length == 5 && isDateValid(result[0]) && result[4].matches("[-+]?\\d+")) {
                return result;
            } else {
                throw new InvalidArgumentException();
            }

        }
        //TODO >> 2020.09.30  balance close
        //TODO >> 2020.09.30  balance
        else if (result.length > 1 && result[1].equalsIgnoreCase("balance")) {

            result[1] = result[1].toLowerCase();
            if ((result.length == 3 &&
                    (Commands.BALANCE_CLOSE.getKey().equalsIgnoreCase(result[2]) ||
                            Commands.BALANCE_OPEN.getKey().equalsIgnoreCase(result[2])) &&
                    isDateValid(result[0])) ||
                    (result.length == 2 && isDateValid(result[0]))) {

                return result;
            } else {
                throw new InvalidArgumentException();
            }

        }

        //TODO >> repay Bob Ann 5
        //TODO >> repay Ann
        //TODO >> borrow  Bob Ann 5
        //TODO >> borrow  Ann
        else if (result.length > 0 && (result[0].equalsIgnoreCase(Commands.BORROW.getKey()) ||
                result[0].equalsIgnoreCase(Commands.REPAY.getKey()))) {

            result[0] = result[0].toLowerCase();
            if (result.length == 4 && result[3].matches("[-+]?\\d+")) {
                return result;
            } else {
                throw new InvalidArgumentException();
            }

        }
        //TODO >> balance close
        //TODO >> balance
        else if (result.length > 0 && result[0].equalsIgnoreCase("balance")) {

            result[0] = result[0].toLowerCase();
            if ((result.length == 2 &&
                    (Commands.BALANCE_CLOSE.getKey().equalsIgnoreCase(result[1]) ||
                            Commands.BALANCE_OPEN.getKey().equalsIgnoreCase(result[1]))) ||
                    result.length == 1) {
                return result;
            } else {
                throw new InvalidArgumentException();
            }

        }

        //TODO > help
        else if (result.length == 1 && Commands.HELP.getKey().equalsIgnoreCase(result[0])) {
            result[0] = result[0].toLowerCase();
            return result;
        } else {
            throw new UnknownCommandException();
        }

    }


    //TODO проверка на наличие в вбитой строке даты в правильном формате
    static boolean isDateValid(String line) {
        String resultDate = line.split(" ")[0];
        try {
            df.setLenient(false);
            df.parse(resultDate);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}
