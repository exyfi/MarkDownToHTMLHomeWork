import java.util.*;


public class StringHash {
    private static final int NCHAR = 3;
    private static final char[] chars = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
            'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
            'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
    };
    private static final int ALPHAS = chars.length;
    public static void main(String[] args) {
        Map<Integer,Collection<String>> map = new HashMap<>();
        int[] index = new int[NCHAR];
        char[] buf = new char[NCHAR];
        while (true) {
            for (int i = 0; i < NCHAR; ++i) {
                buf[i] = chars[index[i]];
            }
            String str = new String(buf);
            int hash = str.hashCode();
            Collection<String> strings = map.get(hash);
            if (strings == null) {
                strings = new LinkedList<String>();
                strings.add(str);
                map.put(hash, strings);
            } else {
                strings.add(str);
            }
            int carry = 1;
            for (int i = 0; i < NCHAR; ++i) {
                index[i] = index[i] + carry;
                carry = index[i] / ALPHAS;
                index[i] %= ALPHAS;
            }
            if (carry > 0) break;
        }

        for (Map.Entry<Integer,Collection<String>> group : map.entrySet()) {
            Collection<String> strings = group.getValue();
            if (strings.size() >= 9) {
                System.out.println("" + group.getKey() + ":");
                for (String str: strings) {
                    System.out.println("\t" + str);
                }
            }
        }
    }
}