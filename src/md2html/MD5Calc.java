package md2html;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MD5Calc {
    private static BufferedReader reader;
    private static BufferedWriter writer;
    private static List<String> paragraph;
    public static void main(String[] args) throws IOException {
        reader = new BufferedReader(new InputStreamReader(new FileInputStream(args[0]), "UTF-8"));
        writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1]), "UTF-8"));
        paragraph = new ArrayList<>();

        String line;
        while ((line = reader.readLine()) != null) {
            if (line.isEmpty()) {
                paragraph.clear();
            } else {
                paragraph.add(line);
            }
        }

        reader.close();
        writer.close();
    }
}
