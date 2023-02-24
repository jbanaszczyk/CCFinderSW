package gemx.utility;

import java.io.File;
import java.net.URL;

public class ExecutionModuleDirectory {
    private static final String StrJar = "jar:";
    private static final String StrFile = "file:";

    private static String decodePercentThingsInURL(String str) {
        return str.replaceAll("%20", " ");
    }

    public static String get() {


        Class<ExecutionModuleDirectory> clazz = ExecutionModuleDirectory.class;
        String clazzName = clazz.getName().replace('.', '/') + ".class";
        URL url = clazz.getResource("/" + clazzName);
        String s = decodePercentThingsInURL(url.toString());
        String dirStr = null;
        if (s.startsWith(StrJar)) {
            if (s.endsWith("!" + "/" + clazzName)) {
                File f = new File(s.substring(StrJar.length(), s.length() - (clazzName.length() + 2)));
                dirStr = f.getParent().substring(StrFile.length());
            }
        } else if (s.startsWith(StrFile)) {
            File f = new File(s);
            dirStr = f.getParent().substring(StrFile.length(), s.length() - (clazzName.length() + 1));
        }
        if (dirStr != null) {
            if (dirStr.length() > 3 && dirStr.charAt(0) == '\\' && dirStr.charAt(2) == ':') {
                return dirStr.substring(1);
            } else {
                return dirStr;
            }
        }
        return null; // error!

    }

}
