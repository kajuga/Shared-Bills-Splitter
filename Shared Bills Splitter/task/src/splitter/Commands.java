package splitter;

public enum Commands {
    BALANCE_CLOSE("close"),
    BALANCE_OPEN("open"),
    BORROW ("borrow"),
    EXIT("exit"),
    HELP("help"),
    REPAY("repay");

    private final String key;

    Commands(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
