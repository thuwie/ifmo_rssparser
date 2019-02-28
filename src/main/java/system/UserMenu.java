package system;

import controller.FeedsController;
import controller.RssFeed;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;

@Slf4j
public class UserMenu {
    private boolean running;
    private Scanner scanner;
    private FeedsController feedsController;
    private ScheduledThreadPoolExecutor executor;


    public UserMenu(ScheduledThreadPoolExecutor scheduledExecutorService) {
        this.running = true;
        this.executor = scheduledExecutorService;
        this.scanner = new Scanner(System.in);
        this.feedsController = new FeedsController(new ConcurrentHashMap<>(), scheduledExecutorService);
    }

    public void launchUserMenu() {
        while (running) {
            printMenu();
            invokeMenu();
        }
    }

    private void invokeMenu() {

        String option = scanner.nextLine();
        switch (option) {
            case ("add"): {
                addFeed();
                break;
            }
            case ("list"): {
                this.feedsController.getFeedsList().forEach((key, value) ->
                        System.out.println(String.format("Name: %s. Status: %b", key, value.getStatus())));
                break;
            }
            case ("stop"): {
                stopFeed();
                break;
            }
            case ("exit"): {
                running = false;
                scanner.close();
                this.executor.shutdown();
                return;
            }
        }
    }

    private void stopFeed() {
        System.out.println("Name to stop: ");
        String name = scanner.nextLine();
        this.feedsController.stopFeed(name);
    }

    private void addFeed() {
        System.out.println("Name: ");
        String name = scanner.nextLine();
        System.out.println("url: ");
        String URL = scanner.nextLine().trim();
        System.out.println("update time: ");
        int updateTime = (scanner.nextInt());
        RssFeed newFeed = new RssFeed(name, URL, updateTime);
        this.feedsController.addFeed(newFeed);

    }

    private void printMenu() {
        System.out.println("___________________________________________________");
        System.out.println("Print chosen option:\nadd\nlist\nstop\nexit");
    }
}
