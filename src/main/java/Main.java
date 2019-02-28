import org.apache.log4j.PropertyConfigurator;
import system.UserMenu;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class Main {
    public static void main(String[] args) {
        PropertyConfigurator.configure(Main.class.getResourceAsStream("/log4j.properties"));
        ScheduledThreadPoolExecutor scheduledExecutorService = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(4);
        scheduledExecutorService.setRemoveOnCancelPolicy(true);


        UserMenu cli = new UserMenu(scheduledExecutorService);
        cli.launchUserMenu();


//        org.apache.log4j.BasicConfigurator.configure();
//        String urlNew = "https://www.jpl.nasa.gov/multimedia/rss/news.xml";
//        controller.RssFeed parser = new controller.RssFeed(urlNew, 1);
//
//        parser.dumb();

    }
}
