package ru.mamchits;

import ru.mamchits.informer.ChangesInformer;
import ru.mamchits.informer.util.LetterMaker.Gender;

import java.util.HashMap;

/**
 * App is a class for a simple demonstration of how ChangesInformer works
 */
public class App {
    private static final String LETTER_TEMPLATE_PROPERTIES_PATH = "src/main/resources/letter.properties";

    private static final String TEST_SECRETARY_NAME = "Наталья";
    private static final String TEST_SECRETARY_PATRONYMIC = "Юрьевна";

    private static final String STAYED_URL = "www.stayed.com";
    private static final String STAYED_CONTENT = "stayed_content";

    private static final String REMOVED_URL = "www.removed.com";
    private static final String REMOVED_CONTENT = "removed_content";

    private static final String MODIFIED_URL = "www.modified.com";
    private static final String MODIFIED_CONTENT_OLD = "modified_content_old";
    private static final String MODIFIED_CONTENT_NEW = "modified_content_new";

    private static final String NEW_URL = "www.new.com";
    private static final String NEW_CONTENT = "new_content";

    private static final HashMap<String, String> yesterdayContent = new HashMap<>();
    private static final HashMap<String, String> todayContent = new HashMap<>();

    public static void main(String[] args) {
        fillHashMaps();
        ChangesInformer changeInformer = new ChangesInformer(LETTER_TEMPLATE_PROPERTIES_PATH);
        changeInformer.updateChanges(yesterdayContent, todayContent);
        changeInformer.inform(Gender.FEMALE, TEST_SECRETARY_NAME, TEST_SECRETARY_PATRONYMIC);
    }

    /**
     * Fill both HashMaps with URLs and HTML codes for all possible cases
     */
    private static void fillHashMaps() {
        yesterdayContent.put(STAYED_URL, STAYED_CONTENT);
        yesterdayContent.put(MODIFIED_URL, MODIFIED_CONTENT_OLD);
        yesterdayContent.put(REMOVED_URL, REMOVED_CONTENT);
        todayContent.put(STAYED_URL, STAYED_CONTENT);
        todayContent.put(MODIFIED_URL, MODIFIED_CONTENT_NEW);
        todayContent.put(NEW_URL, NEW_CONTENT);
    }
}
