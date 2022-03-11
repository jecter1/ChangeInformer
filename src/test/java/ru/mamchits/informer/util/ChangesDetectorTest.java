package ru.mamchits.informer.util;

import org.junit.*;

import java.util.HashMap;
import java.util.Set;

public class ChangesDetectorTest {
    private static final String STAYED_URL = "stayed_url";
    private static final String STAYED_CONTENT = "stayed_content";

    private static final String REMOVED_URL = "removed_url";
    private static final String REMOVED_CONTENT = "removed_content";

    private static final String MODIFIED_URL = "modified_url";
    private static final String MODIFIED_CONTENT_OLD = "modified_content_old";
    private static final String MODIFIED_CONTENT_NEW = "modified_content_new";

    private static final String NEW_URL = "new_url";
    private static final String NEW_CONTENT = "new_content";

    private static final HashMap<String, String> yesterdayContent = new HashMap<>();
    private static final HashMap<String, String> todayContent = new HashMap<>();

    @Test(timeout = 100)
    public void testGetNewUrls() {
        Set<String> newUrls = ChangesDetector.getNewUrls(yesterdayContent, todayContent);
        assert newUrls.contains(NEW_URL);
        assert newUrls.size() == 1;
    }

    @Test(timeout = 100)
    public void testGetRemovedUrls() {
        Set<String> removedUrls = ChangesDetector.getRemovedUrls(yesterdayContent, todayContent);
        assert removedUrls.contains(REMOVED_URL);
        assert removedUrls.size() == 1;
    }

    @Test(timeout = 100)
    public void testGetModifiedUrls() {
        Set<String> modifiedUrls = ChangesDetector.getModifiedUrls(yesterdayContent, todayContent);
        assert modifiedUrls.contains(MODIFIED_URL);
        assert modifiedUrls.size() == 1;
    }

    @Before
    public void fillHashMaps() {
        yesterdayContent.put(STAYED_URL, STAYED_CONTENT);
        yesterdayContent.put(MODIFIED_URL, MODIFIED_CONTENT_OLD);
        yesterdayContent.put(REMOVED_URL, REMOVED_CONTENT);
        todayContent.put(STAYED_URL, STAYED_CONTENT);
        todayContent.put(MODIFIED_URL, MODIFIED_CONTENT_NEW);
        todayContent.put(NEW_URL, NEW_CONTENT);
    }
}