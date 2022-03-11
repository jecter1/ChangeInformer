package ru.mamchits.informer;

import org.junit.Test;

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

    private static final String REMOVED_URL = "www.removed.com";
    private static final String SECOND_REMOVED_URL = "www.removed2.com";
    private static final String NEW_URL = "www.new.com";
    private static final String MODIFIED_URL = "www.modified.com";

    private static final String LETTER_FROM_TEMPLATE_MANUALLY_WITH_CHANGES = """
                        Здравствуйте, дорогая Наталья Юрьевна!
                                            
                        За последние сутки во вверенных Вам сайтах произошли следующие изменения:
                        
                        Исчезли следующие страницы:
                        \twww.removed.com
                        
                        Появились следующие новые страницы:
                        \twww.new.com
                        
                        Изменились следующие страницы:
                        \twww.modified.com
                        
                        С уважением, автоматизированная система мониторинга.""";

    private static final String LETTER_FROM_TEMPLATE_MANUALLY_WITH_CHANGES_2 = """
                        Здравствуйте, дорогая Наталья Юрьевна!
                                            
                        За последние сутки во вверенных Вам сайтах произошли следующие изменения:
                        
                        Исчезли следующие страницы:
                        \twww.removed.com
                        \twww.removed2.com
                        
                        Появились следующие новые страницы:
                        \twww.new.com
                        
                        С уважением, автоматизированная система мониторинга.""";


    @Test(timeout = 100)
    public void testMakeEmpty() {
        Set<String> removedPages = new HashSet<>();
        Set<String> newPages = new HashSet<>();
        Set<String> modifiedPages = new HashSet<>();

        LetterMaker letterMaker = new LetterMaker(LETTER_EMPTY_TEMPLATE_PROPERTIES_PATH);
        String letter = letterMaker.make(LetterMaker.Gender.FEMALE, TEST_SECRETARY_NAME, TEST_SECRETARY_PATRONYMIC,
                                         removedPages, newPages, modifiedPages);

        assert letter.equals(TEST_SECRETARY_NAME + TEST_SECRETARY_PATRONYMIC);
    }

    @Test(timeout = 100)
    public void testMakeNoChanges() {
        Set<String> removedPages = new HashSet<>();
        Set<String> newPages = new HashSet<>();
        Set<String> modifiedPages = new HashSet<>();

        LetterMaker letterMaker = new LetterMaker(LETTER_TEMPLATE_PROPERTIES_PATH);
        String letter = letterMaker.make(LetterMaker.Gender.FEMALE, TEST_SECRETARY_NAME, TEST_SECRETARY_PATRONYMIC,
                                         removedPages, newPages, modifiedPages);

        assert letter.equals(LETTER_FROM_TEMPLATE_MANUALLY_NO_CHANGES);
    }

    @Test(timeout = 100)
    public void testMakeWithChanges() {
        Set<String> removedPages = new HashSet<>();
        removedPages.add(REMOVED_URL);

        Set<String> newPages = new HashSet<>();
        newPages.add(NEW_URL);

        Set<String> modifiedPages = new HashSet<>();
        modifiedPages.add(MODIFIED_URL);

        LetterMaker letterMaker = new LetterMaker(LETTER_TEMPLATE_PROPERTIES_PATH);
        String letter = letterMaker.make(LetterMaker.Gender.FEMALE, TEST_SECRETARY_NAME, TEST_SECRETARY_PATRONYMIC,
                removedPages, newPages, modifiedPages);

        assert letter.equals(LETTER_FROM_TEMPLATE_MANUALLY_WITH_CHANGES);
    }

    @Test(timeout = 100)
    public void testMakeWithChanges2() {
        Set<String> removedPages = new HashSet<>();
        removedPages.add(REMOVED_URL);
        removedPages.add(SECOND_REMOVED_URL);

        Set<String> newPages = new HashSet<>();
        newPages.add(NEW_URL);

        Set<String> modifiedPages = new HashSet<>();

        LetterMaker letterMaker = new LetterMaker(LETTER_TEMPLATE_PROPERTIES_PATH);
        String letter = letterMaker.make(LetterMaker.Gender.FEMALE, TEST_SECRETARY_NAME, TEST_SECRETARY_PATRONYMIC,
                removedPages, newPages, modifiedPages);

        assert letter.equals(LETTER_FROM_TEMPLATE_MANUALLY_WITH_CHANGES_2);
    }
}
