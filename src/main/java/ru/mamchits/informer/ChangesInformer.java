package ru.mamchits.informer;

import ru.mamchits.informer.util.ChangesDetector;
import ru.mamchits.informer.util.LetterMaker;

import java.util.Map;
import java.util.Set;

/**
 * ChangesInformer is class for informing secretaries about changes on the pages assigned to them
 */
public class ChangesInformer {
    private Set<String> newPages;
    private Set<String> removedPages;
    private Set<String> modifiedPages;

    private final LetterMaker letterMaker;

    /**
     * Constructor
     * @param  letterTemplatePropertiesPath  letter properties filepath
     */
    public ChangesInformer(String letterTemplatePropertiesPath) {
        letterMaker = new LetterMaker(letterTemplatePropertiesPath);
    }

    /**
     * Informs the secretary about page changes
     * @param  gender                        secretary's gender
     * @param  name                          secretary's name
     * @param  patronymic                    secretary's patronymic
     */
    public void inform(LetterMaker.Gender gender, String name, String patronymic) {
        String letter = letterMaker.make(gender, name, patronymic, removedPages, newPages, modifiedPages);
        System.out.println(letter); // potentially send email
    }

    /**
     * updates changes in maps to inform about them later
     * @param  prevMap  previous page states
     * @param  curMap   previous page states
     */
    public void updateChanges(Map<String, String> prevMap, Map<String, String> curMap) {
        newPages = ChangesDetector.getNewUrls(prevMap, curMap);
        removedPages = ChangesDetector.getRemovedUrls(prevMap, curMap);
        modifiedPages = ChangesDetector.getModifiedUrls(prevMap, curMap);
    }
}
