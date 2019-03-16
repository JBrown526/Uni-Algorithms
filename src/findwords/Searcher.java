package findwords;

import java.util.ArrayList;

/**
 * Your implementation of the coursework.
 * This is the only source file you should modify, and the only one you
 * should submit.  The signatures of these methods should not be modified.
 */
public class Searcher {

    /**
     * Compare the front part of two character arrays for equality.
     *
     * @param s the first character array
     * @param t the second character array
     * @param n number of characters to compare
     * @return true if s and t are equal up to the first n characters
     */
    public boolean equal(String s, String t, int n) {
        int sLen = s.length();
        int tLen = t.length();
        if (sLen < n) {
            if (sLen == tLen) {
                return equalsCheck(s, t, sLen);
            } else {
                return false;
            }
        } else {
            if (tLen < n) {
                return false;
            } else {
                return equalsCheck(s, t, n);
            }
        }
    }

    private boolean equalsCheck(String s, String t, int n) {
        //
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) != t.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Compare the front part of two character arrays.
     *
     * @param s the first character array
     * @param t the second character array
     * @param n number of characters to compare
     * @return true if s is less than t in the first n characters
     */
    public boolean lessThan(String s, String t, int n) {
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) < t.charAt(i)) {
                return true;
            } else if (s.charAt(i) > t.charAt(i)) {
                return false;
            }
            if (i >= s.length() - 1) {
                return !(i == t.length() - 1);
            }
        }
        return false;
    }

    /**
     * Find the first position of a prefix in a dictionary.
     *
     * @param d an ordered dictionary of words
     * @param w a prefix to search for
     * @param n number of characters to compare
     * @return the least index such that all earlier entries in the dictionary
     * are smaller than e when comparing the first n characters.
     */
    public int findPrefix(Dictionary d, String w, int n) {
        int lo = 0;
        int hi = d.size() - 1;
        int mid;
        while (lo <= hi) {
            mid = (lo + hi) / 2;
            if (lessThan(d.getWord(mid), w, n)) {
                lo = mid + 1;
            } else {
                hi = mid - 1;
            }
        }
        return lo;
    }

    /**
     * Search a dictionary for words matching a clue.
     *
     * @param d    an ordered dictionary of words
     * @param clue a word to search for, with . standing for any character
     * @return a list of all the words in the dictionary that match the clue
     */
    public ArrayList<String> findMatches(Dictionary d, String clue) {
        ArrayList<String> matches = new ArrayList<>();
        ArrayList<Integer> check = new ArrayList<>();
        int prefixLength = 0;
        boolean prefixCheck = true;

        for (int i = 0; i < clue.length(); i++) {
            if (clue.charAt(i) != '.') {
                check.add(i);
            } else {
                if (prefixCheck) {
                    prefixLength = i;
                }
                prefixCheck = false;
            }
        }
        System.out.println(check);
        System.out.println(prefixLength);

        int searchPosition = findPrefix(d, clue, prefixLength);
        System.out.println(searchPosition);

        while (equal(clue, d.getWord(searchPosition), prefixLength) && searchPosition < d.size() - 1) {
            String dictionaryEntry = d.getWord(searchPosition);
            boolean equal = true;
            if (clue.length() == dictionaryEntry.length()) {
                for (int index : check) {
                    if (equal) {
                        if (index < dictionaryEntry.length()) {
                            equal = clue.charAt(index) == dictionaryEntry.charAt(index);
                        } else {
                            equal = false;
                        }
                    }
                }
                if (equal) {
                    matches.add(dictionaryEntry);
                }
            }
            searchPosition++;
        }
        System.out.println(matches.size());
        return matches;
    }
}
