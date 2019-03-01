package com.konovalov.edu.system;

import com.konovalov.edu.controller.FeedController;
import com.konovalov.edu.exceptions.FeedAlreadyExistsException;
import com.konovalov.edu.model.RssFeed;
import com.konovalov.edu.exceptions.NoSuchFeedException;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;

@Slf4j
public class UserMenu {
    private boolean running;
    private Scanner scanner;
    private FeedController feedController;


    public UserMenu(FeedController feedController) {
        this.running = true;
        this.scanner = new Scanner(System.in);
        this.feedController = feedController;
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
                try {
                    addFeed();
                } catch (FeedAlreadyExistsException ex) {
                    log.error(ex.getMessage());
                }
                break;
            }
            case ("list"): {
                displayFeedList();
                break;
            }
            case ("view"): {
                viewFeed();
                break;
            }
            case ("edit"): {
                try {
                    editFeed();
                } catch (NoSuchFeedException ex) {
                    log.error(ex.getMessage());
                }
                break;
            }
            case ("stop"): {
                try {
                    stopFeed();
                } catch (NoSuchFeedException ex) {
                    log.error(ex.getMessage());
                }
                break;
            }
            case ("exit"): {
                running = false;
                scanner.close();
                this.feedController.shutdownExecutor();
                return;
            }
        }
    }

    private void addFeed() {
        System.out.println("Name: ");
        String name = scanner.nextLine();
        System.out.println("url: ");
        String URL = scanner.nextLine().trim();
        System.out.println("update time: ");
        long updateTime = scanner.nextLong(); // TODO catch mismatch exc
        if (!this.feedController.isFeedExists(name)) {
            RssFeed newFeed = new RssFeed(name, URL, updateTime, null, null, null);
            this.feedController.addFeed(newFeed);
        } else {
            throw new FeedAlreadyExistsException(String.format("Feed %s does not exist", name));
        }
    }

    private void displayFeedList() {
        this.feedController.getFeedsList().forEach((key, value) ->
                System.out.println(String.format("Name: %s. Status: %b", key, value.getStatus())));
    }

    private void editFeed() {
        System.out.println("Name to change: ");
        String name = scanner.nextLine();
        System.out.println("Property to change: ");
        String property = scanner.nextLine();
        System.out.println("New value: ");
        String value = scanner.nextLine();
        if (this.feedController.isFeedExists(name)) {
            this.feedController.editProperties(name, property, value);
        } else {
            throw new NoSuchFeedException(String.format("Feed %s does not exist", name));
        }
    }

    private void viewFeed() {
        System.out.println("Name to view: ");
        String name = scanner.nextLine();
        if (this.feedController.isFeedExists(name)) {
            System.out.println(this.feedController.viewFeed(name));
        } else {
            throw new NoSuchFeedException(String.format("Feed %s does not exist", name));
        }

    }

    private void stopFeed() {
        System.out.println("Name to stop: ");
        String name = scanner.nextLine();

        if (this.feedController.isFeedExists(name)) {
            this.feedController.stopFeed(name);
        } else {
            throw new NoSuchFeedException(String.format("Feed %s does not exist", name));
        }
    }

    private void printMenu() {
        log.info("Chose option: ");
        log.info("1. Add feed worker      || add");
        log.info("2. Display workers list || list");
        log.info("3. Edit feed worker     || edit");
        log.info("4. Stop feed worker     || stop");
        log.info("5. Exit                 || exit");
    }
}
