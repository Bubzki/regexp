package bubzki.regexp;

import java.util.*;

public class StringRegExp {
    private final Collection<String> set;

    public StringRegExp() {
        set = new HashSet<>();
    }

    public void add(String s) {
        if (s == null) {
            set.add(s);
        } else {
            set.add(s.toLowerCase(Locale.ROOT));
        }
    }

    public boolean remove(String s) {
        return s == null ? set.remove(s) : set.remove(s.toLowerCase(Locale.ROOT));
    }

    public void removeAll() {
        set.clear();
    }

    public Collection<String> getCollection() {
        return set;
    }

    private interface Filter {
        boolean delegate(String strFromSet, String strCriterion);
    }

    private Iterator<String> iterator(Filter filter, String criterion) {
        if (Objects.isNull(criterion) || criterion.isEmpty()) {
            return set.iterator();
        } else {
            Set<String> tempSet = new HashSet<>();
            for (String temp : set) {
                if (filter.delegate(temp, criterion.toLowerCase(Locale.ROOT))) {
                    tempSet.add(temp);
                }
            }
            return tempSet.iterator();
        }
    }

    public Iterator<String> getStringsContaining(String chars) {
        Filter filter = (str, criterion) -> str != null && str.contains(criterion);
        return iterator(filter, chars);
    }

    public Iterator<String> getStringsStartingWith(String begin) {
        Filter filter = (str, criterion) -> str != null && str.startsWith(criterion);
        return iterator(filter, begin);
    }

    public Iterator<String> getStringsByNumberFormat(String format) {
        Filter filter = (str, criterion) -> {
            if (str != null && str.length() == criterion.length()) {
                String temp = str;
                for (int i = 0; i < 10; ++i) {
                    temp = temp.replace(Character.forDigit(i, 10), '#');
                }
                return temp.equals(criterion);
            } else {
                return false;
            }
        };
        return iterator(filter, format);
    }

    public Iterator<String> getStringsByPattern(String pattern) {
        Filter filter = (str, criterion) -> {
            char wildcard = '*';
            char[] charStr = criterion.toCharArray();
            boolean wildcardStartFlag = charStr[0] == wildcard;
            boolean wildcardEndFlag = charStr[criterion.length() - 1] == wildcard;
            ArrayList<String> partsOfStr = new ArrayList<>();
            StringBuilder temp = new StringBuilder();
            for (char c : charStr) {
                if (c != wildcard) {
                    temp.append(c);
                } else {
                    if (!temp.isEmpty()) {
                        partsOfStr.add(temp.toString());
                        temp.delete(0, temp.length());
                    }
                }
            }
            // if the pattern ends with a wildcard
            if (!wildcardEndFlag && !temp.isEmpty()) {
                partsOfStr.add(temp.toString());
            }
            if (!wildcardStartFlag && !str.startsWith(partsOfStr.get(0))) {
                return false;
            } else if (!wildcardEndFlag && !str.endsWith(partsOfStr.get(partsOfStr.size() - 1))) {
                return false;
            } else {
                int indexOfSearchStart = 0;
                for (String temporary : partsOfStr) {
                    if (str.substring(indexOfSearchStart).contains(temporary)) {
                        indexOfSearchStart = str.indexOf(temporary);
                    } else {
                        return false;
                    }
                }
            }
            return true;
        };
        return iterator(filter, pattern);
    }
}
