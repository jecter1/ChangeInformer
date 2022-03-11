package ru.mamchits;

import ru.mamchits.informer.ChangesInformer;
import ru.mamchits.informer.util.LetterMaker;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

/**
 * App is a class for a simple demonstration of how ChangesInformer works
 */
public class App {
    private static final String LETTER_TEMPLATE_PROPERTIES_PATH = "src/main/resources/letter.properties";

    private static final LetterMaker.Gender TEST_SECRETARY_GENDER = LetterMaker.Gender.FEMALE;
    private static final String TEST_SECRETARY_NAME = "Наталья";
    private static final String TEST_SECRETARY_PATRONYMIC = "Юрьевна";

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

    private static final HashMap<URL, String> yesterdayContent = new HashMap<>();
    private static final HashMap<URL, String> todayContent = new HashMap<>();

    public static void main(String[] args) {
        fillHashMaps();
        ChangesInformer changeInformer = new ChangesInformer(LETTER_TEMPLATE_PROPERTIES_PATH);
        changeInformer.updateChanges(yesterdayContent, todayContent);
        changeInformer.inform(TEST_SECRETARY_GENDER, TEST_SECRETARY_NAME, TEST_SECRETARY_PATRONYMIC);
    }

    /**
     * Fill both HashMaps with URLs and HTML codes for all possible cases
     */
    private static void fillHashMaps() {
        URL stayed_url, removed_url, modified_url, new_url;
        try {
            stayed_url = new URL(STAYED_URL_STRING);
            removed_url = new URL(REMOVED_URL_STRING);
            modified_url = new URL(MODIFIED_URL_STRING);
            new_url = new URL(NEW_URL_STRING);

            yesterdayContent.put(stayed_url, STAYED_CONTENT);
            yesterdayContent.put(modified_url, MODIFIED_CONTENT_OLD);
            yesterdayContent.put(removed_url, REMOVED_CONTENT);

            todayContent.put(stayed_url, STAYED_CONTENT);
            todayContent.put(modified_url, MODIFIED_CONTENT_NEW);
            todayContent.put(new_url, NEW_CONTENT);
        } catch (MalformedURLException ex) {
            System.err.println(URL_CREATE_EXCEPTION);
            System.err.println(ex.getMessage());
        }
    }
}
