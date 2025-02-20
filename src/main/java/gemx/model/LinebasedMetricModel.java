package gemx.model;

import gemx.utility.StringUtil;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class LinebasedMetricModel {
    private static final String[] predefinedMetricNames = new String[]{
            "LOC", "SLOC", "CLOC", "CVRL"
    };
    private int maxFileID;
    private String[] metricNames;
    private double[] values;
    private boolean[] isValidValue;
    private boolean[] isFloatingPoints;
    private int fields;
    private MetricsSummaryData summaryData;

    public static String fieldIDToName(int field) throws IndexOutOfBoundsException {
        return predefinedMetricNames[field];
    }

    public static int fieldNameToID(String name) throws IndexOutOfBoundsException {
        int field = java.util.Arrays.binarySearch(predefinedMetricNames, name);
        if (field >= 0) {
            return field;
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    public static String[] getFieldNames() {
        return predefinedMetricNames.clone();
    }

    public void readLinebasedMetricFile(String path, int maxFileID) throws DataFileReadError, IOException {
        this.maxFileID = maxFileID;
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8));
        String line;
        line = reader.readLine();
        String supposedTitleLine = "FID" + "\t" + StringUtil.join(LinebasedMetricModel.getFieldNames(), "\t");
        if (!line.equals(supposedTitleLine)) {
            throw new DataFileReadError("invalid file metric file");
        }

        String[] ss = StringUtil.split(line, '\t');
        fields = ss.length - 1;
        metricNames = new String[fields];
        System.arraycopy(ss, 1, metricNames, 0, fields);

        values = new double[(maxFileID + 1) * fields];
        isValidValue = new boolean[maxFileID + 1];
        isFloatingPoints = new boolean[fields];
        for (int i = 0; i < fields; ++i) {
            isFloatingPoints[i] = false;
        }
        while (true) {
            line = reader.readLine();
            if (line == null) {
                throw new DataFileReadError("Invalid line-based metric file");
            }
            String[] subs = StringUtil.split(line, '\t');
            String subs0 = subs[0];
            if (subs0.equals("ave.") || subs0.equals("total")) {
                break; // while
            }
            if (subs.length != fields + 1) {
                throw new DataFileReadError("invalid file metric file");
            }
            int id = Integer.parseInt(subs[0]);
            if (!(0 <= id && id <= maxFileID)) {
                throw new DataFileReadError("Invalid File ID");
            }
            isValidValue[id] = true;
            int valuePos = id * fields;
            for (int i = 0; i < fields; ++i) {
                double v = Double.parseDouble(subs[i + 1]);
                if (Math.floor(v) != v) {
                    isFloatingPoints[i] = true;
                }
                values[valuePos + i] = v;
            }
        }

        summaryData = new MetricsSummaryData();
        summaryData.read(fields, reader, line);

        reader.close();
    }

    public double[] getMetricDataOfFile(int fileID) throws IndexOutOfBoundsException {
        if (!(0 <= fileID && fileID < maxFileID) && !isValidValue[fileID]) {
            throw new IndexOutOfBoundsException();
        }
        return gemx.utility.ArrayUtil.slice(values, fileID * fields, fileID * fields + fields).clone();
    }

    public double getNthMetricDataOfFile(int fileID, int fieldIndex) throws IndexOutOfBoundsException {
        if (!(0 <= fileID && fileID < maxFileID) && !isValidValue[fileID]) {
            throw new IndexOutOfBoundsException();
        }
        if (!(0 <= fieldIndex && fieldIndex < fields)) {
            throw new IndexOutOfBoundsException();
        }
        return values[fileID * fields + fieldIndex];
    }

    public int getFieldCount() {
        return fields;
    }

    public boolean isFlotingPoint(int metricIndex) {
        assert 0 <= metricIndex && metricIndex < isFloatingPoints.length;
        return isFloatingPoints[metricIndex];
    }

    public boolean[] getFlotingPointValueMap() {
        return isFloatingPoints.clone();
    }

    public String getMetricName(int metricIndex) {
        assert 0 <= metricIndex && metricIndex < metricNames.length;
        return metricNames[metricIndex];
    }

    public String[] getMetricNames() {
        return metricNames.clone();
    }

    public MetricsSummaryData getSummaryData() {
        return summaryData.clone();
    }

    public double[] getRelativeMetricDataOfFile(int fileID) throws IndexOutOfBoundsException {
        if (!(0 <= fileID && fileID < maxFileID) && !isValidValue[fileID]) {
            throw new IndexOutOfBoundsException();
        }
        double[] vs = new double[fields];
        for (int i = 0; i < fields; ++i) {
            vs[i] = summaryData.toRelativeValue(i, values[fileID * fields + i]);
        }
        return vs;
    }

    public double getNthRelativeMetricDataOfFile(int fileID, int fieldIndex) throws IndexOutOfBoundsException {
        if (!(0 <= fileID && fileID < maxFileID) && !isValidValue[fileID]) {
            throw new IndexOutOfBoundsException();
        }
        if (!(0 <= fieldIndex && fieldIndex < fields)) {
            throw new IndexOutOfBoundsException();
        }
        return summaryData.toRelativeValue(fieldIndex, values[fileID * fields + fieldIndex]);
    }
}
