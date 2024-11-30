public class Customer {
    private int ticketNumber;
    private int transactionTime;

    public Customer(int ticketNumber, int transactionTime) {
        this.ticketNumber = ticketNumber;
        this.transactionTime = transactionTime;
    }

    public int getTicketNumber() {
        return ticketNumber;
    }

    public int getTransactionTime() {
        return transactionTime;
    }

    @Override
    public String toString() {
        return "Customer [TicketNumber=" + ticketNumber + ", TransactionTime=" + transactionTime + "]";
    }
}
