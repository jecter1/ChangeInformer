package ru.mamchits.informer.util;

import org.junit.Before;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;


public class LetterMakerTest {
    private static final String LETTER_EMPTY_TEMPLATE_PROPERTIES_PATH = "src/test/resources/letter_test_empty.properties";
    private static final String LETTER_TEMPLATE_PROPERTIES_PATH = "src/test/resources/letter_test_full.properties";

    private static final String TEST_SECRETARY_NAME = "Наталья";
    private static final String TEST_SECRETARY_PATRONYMIC = "Юрьевна";

    private static final String LETTER_FROM_TEMPLATE_MANUALLY_NO_CHANGES = """
                        Здравствуйте, дорогая Наталья Юрьевна!
        
                        За последние сутки во вверенных Вам сайтах изменений не произошло.
                        
                        С уважением, автоматизированная система мониторинга.""";

    private static final String REMOVED_URL_STRING = "https://www.removed.com";
    private static final String SECOND_REMOVED_URL_STRING = "https://www.removed2.com";
    private static final String NEW_URL_STRING = "https://www.new.com";
    private static final String MODIFIED_URL_STRING = "https://www.modified.com";

    private static final String LETTER_FROM_TEMPLATE_MANUALLY_WITH_CHANGES = """
                        Здравствуйте, дорогая Наталья Юрьевна!
                                            
                        За последние сутки во вверенных Вам сайтах произошли следующие изменения:
                        
                        Исчезли следующие страницы:
                        \thttps://www.removed.com
                        
                        Появились следующие новые страницы:
                        \thttps://www.new.com
                        
                        Изменились следующие страницы:
                        \thttps://www.modified.com
                        
                        С уважением, автоматизированная система мониторинга.""";

    private static final String LETTER_FROM_TEMPLATE_MANUALLY_WITH_CHANGES_2_1 = """
                        Здравствуйте, дорогая Наталья Юрьевна!
                                            
                        За последние сутки во вверенных Вам сайтах произошли следующие изменения:
                        
                        Исчезли следующие страницы:
                        \thttps://www.removed.com
                        \thttps://www.removed2.com
                        
                        Появились следующие новые страницы:
                        \thttps://www.new.com
                        
                        С уважением, автоматизированная система мониторинга.""";

    private static final String LETTER_FROM_TEMPLATE_MANUALLY_WITH_CHANGES_2_2 = """
                        Здравствуйте, дорогая Наталья Юрьевна!
                                            
                        За последние сутки во вверенных Вам сайтах произошли следующие изменения:
                        
                        Исчезли следующие страницы:
                        \thttps://www.removed2.com
                        \thttps://www.removed.com
                        
                        Появились следующие новые страницы:
                        \thttps://www.new.com
                        
                        С уважением, автоматизированная система мониторинга.""";

    private static final String URL_CREATE_EXCEPTION = "Failed to make URLs from Strings";

    private static URL removed_url;
    private static URL second_removed_url;
    private static URL new_url;
    private static URL modified_url;

    @Test(timeout = 200)
    public void testMakeEmpty() {
        Set<URL> removedPages = new HashSet<>();
        Set<URL> newPages = new HashSet<>();
        Set<URL> modifiedPages = new HashSet<>();

        LetterMaker letterMaker = new LetterMaker(LETTER_EMPTY_TEMPLATE_PROPERTIES_PATH);
        String letter = letterMaker.make(LetterMaker.Gender.FEMALE, TEST_SECRETARY_NAME, TEST_SECRETARY_PATRONYMIC,
                                         removedPages, newPages, modifiedPages);

        assert letter.equals(TEST_SECRETARY_NAME + TEST_SECRETARY_PATRONYMIC);
    }

    @Test(timeout = 200)
    public void testMakeNoChanges() {
        Set<URL> removedPages = new HashSet<>();
        Set<URL> newPages = new HashSet<>();
        Set<URL> modifiedPages = new HashSet<>();

        LetterMaker letterMaker = new LetterMaker(LETTER_TEMPLATE_PROPERTIES_PATH);
        String letter = letterMaker.make(LetterMaker.Gender.FEMALE, TEST_SECRETARY_NAME, TEST_SECRETARY_PATRONYMIC,
                                         removedPages, newPages, modifiedPages);

        assert letter.equals(LETTER_FROM_TEMPLATE_MANUALLY_NO_CHANGES);
    }

    @Test(timeout = 200)
    public void testMakeWithChanges() {
        Set<URL> removedPages = new HashSet<>();
        removedPages.add(removed_url);

        Set<URL> newPages = new HashSet<>();
        newPages.add(new_url);

        Set<URL> modifiedPages = new HashSet<>();
        modifiedPages.add(modified_url);

        LetterMaker letterMaker = new LetterMaker(LETTER_TEMPLATE_PROPERTIES_PATH);
        String letter = letterMaker.make(LetterMaker.Gender.FEMALE, TEST_SECRETARY_NAME, TEST_SECRETARY_PATRONYMIC,
                removedPages, newPages, modifiedPages);

        assert letter.equals(LETTER_FROM_TEMPLATE_MANUALLY_WITH_CHANGES);
    }

    @Test(timeout = 200)
    public void testMakeWithChanges2() {
        Set<URL> removedPages = new HashSet<>();
        removedPages.add(removed_url);
        removedPages.add(second_removed_url);

        Set<URL> newPages = new HashSet<>();
        newPages.add(new_url);

        Set<URL> modifiedPages = new HashSet<>();

        LetterMaker letterMaker = new LetterMaker(LETTER_TEMPLATE_PROPERTIES_PATH);
        String letter = letterMaker.make(LetterMaker.Gender.FEMALE, TEST_SECRETARY_NAME, TEST_SECRETARY_PATRONYMIC,
                removedPages, newPages, modifiedPages);

        assert letter.equals(LETTER_FROM_TEMPLATE_MANUALLY_WITH_CHANGES_2_1) ||
               letter.equals(LETTER_FROM_TEMPLATE_MANUALLY_WITH_CHANGES_2_2);
    }

    @Before
    public void initUrls() {
        try {
            removed_url = new URL(REMOVED_URL_STRING);
            second_removed_url = new URL(SECOND_REMOVED_URL_STRING);
            modified_url = new URL(MODIFIED_URL_STRING);
            new_url = new URL(NEW_URL_STRING);
        } catch (MalformedURLException ex) {
            System.err.println(URL_CREATE_EXCEPTION);
            System.err.println(ex.getMessage());
        }
    }
}
