package ru.mamchits.informer.util;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;

/**
 * LetterMaker is class for making letters (String) for secretaries
 */
public class LetterMaker {
    private static final String GREETINGS_PROPERTY = "greetings";
    private static final String APPEAL_MALE_PROPERTY = "appeal_prefix_male";
    private static final String APPEAL_FEMALE_PROPERTY = "appeal_prefix_female";
    private static final String APPEAL_END_PROPERTY = "appeal_end";
    private static final String CHANGES_BODY_BEGINNING_PROPERTY = "changes_body_beginning";
    private static final String NO_CHANGES_BODY_PROPERTY = "no_changes_body";
    private static final String LIST_ELEMENT_PREFIX_PROPERTY = "list_element_prefix";
    private static final String REMOVED_PAGES_PREFIX_PROPERTY = "removed_pages_prefix";
    private static final String NEW_PAGES_PREFIX_PROPERTY = "new_pages_prefix";
    private static final String MODIFIED_PAGES_PREFIX_PROPERTY = "modified_pages_prefix";
    private static final String SIGNATURE_PROPERTY = "signature";
    private static final String NAME_PATRONYMIC_SEPARATOR_PROPERTY = "name_patronymic_separator";
    private static final String LETTER_PART_SEPARATOR_PROPERTY = "letter_part_separator";

    private static final String EMPTY_STRING = "";

    private static final String LOAD_PROPERTIES_ERROR_DESC = "Failed to load letter properties";

    private Properties letterProperties;

    public enum Gender {
        MALE, FEMALE
    }

    /**
     * Constructor
     * @param  templatePropertiesPath  properties filepath
     */
    public LetterMaker(String templatePropertiesPath) {
        try (FileReader letterPropertiesReader = new FileReader(templatePropertiesPath)) {
            letterProperties = new Properties();
            letterProperties.load(letterPropertiesReader);
        } catch (IOException ex) {
            System.err.println(LOAD_PROPERTIES_ERROR_DESC);
            System.err.println(ex.getMessage());
        }
    }

    /**
     * Makes the full letter
     * @param  gender        secretary's gender
     * @param  name          secretary's name
     * @param  patronymic    secretary's patronymic
     * @param  removedUrls   set of page URLs that are present only in the yesterday map
     * @param  newUrls       set of page URLs that are present only in the today map
     * @param  modifiedUrls  set of page URLs for which the HTML codes are different
     * @return               letter for secretary
     */
    public String make(Gender gender, String name, String patronymic,
                       Set<String> removedUrls, Set<String> newUrls, Set<String> modifiedUrls) {
        return makeHead(gender, name, patronymic) +
                makeBody(removedUrls, newUrls, modifiedUrls) +
                letterProperties.getProperty(SIGNATURE_PROPERTY);
    }

    /**
     * Makes head of the letter
     * @param  gender        secretary's gender
     * @param  name          secretary's name
     * @param  patronymic    secretary's patronymic
     * @return               head of the letter
     */
    private String makeHead(Gender gender, String name, String patronymic) {
        return letterProperties.getProperty(GREETINGS_PROPERTY) +
                getAppealProperty(gender) +
                name +
                letterProperties.getProperty(NAME_PATRONYMIC_SEPARATOR_PROPERTY) +
                patronymic +
                letterProperties.getProperty(APPEAL_END_PROPERTY)+
                letterProperties.getProperty(LETTER_PART_SEPARATOR_PROPERTY);
    }

    /**
     * Returns the correct appeal for the given gender
     * @param  gender  secretary's gender
     * @return         appeal part of letter
     */
    private String getAppealProperty(Gender gender) {
        return switch (gender) {
            case MALE -> letterProperties.getProperty(APPEAL_MALE_PROPERTY);
            case FEMALE -> letterProperties.getProperty(APPEAL_FEMALE_PROPERTY);
            default -> EMPTY_STRING;
        };
    }

    /**
     * Makes body of the letter
     * @param  removedUrls   set of page URLs that are present only in the yesterday map
     * @param  newUrls       set of page URLs that are present only in the today map
     * @param  modifiedUrls  set of page URLs for which the HTML codes are different
     * @return               body of the letter
     */
    private String makeBody(Set<String> removedUrls, Set<String> newUrls, Set<String> modifiedUrls) {
        if (allSetsAreEmpty(removedUrls, newUrls, modifiedUrls)) {
            return letterProperties.getProperty(NO_CHANGES_BODY_PROPERTY) +
                    letterProperties.getProperty(LETTER_PART_SEPARATOR_PROPERTY);
        }
        return letterProperties.getProperty(CHANGES_BODY_BEGINNING_PROPERTY) +
                letterProperties.getProperty(LETTER_PART_SEPARATOR_PROPERTY) +
                makeUrlListString(letterProperties.getProperty(REMOVED_PAGES_PREFIX_PROPERTY), removedUrls) +
                makeUrlListString(letterProperties.getProperty(NEW_PAGES_PREFIX_PROPERTY), newUrls) +
                makeUrlListString(letterProperties.getProperty(MODIFIED_PAGES_PREFIX_PROPERTY), modifiedUrls);
    }

    /**
     * Checks for emptiness of all sets
     * @param  removedUrls   set of page URLs that are present only in the yesterday map
     * @param  newUrls       set of page URLs that are present only in the today map
     * @param  modifiedUrls  set of page URLs for which the HTML codes are different
     * @return               True if all sets are empty
     */
    private boolean allSetsAreEmpty(Set<String> removedUrls, Set<String> newUrls, Set<String> modifiedUrls) {
        return (removedUrls.isEmpty() && newUrls.isEmpty() && modifiedUrls.isEmpty());
    }

    /**
     * Makes a formatted string containing a list of URLs
     * @param  prefix   the string preceding the list
     * @param  urls     set of URLs
     * @return          part of letter that contains a list of URLs
     */
    private String makeUrlListString(String prefix, Set<String> urls) {
        if (urls.isEmpty()) {
            return EMPTY_STRING;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(prefix);
        for (var url: urls) {
            stringBuilder.append(letterProperties.getProperty(LIST_ELEMENT_PREFIX_PROPERTY))
                    .append(url);
        }
        stringBuilder.append(letterProperties.getProperty(LETTER_PART_SEPARATOR_PROPERTY));
        return stringBuilder.toString();
    }
}
