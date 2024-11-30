import java.util.concurrent.atomic.AtomicBoolean;

public class Server implements Runnable {
    private int serverNumber;
    private Customer currentCustomer;
    private AtomicBoolean available;

    public Server(int serverNumber) {
        this.serverNumber = serverNumber;
        this.available = new AtomicBoolean(true);
    }

    public boolean isAvailable() {
        return available.get();
    }

    public void assignCustomer(Customer customer) {
        this.currentCustomer = customer;
        this.available.set(false);
    }

    @Override
    public void run() {
        if (currentCustomer != null) {
            // Print when the server starts serving a customer
            System.out.println("Server " + serverNumber + " takes customer " + currentCustomer.getTicketNumber());
            try {
                // Simulate transaction time
                Thread.sleep(currentCustomer.getTransactionTime());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // Print when the server finishes serving a customer
            System.out.println("Server " + serverNumber + " is done with customer " + currentCustomer.getTicketNumber());
            // Reset server state
            this.currentCustomer = null;
            this.available.set(true);
        }
    }
}

