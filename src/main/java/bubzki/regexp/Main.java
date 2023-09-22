package bubzki.regexp;

import java.util.Iterator;

public class Main {
    public static void main(String[] args) {
        StringRegExp str1 = new StringRegExp();
        str1.add("distribute");
        str1.add("dibute1");
        str1.add("di1bute");
        str1.add("di1bute1");
        str1.add("dibute");
        str1.add("0distrbute");
        str1.add("d0istrbute");
        str1.add("d0istrbut0e");
        for (Iterator<String> it = str1.getStringsByPattern("d*i****bute****"); it.hasNext(); ) {
            String temp = it.next();
            System.out.println("String: " + temp);
        }

        StringRegExp str2 = new StringRegExp();
        str2.add("11.50");
        str2.add("-1.50");
        str2.add("-11.50");
        str2.add("-21.50");
        str2.add(null);
        str2.add("11.5l");
        str2.add("23.7l");
        for (Iterator<String> it = str2.getStringsByNumberFormat("##.#l"); it.hasNext(); ) {
            String temp = it.next();
            System.out.println("String: " + temp);
        }
    }
}