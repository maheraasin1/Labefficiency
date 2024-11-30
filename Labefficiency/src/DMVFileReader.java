import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class DMVFileReader {
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
}
