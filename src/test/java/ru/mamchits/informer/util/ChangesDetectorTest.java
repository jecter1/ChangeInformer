package ru.mamchits.informer.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Set;

public class ChangesDetectorTest {
    private static final String STAYED_URL_STRING = "https://www.stayed.com";
    private static final String STAYED_CONTENT = "stayed_content";

    private static final String REMOVED_URL_STRING = "https://www.removed.com";
    private static final String REMOVED_CONTENT = "removed_content";

    private static final String MODIFIED_URL_STRING = "https://www.modified.com";
    private static final String MODIFIED_CONTENT_OLD = "modified_content_old";
    private static final String MODIFIED_CONTENT_NEW = "modified_content_new";

    private static final String NEW_URL_STRING = "https://www.new.com";
    private static final String NEW_CONTENT = "new_content";

    private static final String URL_CREATE_EXCEPTION = "Failed to make URLs from Strings";

    private static final int URL_COUNT_EACH_GROUP = 1;

    private static final HashMap<URL, Document> yesterdayContent = new HashMap<>();
    private static final HashMap<URL, Document> todayContent = new HashMap<>();

    private static URL stayed_url;
    private static URL removed_url;
    private static URL modified_url;
    private static URL new_url;

    @Test(timeout = 200)
    public void testGetNewUrls() {
        Set<URL> newUrls = ChangesDetector.getNewUrls(yesterdayContent, todayContent);
        assert newUrls.contains(new_url);
        assert newUrls.size() == URL_COUNT_EACH_GROUP;
    }

    @Test(timeout = 200)
    public void testGetRemovedUrls() {
        Set<URL> removedUrls = ChangesDetector.getRemovedUrls(yesterdayContent, todayContent);
        assert removedUrls.contains(removed_url);
        assert removedUrls.size() == URL_COUNT_EACH_GROUP;
    }

    @Test(timeout = 200)
    public void testGetModifiedUrls() {
        Set<URL> modifiedUrls = ChangesDetector.getModifiedUrls(yesterdayContent, todayContent);
        assert modifiedUrls.contains(modified_url);
        assert modifiedUrls.size() == URL_COUNT_EACH_GROUP;
    }

    @Before
    public void fillHashMaps() {
        Document stayed_html = Jsoup.parse(STAYED_CONTENT);
        Document modified_old_html = Jsoup.parse(MODIFIED_CONTENT_OLD);
        Document removed_html = Jsoup.parse(REMOVED_CONTENT);
        Document modified_new_html = Jsoup.parse(MODIFIED_CONTENT_NEW);
        Document new_html = Jsoup.parse(NEW_CONTENT);

        yesterdayContent.put(stayed_url, stayed_html);
        yesterdayContent.put(modified_url, modified_old_html);
        yesterdayContent.put(removed_url, removed_html);
        todayContent.put(stayed_url, stayed_html);
        todayContent.put(modified_url, modified_new_html);
        todayContent.put(new_url, new_html);
    }

    @Before
    public void initUrls() {
        try {
            stayed_url = new URL(STAYED_URL_STRING);
            removed_url = new URL(REMOVED_URL_STRING);
            modified_url = new URL(MODIFIED_URL_STRING);
            new_url = new URL(NEW_URL_STRING);
        } catch (MalformedURLException ex) {
            System.err.println(URL_CREATE_EXCEPTION);
            System.err.println(ex.getMessage());
        }
    }
}
