public class BankAccount implements Measurable {
    private int accNumber;
    private double balance;
    private static int lastAssignedNumber = 0;

    public BankAccount() {
        this(0);
    }

    public BankAccount(double balance) {
        lastAssignedNumber++;
        accNumber = lastAssignedNumber;
        this.balance = balance;
    }

    public int getAccountNumber() {
        return accNumber;
    }

    public double getMeasure() {
        return balance;
    }

    public String getName() {
        return "Account " + accNumber;
    }
}
