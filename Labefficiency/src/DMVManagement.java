import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class DMVManagement {
    private static final int NUMBER_OF_SERVERS = 3;

    public static Queue<Customer> readCustomersFromFile(String fileName) {
        Queue<Customer> customerQueue = new LinkedList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                int ticketNumber = Integer.parseInt(parts[0]);
                int transactionTime = Integer.parseInt(parts[1]);
                customerQueue.add(new Customer(ticketNumber, transactionTime));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return customerQueue;
    }

    public static void main(String[] args) {
        Queue<Customer> customerQueue = readCustomersFromFile("customers.txt");

        BlockingQueue<Runnable> blockingQueue = new LinkedBlockingQueue<>();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                NUMBER_OF_SERVERS,
                NUMBER_OF_SERVERS,
                0L,
                TimeUnit.SECONDS,
                blockingQueue
        );

        Server[] servers = new Server[NUMBER_OF_SERVERS];
        for (int i = 0; i < NUMBER_OF_SERVERS; i++) {
            servers[i] = new Server(i + 1);
        }

        while (!customerQueue.isEmpty() || executor.getActiveCount() > 0) {
            for (Server server : servers) {
                if (server.isAvailable() && !customerQueue.isEmpty()) {
                    Customer nextCustomer = customerQueue.poll();
                    server.assignCustomer(nextCustomer);
                    executor.execute(server);
                }
            }
        }

        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("All customers have been served.");
    }
}


