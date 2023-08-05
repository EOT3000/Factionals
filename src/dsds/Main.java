package dsds;

import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws Exception {
        int[] ints = new int[Short.MAX_VALUE];
        Set<String> strings = new HashSet<>();
        Set<String> strings2 = new HashSet<>();
        Set<String> strings3 = new HashSet<>();
        Set<String> strings4 = new HashSet<>();
        Set<String> strings5 = new HashSet<>();
        Set<String> strings6 = new HashSet<>();
        Set<String> strings7 = new HashSet<>();
        Set<String> strings8 = new HashSet<>();

        System.out.println(".");

        InputStream stream = Main.class.getClassLoader().getResourceAsStream("data.txt");

        byte[] bytes = new byte[stream.available()];

        stream.read(bytes);

        String s = new String(bytes);

        {/*for(char c : s.toCharArray()) {
            ints[c]++;
        }

        for(int i = 0; i < ints.length; i++) {
            if(ints[i] != 0) {
                System.out.println(((char) i) + " " + ints[i]);
            }
        }*/}

        String[] array = s.replaceAll("\\n|\\.|,|\\?|\\(|\\)|-|\"", " ").replaceAll("ў", "у").split(" ");

        for(String r : array) {
            if(!strings.contains(r.toLowerCase())) {
                strings.add(r.toLowerCase());
            } else if(!strings2.contains(r.toLowerCase())) {
                strings2.add(r.toLowerCase());
            } else if(!strings3.contains(r.toLowerCase())) {
                strings3.add(r.toLowerCase());
            } else if(!strings4.contains(r.toLowerCase())) {
                strings4.add(r.toLowerCase());
            } else if(!strings5.contains(r.toLowerCase())) {
                strings5.add(r.toLowerCase());
            } else if(!strings6.contains(r.toLowerCase())) {
                strings6.add(r.toLowerCase());
            } else if(!strings7.contains(r.toLowerCase())) {
                strings7.add(r.toLowerCase());
            } else if(!strings8.contains(r.toLowerCase())) {
                strings8.add(r.toLowerCase());
            }
        }

        //System.out.println(strings);
        //System.out.println(strings2);

        System.out.println(strings.size());
        System.out.println(strings2.size());
        System.out.println(strings3.size());
        System.out.println(strings4.size());
        System.out.println(strings5.size());
        System.out.println(strings6.size());
        System.out.println(strings7.size());
        System.out.println(strings8.size());

        for(String t : strings8) {
            System.out.println(t);
        }
    }

}
