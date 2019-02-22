import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class Console implements Runnable {
    private final AtomicBoolean running = new AtomicBoolean(false);
    private Thread worker;
    public Console() {
    }

    public void start() {
        worker = new Thread(this);
        worker.start();
    }

    public void stop() {
        running.set(false);
    }

    @Override
    public void run() {
        running.set(true);
        while (running.get()) {
            System.out.println("Available options: ");
            Scanner scanner = new Scanner(System.in);
            String option = scanner.nextLine();
            System.out.println("Option is " + option);
            if ("exit".equalsIgnoreCase(option)) {
                Thread.currentThread().interrupt();
                System.out.println("Exiting");
            }
        }
    }
}
