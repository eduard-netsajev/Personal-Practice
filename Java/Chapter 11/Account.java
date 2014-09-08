import java.util.ArrayList;
import java.util.Date;

public class Account {

    public static void main(String[] args) {

        Account acc = new Account("George", 1122, 1000);
        Account.setAnnualInterestRate(1.5);

        acc.deposit(30, "first deposit");
        acc.deposit(40, "second deposit");
        acc.deposit(50, "last deposit");

        acc.withdraw(5, "buy hamburger");
        acc.withdraw(4, "buy chocolate");
        acc.withdraw(2, "buy juice");

        System.out.println(acc);
    }

    private String name;
    private int id;
    private double balance;
    private static double annualInterestRate;
    private Date dateCreated = new Date();
    private ArrayList<Transaction> transactions = new ArrayList<>();

    public Account() {}

    public Account(int id, double balance) {
        this.id = id;
        this.balance = balance;
    }

    public Account(String name, int id, double balance) {
        this.name = name;
        this.id = id;
        this.balance = balance;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public double getMonthlyInterestRate() {
        return annualInterestRate / 12 / 100;
    }

    public double getMonthlyInterest() {
        return balance * getMonthlyInterestRate();
    }

    public double withdraw(double sum, String description) {
        balance -= sum;
        transactions.add(new Transaction('W', sum, balance, description));
        return sum;
    }

    public void deposit(double sum, String description) {
        balance += sum;
        transactions.add(new Transaction('D', sum, balance, description));
    }

    public static double getAnnualInterestRate() {
        return annualInterestRate;
    }

    public static void setAnnualInterestRate(double annualInterestRate) {
        Account.annualInterestRate = annualInterestRate;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    @Override
    public String toString() {
        return "Account Summary :" +
                "\n Name = '" + name + '\'' +
                ",\n Balance = " + balance +
                ",\n Annual Interest Rate = " + annualInterestRate +
                "%,\n Transactions:\n" + transactions;
    }
}

class Transaction {
    private Date date = new Date();
    private char type;

    private double amount;
    private double balance;
    private String description;

    Transaction(char type, double amount, double balance, String description) {
        this.type = type;
        this.amount = amount;
        this.balance = balance;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public Date getDate() {
        return date;
    }

    public char getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "\nTransaction{" +
                "date=" + date +
                ", type=" + type +
                ", amount=" + amount +
                ", balance=" + balance +
                ", description='" + description + '\'' +
                "}";
    }

    public double getBalance() {
        return balance;
    }
}
