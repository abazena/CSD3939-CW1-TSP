package utils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Parser {


        public String loadTxt(String path, Charset encoding) throws IOException {
            byte[] encoded = Files.readAllBytes(Paths.get(path));
            return new String(encoded, encoding);
        }

        public String[][] parseArray2D(String text, String regex) {
            String[] lines = text.split("\\r?\\n");
            int temp = lines.length;
            String[][] parsedArray = new String[temp][3];

            for(int x = 0; x < lines.length; ++x) {
                List<String> list = Arrays.asList(lines[x].split(regex));

                for(int i = 0; i < list.size(); ++i) {
                    parsedArray[x][i] = (String)list.get(i);
                }
            }

            return parsedArray;
        }

        public void print2D(String[][] array) {
            for(int i = 0; i < array.length; ++i) {
                for(int j = 0; j < array[i].length; ++j) {
                    System.out.print("/" + array[i][j]);
                    if (j == 2) {
                        System.out.println();
                    }
                }

                System.out.println("-------------------------");
            }

        }
    }