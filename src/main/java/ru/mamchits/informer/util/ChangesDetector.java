package ru.mamchits.informer.util;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * ChangeDetector is utility class for detecting URLs for which some changes have occurred
 */
public final class ChangesDetector {
    /**
     * Since ChangeDetector is utility class consisting solely of static methods,
     * we will prohibit the creation of class instances
     */
    private ChangesDetector() {}

    /**
     * Returns URLs whose HTML code is present only in the curMap
     * @param  prevMap  previous page states
     * @param  curMap   current page states
     * @return          set of page URLs that are present only in the curMap
     */
    public static Set<String> getNewUrls(Map<String, String> prevMap, Map<String, String> curMap) {
        Set<String> prevUrls = new HashSet<>(prevMap.keySet());
        Set<String> curUrls = new HashSet<>(curMap.keySet());
        curUrls.removeAll(prevUrls);
        return curUrls;
    }

    /**
     * Returns URLs whose HTML code is present only in the prevMap
     * @param  prevMap  previous page states
     * @param  curMap   current page states
     * @return          set of page URLs that are present only in the prevMap
     */
    public static Set<String> getRemovedUrls(Map<String, String> prevMap, Map<String, String> curMap) {
        Set<String> prevUrls = new HashSet<>(prevMap.keySet());
        Set<String> curUrls = new HashSet<>(curMap.keySet());
        prevUrls.removeAll(curUrls);
        return prevUrls;
    }

    /**
     * Returns URLs whose current and previous HTML code are different but both exist
     * @param  prevMap  previous page states
     * @param  curMap   current page states
     * @return          set of page URLs for which the HTML codes are different
     */
    public static Set<String> getModifiedUrls(Map<String, String> prevMap, Map<String, String> curMap) {
        Set<String> stayedUrls = getStayedUrls(prevMap, curMap);
        stayedUrls.removeIf(url -> prevMap.get(url).equals(curMap.get(url)));
        return stayedUrls;
    }

    /**
     * Returns URLs whose current and previous HTML code are the same
     * @param  prevMap  previous page states
     * @param  curMap   current page states
     * @return          set of page URLs for which the HTML codes are the same in both states
     */
    private static Set<String> getStayedUrls(Map<String, String> prevMap, Map<String, String> curMap) {
        Set<String> prevUrls = new HashSet<>(prevMap.keySet());
        Set<String> curUrls = new HashSet<>(curMap.keySet());
        prevUrls.retainAll(curUrls);
        return prevUrls;
    }
}
