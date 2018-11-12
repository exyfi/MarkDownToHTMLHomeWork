package md2html;

import java.io.*;
import java.util.ArrayList;

public class Md2Html2 {
    private static BufferedWriter writer = null;
    private static ArrayList<String> memory = null;

    public static void main(String[] args) {
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1]), "UTF-8"));
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(args[0]), "UTF-8"));
            memory = new ArrayList<>();

            String line = reader.readLine();
            while (line != null) {
                if (line.isEmpty()) {
                    tryWrite();
                    memory.clear();
                } else {
                    memory.add(line);
                }
                line = reader.readLine();
            }

            tryWrite();
            reader.close();
            writer.close();
        } catch (IOException | ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    private static void tryWrite() throws IOException {
        if (memory.isEmpty()) {
            return;
        }

        int lvl = getHeaderLevel(memory.get(0));
        if (lvl > 0) {
            StringBuilder line = new StringBuilder(memory.get(0));
            line.delete(0, lvl + 1);
            memory.set(0, line.toString());
            writeHeader(lvl);
        } else {
            writeParagraph();
        }
        writer.newLine();
    }

    private static int getHeaderLevel(String line) {
        int lvl = 0;
        while (line.charAt(lvl) == '#') {
            ++lvl;
        }
        return lvl * (Character.isWhitespace(line.charAt(lvl)) ? 1 : 0);
    }

    private static void writeHeader(int lvl) throws IOException {
        writer.write("<h" + lvl + ">");
        symbols();
        writer.write("</h" + lvl + ">");
    }

    private static void writeParagraph() throws IOException {
        writer.write("<p>");
        symbols();
        writer.write("</p>");
    }

    private static void symbols() throws IOException {
        int emNum = 0;
        int em_Num = 0;
        int strongNum = 0;
        int strong_Num = 0;
        int sNum = 0;
        int codeNum = 0;
        int uNum = 0;
        int em_Written = 0;
        int em__Written = 0;
        int strong_Written = 0;
        int strong__Written = 0;
        int s_Written = 0;
        int code_Written = 0;
        int u_Written = 0;
        int MarkNum = 0;
        int u_Markk = 0;

        for (String line : memory) {
            for (int ptr = 0; ptr < line.length(); ptr++) {
                if (line.charAt(ptr) == '*') {
                    if (ptr + 1 < line.length() && line.charAt(ptr + 1) == '*') {
                        strongNum++;
                        ptr++;
                    } else {
                        emNum++;
                    }
                } else if (line.charAt(ptr) == '_') {
                    if (ptr + 1 < line.length() && line.charAt(ptr + 1) == '_') {
                        strong_Num++;
                        ptr++;
                    } else {
                        em_Num++;
                    }


                } else if (line.charAt(ptr) == '-') {
                    if (ptr + 1 < line.length() && line.charAt(ptr + 1) == '-') {
                        sNum++;
                        ptr++;
                    }
                } else if (line.charAt(ptr) == '~') {
                    MarkNum++;


                } else if (line.charAt(ptr) == '`') {
                    codeNum++;
                } else if (line.charAt(ptr) == '+') {
                    if (ptr + 1 < line.length() && line.charAt(ptr + 1) == '+') {
                        uNum++;
                        ptr++;
                    }
                } else if (line.charAt(ptr) == '\\') {
                    ptr++;
                }
            }
        }

        emNum = (emNum + strongNum % 2);
        emNum -= emNum % 2;
        strongNum -= strongNum % 2;

        em_Num = (em_Num + strong_Num % 2);
        em_Num -= em_Num % 2;
        strong_Num -= strong_Num % 2;

        sNum -= sNum % 2;
        codeNum -= codeNum % 2;
        uNum -= uNum % 2;
        MarkNum -= MarkNum % 2;


        for (int i = 0; i < memory.size(); ++i) {
            if (i > 0) {
                writer.newLine();
            }

            String line = memory.get(i);
            for (int j = 0; j < line.length(); ++j) {
                switch (line.charAt(j)) {
                    case '*':
                        if (j + 1 < line.length() && line.charAt(j + 1) == '*') {
                            if (strong_Written < strongNum) {
                                if (strong_Written++ % 2 == 0) {
                                    writer.write("<strong>");
                                } else {
                                    writer.write("</strong>");
                                }
                                j++;
                            } else {
                                if (em_Written < emNum) {
                                    if (em_Written++ % 2 == 0) {
                                        writer.write("<em>");
                                    } else {
                                        writer.write("</em>");
                                    }
                                } else {
                                    writer.write(line.charAt(j));
                                }
                            }
                        } else {
                            if (em_Written < emNum) {
                                if (em_Written++ % 2 == 0) {
                                    writer.write("<em>");
                                } else {
                                    writer.write("</em>");
                                }
                            } else {
                                writer.write(line.charAt(j));
                            }
                        }
                        break;
                    case '_':
                        if (j + 1 < line.length() && line.charAt(j + 1) == '_') {
                            if (strong__Written < strong_Num) {
                                if (strong__Written++ % 2 == 0) {
                                    writer.write("<strong>");
                                } else {
                                    writer.write("</strong>");
                                }
                                ++j;
                            } else {
                                if (em__Written < em_Num) {
                                    if (em__Written++ % 2 == 0) {
                                        writer.write("<em>");
                                    } else {
                                        writer.write("</em>");
                                    }
                                } else {
                                    writer.write(line.charAt(j));
                                }
                            }
                        } else {
                            if (em__Written < em_Num) {
                                if (em__Written++ % 2 == 0) {
                                    writer.write("<em>");
                                } else {
                                    writer.write("</em>");
                                }
                            } else {
                                writer.write(line.charAt(j));
                            }
                        }
                        break;

                    case '-':
                        if (j + 1 < line.length() && line.charAt(j + 1) == '-') {
                            if (s_Written < sNum) {
                                if (s_Written++ % 2 == 0) {
                                    writer.write("<s>");
                                } else {
                                    writer.write("</s>");
                                }
                            }
                            j++;
                        } else {
                            writer.write(line.charAt(j));
                        }
                        break;
                    case '~':
                        if (u_Markk < MarkNum) {
                            if (u_Markk++ % 2 == 0) {
                                writer.write("<mark>");
                            } else {
                                writer.write("</mark>");
                            }
                        } else {
                            writer.write(line.charAt(j));
                        }
                        break;
                    case '`':
                        if (code_Written < codeNum) {
                            if (code_Written++ % 2 == 0) {
                                writer.write("<code>");
                            } else {
                                writer.write("</code>");
                            }
                        } else {
                            writer.write(line.charAt(j));
                        }
                        break;
                    case '+':
                        if (j + 1 < line.length() && line.charAt(j + 1) == '+' && u_Written < uNum) {
                            if (u_Written++ % 2 == 0) {
                                writer.write("<u>");
                            } else {
                                writer.write("</u>");
                            }
                            j++;
                        } else {
                            writer.write(line.charAt(j));
                        }
                        break;
                    case '&':
                        writer.write("&amp;");
                        break;
                    case '>':
                        writer.write("&gt;");
                        break;
                    case '<':
                        writer.write("&lt;");
                        break;
                    case '\\':
                        ++j;
                    default:
                        writer.write(line.charAt(j));
                        break;
                }
            }
        }
    }
}