public class Customer {
    // Unique number assigned to each customer
    private int ticketNumber;

    // Time taken for the customer's transaction
    private int transactionTime;

    // Constructor to initialize the ticket number and transaction time
    public Customer(int ticketNumber, int transactionTime) {
        this.ticketNumber = ticketNumber; // Assign the ticket number
        this.transactionTime = transactionTime; // Assign the transaction time
    }

    // Getter method to get the ticket number of the customer
    public int getTicketNumber() {
        return ticketNumber;
    }

    // Getter method to get the transaction time of the customer
    public int getTransactionTime() {
        return transactionTime;
    }

    // Converts the Customer object to a readable string format
    @Override
    public String toString() {
        return "Customer [TicketNumber=" + ticketNumber + ", TransactionTime=" + transactionTime + "]";
    }
}
