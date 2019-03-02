package com.konovalov.edu.system;

import com.konovalov.edu.exceptions.FeedAlreadyExistsException;
import com.konovalov.edu.model.RssFeedConfiguration;
import com.konovalov.edu.exceptions.NoSuchFeedException;
import com.konovalov.edu.util.Defaults;
import com.konovalov.edu.util.FeedUtils;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLOutput;
import java.util.Scanner;

/**
 * The type User menu.
 */
@Slf4j
public class UserMenu {
    private boolean running;
    private Scanner scanner;
    private FeedManager feedManager;


    /**
     * Instantiates a new User menu.
     *
     * @param feedManager the feed manager
     */
    public UserMenu(FeedManager feedManager) {
        this.running = true;
        this.scanner = new Scanner(System.in);
        this.feedManager = feedManager;
    }

    /**
     * Launch user menu.
     */
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
                try {
                    viewFeed();
                } catch (NoSuchFeedException ex) {
                    log.error(ex.getMessage());
                };
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
                this.feedManager.shutdownExecutor();
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
        int updateTime = scanner.nextInt();
        System.out.println("Posts limit: ");
        int postsLimit = scanner.nextInt();// TODO catch mismatch exc
        System.out.println("Template: (optional)");
        String template = scanner.nextLine();
        System.out.println("Filename: (optional)");
        String filename = scanner.nextLine();

        if (!this.feedManager.isFeedExists(name)) {
            RssFeedConfiguration newFeed = new RssFeedConfiguration();
            newFeed.setName(name);
            newFeed.setURL(URL);
            newFeed.setUpdateTime(updateTime);
            newFeed.setTemplate(template.isEmpty() ? Defaults.template : template);
            newFeed.setPostsLimit(postsLimit != 0 ? postsLimit : Defaults.postsLimit);
            newFeed.setFile(filename.isEmpty() ? FeedUtils.getFile(FeedUtils.convertUrlToFilename(URL)) : FeedUtils.getFile(filename));

            this.feedManager.addFeed(newFeed);
        } else {
            throw new FeedAlreadyExistsException(String.format("Feed %s does not exist", name));
        }
    }

    private void displayFeedList() {
        this.feedManager.getFeedsList().forEach((key, value) ->
                System.out.println(String.format("Name: %s. Status: %b", key, value.getFeedConfiguration().getStatus())));
    }

    private void editFeed() {
        System.out.println("Name to change: ");
        String name = scanner.nextLine();
        System.out.println("Property to change: ");
        String property = scanner.nextLine();
        System.out.println("New value: ");
        String value = scanner.nextLine();
        if (this.feedManager.isFeedExists(name)) {
            this.feedManager.editProperties(name, property, value);
        } else {
            throw new NoSuchFeedException(String.format("Feed %s does not exist", name));
        }
    }

    private void viewFeed() {
        System.out.println("Name to view: ");
        String name = scanner.nextLine();
        if (this.feedManager.isFeedExists(name)) {
            System.out.println(this.feedManager.viewFeed(name));
        } else {
            throw new NoSuchFeedException(String.format("Feed %s does not exist", name));
        }

    }

    private void stopFeed() {
        System.out.println("Name to stop: ");
        String name = scanner.nextLine();

        if (this.feedManager.isFeedExists(name)) {
            this.feedManager.stopFeed(name);
        } else {
            throw new NoSuchFeedException(String.format("Feed %s does not exist", name));
        }
    }

    private void printMenu() {
        log.info("Chose option: ");
        log.info("1. Add feed worker           || add");
        log.info("2. Display workers list      || list");
        log.info("3. Display worker's settings || view");
        log.info("4. Edit feed worker          || edit");
        log.info("5. Stop feed worker          || stop");
        log.info("6. Exit                      || exit");
    }
}
