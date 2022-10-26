package com.epam.rd.autotasks.words;

import java.util.*;

public class StringUtil {
    public static int countEqualIgnoreCaseAndSpaces(String[] words, String sample) {
        if (words == null || sample == null || words.length == 0)
            return 0;
        int res = 0;
        for (int i = 0; i < words.length; i++) {
            if (words[i].strip().equalsIgnoreCase(sample.strip()))
                res++;
        }
        return res;
    }

    public static String[] splitWords(String text) {
        if (text == null || text.isBlank())
            return null;
        StringTokenizer st = new StringTokenizer(text, ",.:; ?!");
        if (!st.hasMoreTokens())
            return null;
        String[] mas = new String[st.countTokens()];
        int pos = 0;
        while (st.hasMoreTokens()) {
            mas[pos++] = st.nextToken();
        }
        return mas;
    }

    public static String convertPath(String path, boolean toWin) {
        if (path == null || path.isBlank())
            return null;
        path = path.strip();
        int a = path.indexOf('~');
        if (a > 0)
            return null;
        int b = path.indexOf("C:");
        if (b > 0)
            return null;
        int c = path.indexOf("\\");
        int d = path.indexOf('/');

        if ((a >= 0 && c >= 0) ||
                (b >= 0 && d >= 0) ||
                (c >= 0 && d >= 0))
            return null;

        if (path.indexOf("C:", b + 1) != -1)
            return null;
        if (path.indexOf('~', a + 1) != -1)
            return null;

        if (toWin) {
            if (d == -1 && a == -1) {
                return path;
            } else {
                if (d != -1) {
                    path = path.replaceAll("/", "\\\\");
                }
                if(a != -1){
                    path.replace("~", "C:\\User");
                }
                else{
                    if(path.charAt(0) != '/'){
                        return path;
                    }
                    path = path.replace("/", "C:");
                }
                return path;
            }
        } else {
            if (b == -1 && c == -1) {
                return path;
            } else {
                if (b != -1) {
                    path = path.replace("C:", "~");
                }
                if (c != -1) {
                    path = path.replaceAll("\\\\", "/");
                }
                return path;
            }
        }


    }

    public static String joinWords(String[] words) {
        if (words == null || words.length == 0)
            return null;
        ArrayList<String> t = new ArrayList<>();
        for (int i = 0; i < words.length; i++) {
            if (words[i].strip() != "")
                t.add(words[i]);
        }
        if (t.size() == 0)
            return null;
        return t.toString();
    }

    public static void main(String[] args) {
        System.out.println("Test 1: countEqualIgnoreCaseAndSpaces");
        String[] words = new String[]{" WordS    \t", "words", "w0rds", "WOR  DS",};
        String sample = "words   ";
        int countResult = countEqualIgnoreCaseAndSpaces(words, sample);
        System.out.println("Result: " + countResult);
        int expectedCount = 2;
        System.out.println("Must be: " + expectedCount);

        System.out.println("Test 2: splitWords");
        String text = "   ,, first, second!!!! third";
        String[] splitResult = splitWords(text);
        System.out.println("Result : " + Arrays.toString(splitResult));
        String[] expectedSplit = new String[]{"first", "second", "third"};
        System.out.println("Must be: " + Arrays.toString(expectedSplit));

        System.out.println("Test 3: convertPath");
        String unixPath = "/some/unix/path";
        String convertResult = convertPath(unixPath, true);
        System.out.println("Result: " + convertResult);
        String expectedWinPath = "C:\\some\\unix\\path";
        System.out.println("Must be: " + expectedWinPath);

        System.out.println("Test 4: joinWords");
        String[] toJoin = new String[]{"go", "with", "the", "", "FLOW"};
        String joinResult = joinWords(toJoin);
        System.out.println("Result: " + joinResult);
        String expectedJoin = "[go, with, the, FLOW]";
        System.out.println("Must be: " + expectedJoin);
    }
}