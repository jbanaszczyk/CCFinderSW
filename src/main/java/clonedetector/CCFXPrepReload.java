package clonedetector;

import clonedetector.classlist.Token;
import common.FileAndString;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static common.TokenName.*;

public class CCFXPrepReload {

    private final String directoryName;
    private final String language;
    public ArrayList<Token> tokenList = new ArrayList<>();

    public CCFXPrepReload(String dn, String language) {
        directoryName = dn;
        this.language = language;
    }

    public void reload(String filename) {
        String dirname = directoryName + File.separator + ".ccfxprepdir";
        filename = dirname + filename.substring(directoryName.length()) + "." + language + ".2_0_0_2.default.ccfxprep";

        String str = "";
        try {
            str = FileAndString.readAll(filename);
        } catch (IOException e) {
            System.out.println("Exception : " + filename);
        }
        String[] split = str.split("\r\n?|\n");

        for (String x : split) {
            if (x.equals("")) {
                continue;
            }
            String[] tab = x.split("\t");
            //left
            String[] num = tab[0].split("\\.");
            int line = Integer.parseInt(num[0], 16);
            int clm = Integer.parseInt(num[1], 16);
            //int sum = Integer.parseInt(num[2], 16);

            //center
            int line2 = line;
            int clm2;
            //int sum2 = sum;
            if (tab[1].charAt(0) == '+') {
                clm2 = clm + Integer.parseInt(tab[1].substring(1), 16);
                //sum2 = sum + Integer.parseInt(tab[1].substring(1), 16);
            } else {
                String[] num2 = tab[1].split("\\.");
                line2 = Integer.parseInt(num2[0], 16);
                clm2 = Integer.parseInt(num2[0], 16);
                //sum2 = Integer.parseInt(num2[0], 16);
            }

            //right
            String value;
            String three = tab[2];
            int type;
            if (three.length() > 3 && three.startsWith("id|")) {
                type = IDENTIFIER;
                value = three.substring(3);
            } else if (three.length() > 2 && three.startsWith("r_")) {
                type = RESERVE;
                value = three.substring(2);
            } else if (three.length() > 2 && three.startsWith("l_")) {
                if (three.substring(0, three.indexOf("|")).equals("l_string")) {
                    type = STRING;
                } else {
                    type = NUMBER;
                }
                value = three.substring(2);
            } else if (three.length() > 2 && three.startsWith("s_")) {
                type = SYMBOL;
                value = three.substring(2);
            } else {
                type = SYMBOL;
                switch (three) {
                    case "suffix:semicolon":
                        value = ";";
                        break;
                    case "(paren":
                        value = "(";
                        break;
                    case ")paren":
                        value = ")";
                        break;
                    case "(brace":
                        value = "{";
                        break;
                    case ")brace":
                        value = "}";
                        break;
                    default:
                        value = three;
                        type = ZERO;
                        break;
                }
            }

            //System.out.println(value + " " + new Token(str, line, clm, line2, clm2, type).toString());
            tokenList.add(new Token(value, line, clm, line2, clm2, type));
        }
    }
}
