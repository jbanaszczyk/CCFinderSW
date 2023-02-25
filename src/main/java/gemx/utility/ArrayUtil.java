package gemx.utility;

import gnu.trove.TIntArrayList;
import gnu.trove.TLongArrayList;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class ArrayUtil {
    public static long[] slice(long[] array, int begin, int end) {
        assert begin >= 0;
        assert begin <= end;
        assert end <= array.length;

        if (begin == 0 && end == array.length) {
            return array;
        }

        long[] sliced = new long[end - begin];
        int pos = 0;
        for (int i = begin; i < end; ++i) {
            sliced[pos++] = array[i];
        }
        return sliced;
    }

    public static int[] slice(int[] array, int begin, int end) {
        assert begin >= 0;
        assert begin <= end;
        assert end <= array.length;

        if (begin == 0 && end == array.length) {
            return array;
        }

        int[] sliced = new int[end - begin];
        int pos = 0;
        for (int i = begin; i < end; ++i) {
            sliced[pos++] = array[i];
        }
        return sliced;
    }

    public static double[] slice(double[] array, int begin, int end) {
        assert begin >= 0;
        assert begin <= end;
        assert end <= array.length;

        if (begin == 0 && end == array.length) {
            return array;
        }

        double[] sliced = new double[end - begin];
        int pos = 0;
        for (int i = begin; i < end; ++i) {
            sliced[pos++] = array[i];
        }
        return sliced;
    }

    public static TIntArrayList readIntList(String path) throws
            IOException, NumberFormatErrorAtLineOfFile {
        TIntArrayList ary = new TIntArrayList();

        long lineNumber = 0;
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8));
        String line;
        while ((line = in.readLine()) != null) {
            ++lineNumber;
            int value;
            try {
                value = Integer.parseInt(line);
            } catch (NumberFormatException e) {
                throw new NumberFormatErrorAtLineOfFile(lineNumber, path);
            }
            ary.add(value);
        }
        in.close();

        return ary;
    }

    public static TLongArrayList readLongList(String path) throws
            IOException, NumberFormatErrorAtLineOfFile {
        TLongArrayList ary = new TLongArrayList();

        long lineNumber = 0;
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8));
        String line;
        while ((line = in.readLine()) != null) {
            ++lineNumber;
            long value;
            try {
                value = Long.parseLong(line);
            } catch (NumberFormatException e) {
                throw new NumberFormatErrorAtLineOfFile(lineNumber, path);
            }
            ary.add(value);
        }
        in.close();

        return ary;
    }

    public static void iterateOnIntList(String path, IterateOnIntListClosure closure) throws
            IOException, NumberFormatErrorAtLineOfFile {
        long lineNumber = 0;
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8));
        String line;
        while ((line = in.readLine()) != null) {
            ++lineNumber;
            int value;
            try {
                value = Integer.parseInt(line);
                closure.action(value);
            } catch (NumberFormatException e) {
                throw new NumberFormatErrorAtLineOfFile(lineNumber, path);
            }
        }
        in.close();
    }

    public static void iterateOnLongList(String path, IterateOnLongListClosure closure) throws
            IOException, NumberFormatErrorAtLineOfFile {
        long lineNumber = 0;
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8));
        String line;
        while ((line = in.readLine()) != null) {
            ++lineNumber;
            long value;
            try {
                value = Integer.parseInt(line);
                closure.action(value);
            } catch (NumberFormatException e) {
                throw new NumberFormatErrorAtLineOfFile(lineNumber, path);
            }
        }
        in.close();
    }

    public interface IterateOnIntListClosure {
        void action(int value);
    }

    public interface IterateOnLongListClosure {
        void action(long value);
    }

    public static class NumberFormatErrorAtLineOfFile extends NumberFormatException {
        private static final long serialVersionUID = 4674438542125456192L;
        public long lineNumber;
        public String path;

        public NumberFormatErrorAtLineOfFile(long lineNumber, String path) {
            super(String.format("Invalid number at line %d, file %s", lineNumber, path));
            this.lineNumber = lineNumber;
            this.path = path;
        }
    }
}
