//Mahera Asin 
//October 25 2024 
//DMV efficiency 
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        final int NUMBER_OF_SERVERS = 3; // Number of servers

        // Prompt for the file name
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the name of the file with the customers line: ");
        String fileName = scanner.nextLine(); // User enters file name (e.g., "customers.txt")

        // Load customers from the file
        Queue<Customer> customerQueue = DMVFileReader.readCustomersFromFile(fileName);

        // Initialize thread pool
        BlockingQueue<Runnable> blockingQueue = new LinkedBlockingQueue<>();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                NUMBER_OF_SERVERS, NUMBER_OF_SERVERS, 0L, TimeUnit.SECONDS, blockingQueue);

        // Create servers
        Server[] servers = new Server[NUMBER_OF_SERVERS];
        for (int i = 0; i < NUMBER_OF_SERVERS; i++) {
            servers[i] = new Server(i + 1);
        }

        // Assign customers to servers
        while (!customerQueue.isEmpty() || executor.getActiveCount() > 0) {
            for (Server server : servers) {
                if (server.isAvailable() && !customerQueue.isEmpty()) {
                    Customer nextCustomer = customerQueue.poll();
                    server.assignCustomer(nextCustomer);
                    executor.execute(server);

                    // Introduce a slight delay to enforce order
                    try {
                        Thread.sleep(50); // Slight delay for deterministic output
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        // Shut down executor
        executor.shutdown();

        // Close the scanner
        scanner.close();
    }
}


