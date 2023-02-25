package common;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class FileAndString {

    /*
    A method that reads the entire text file and returns a string
    https://qiita.com/penguinshunya/items/353bb1c555f337b0cf6d
     **/
    public static String readAll(final String path) throws IOException {
        return readAll(path, "UTF-8");
    }

    public static String readAll(final String path, final String charset) throws IOException {
        return Files.lines(Paths.get(path), Charset.forName(charset)).collect(Collectors.joining(System.lineSeparator()));
    }

    public static void writeAll(final String path, String str) {
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), StandardCharsets.UTF_8), 1048576)) {
            bw.write(str);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void writeAllAppend(final String path, String str) {
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path, true), StandardCharsets.UTF_8), 1048576)) {
            bw.write(str);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static ArrayList<String> searchDirectory(String directory) {// normal use
        ArrayList<String> list = new ArrayList<>();
        File cDirectory = new File(directory);
        if (!cDirectory.exists()) {
            System.out.println("failed to find directory:" + directory);
            System.exit(1);
        }
        File[] fileList = cDirectory.listFiles();
        assert fileList != null;
        for (File aFileList : fileList) {
            list.add(aFileList.getPath());
            if (aFileList.isDirectory()) {
                list.addAll(searchDirectory(aFileList.getPath()));
            }
        }
        return list;
    }
}
