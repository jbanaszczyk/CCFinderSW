package gemx.gemx.scatterplothelper;

import gemx.model.ClonesetMetricModel;
import gemx.model.MetricsSummaryData;

public class CloneSetMetricExtractor {
    private final ClonesetMetricModel model;
    private final int metricID;
    private double maxValue;
    private double minValue;
    private double w;

    public CloneSetMetricExtractor(ClonesetMetricModel model, int metricID) {
        this.model = model;
        this.metricID = metricID;
        final MetricsSummaryData summaryData = model.getSummaryData();
        try {
            this.maxValue = summaryData.getMaxValues()[this.metricID];
            this.minValue = summaryData.getMinValues()[this.metricID];
            if (maxValue > minValue) {
                this.w = 1.0 / (maxValue - minValue);
            } else {
                this.w = 0.0;
            }
        } catch (IndexOutOfBoundsException e) {
            this.w = 0.0;
        }
    }

    public static CloneSetMetricExtractor newCloneSetMetricExtractorByID(ClonesetMetricModel model, int metricID)
            throws IndexOutOfBoundsException {
        return new CloneSetMetricExtractor(model, metricID);
    }

    public static CloneSetMetricExtractor newCloneSetMetricExtractorByName(ClonesetMetricModel model, String name)
            throws IndexOutOfBoundsException {
        int metricID = ClonesetMetricModel.fieldNameToID(name);
        return new CloneSetMetricExtractor(model, metricID);
    }

    public double getRatio(long cloneSetID) throws IndexOutOfBoundsException {
        if (this.w != 0.0) {
            double[] metrics = model.getMetricDataOfCloneSet(cloneSetID);
            try {
                double rnr = metrics[this.metricID];
                return (rnr - minValue) * w;
            } catch (IndexOutOfBoundsException e) {
                return 0.0;
            }
        } else {
            return 0.0;
        }
    }
}
