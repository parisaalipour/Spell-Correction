package com.uu.searchengine;

import com.uu.searchengine.dto.WebPageDto;
import com.uu.searchengine.handlers.Handler;
import com.uu.searchengine.handlers.Provider;
import org.json.JSONException;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Spider {


    private static final int MAX_PAGES_TO_SEARCH = 5000;
    private static Set<String> pagesVisited = new HashSet<String>();
    private static ArrayList<String> pagesToVisit = new ArrayList<>();
    public static String currentUrl;

    private static String nextUrl()
    {
        String nextUrl = null;
        do
        {
            if(!pagesToVisit.isEmpty()) {
                nextUrl = pagesToVisit.remove(0);
            }
        } while(pagesVisited.contains(nextUrl));
        pagesVisited.add(nextUrl);
        return nextUrl;
    }

    private static Thread databaseThread;
    private static Thread crawlerThread;

    private static int MAX_PAGES = 10000;

    public static void search(String url) {

        int i = 0;

        while (i < MAX_PAGES)
        {
            SpiderLeg leg = new SpiderLeg();
            if (pagesToVisit.isEmpty()) {
                currentUrl = url;
                pagesVisited.add(url);
            } else {
                currentUrl = nextUrl();
            }
            boolean success = leg.crawl(currentUrl); // Lots of stuff happening here. Look at the crawl method in
            // SpiderLeg

            if (success && leg.getWebPageDto() != null)
            {
                i++;
                System.out.println("crawled pages = " + i);
                Handler.INSTANCE.addWebPageDto(leg.getWebPageDto());
            }

            pagesToVisit.addAll(leg.getLinks());
        }

        Handler.INSTANCE.addAllDiscoveredTermsToDb();

    }
}
