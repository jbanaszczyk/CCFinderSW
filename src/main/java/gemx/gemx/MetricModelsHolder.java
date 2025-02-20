package gemx.gemx;

import gemx.model.ClonesetMetricModel;
import gemx.model.FileMetricModel;
import gemx.resources.Messages;

import java.io.IOException;

public class MetricModelsHolder {
    private FileMetricModel fileMetricModel;
    private ClonesetMetricModel cloneSetMetricModel;
    private MainWindow mainWindow;

    public MetricModelsHolder(MainWindow mainWindow) {
        fileMetricModel = null;

        if (cloneSetMetricModel != null) {
            cloneSetMetricModel.dispose();
        }
        cloneSetMetricModel = null;
    }

    public void dispose() {
        this.fileMetricModel = null;

        if (cloneSetMetricModel != null) {
            cloneSetMetricModel.dispose();
        }
        cloneSetMetricModel = null;
    }

    public FileMetricModel getFileMetricModel() {
        return this.fileMetricModel;
    }

    public void setFileMetricModel(FileMetricModel fileMetricModel) {
        this.fileMetricModel = fileMetricModel;
    }

    public ClonesetMetricModel getCloneSetMetricModel() {
        return this.cloneSetMetricModel;
    }

    public void setCloneSetMetricModel(ClonesetMetricModel cloneSetMetricModel) {
        this.cloneSetMetricModel = cloneSetMetricModel;
    }

    public void readFileMetricFile(String metricFile, int maxFileID) {
        this.fileMetricModel = null;

        FileMetricModel fmModel = new FileMetricModel();
        try {
            fmModel.readFileMetricFile(metricFile, maxFileID);
        } catch (IOException e) {
            mainWindow.showErrorMessage(Messages.getString("gemx.MainWindow.S_CAN_NOT_READ_FILE_METRIC_FILE"));
        }

        this.fileMetricModel = fmModel;
    }

    public void readCloneSetMetricFile(String metricFile, long maxCloneSetID) {
        this.cloneSetMetricModel = null;

        ClonesetMetricModel cmModel = new ClonesetMetricModel();
        try {
            cmModel.readCloneSetMetricFile(metricFile, maxCloneSetID);
        } catch (IOException e) {
            mainWindow.showErrorMessage(Messages.getString("gemx.MainWindow.S_CAN_NOT_READ_CLONE_SET_METRIC_FILE"));
        }

        this.cloneSetMetricModel = cmModel;
    }

    public void readFileMetricFileAndCloneSetMetricFile(String fileMetricFile, int maxFileID, String clonesetMetricFile, long maxCloneSetID) {
        this.fileMetricModel = null;
        this.cloneSetMetricModel = null;

        FileMetricReader fmReader = new FileMetricReader(fileMetricFile, maxFileID);
        Thread fmThread = new Thread(fmReader);
        fmThread.start();

        ClonesetMetricReader cmReader = new ClonesetMetricReader(clonesetMetricFile, maxCloneSetID);
        Thread cmThread = new Thread(cmReader);
        cmThread.start();

        try {
            fmThread.join();
            cmThread.join();
        } catch (InterruptedException e) {
            mainWindow.showErrorMessage("Interrupted by user.");
            return;
        }

        this.fileMetricModel = fmReader.getModel();
        if (this.fileMetricModel == null) {
            mainWindow.showErrorMessage(Messages.getString("gemx.MainWindow.S_CAN_NOT_READ_FILE_METRIC_FILE"));
        }

        this.cloneSetMetricModel = cmReader.getModel();
        if (this.cloneSetMetricModel == null) {
            mainWindow.showErrorMessage(Messages.getString("gemx.MainWindow.S_CAN_NOT_READ_CLONE_SET_METRIC_FILE"));
        }
    }

    private static class FileMetricReader implements Runnable {
        private final String metricFile;
        private final int maxFileID;
        private FileMetricModel fmModel;

        public FileMetricReader(String metricFile, int maxFileID) {
            this.metricFile = metricFile;
            this.maxFileID = maxFileID;
        }

        public void run() {
            fmModel = new FileMetricModel();
            try {
                fmModel.readFileMetricFile(metricFile, maxFileID);
            } catch (IOException e) {
                fmModel = null;
            }
        }

        public FileMetricModel getModel() {
            return fmModel;
        }
    }

    private static class ClonesetMetricReader implements Runnable {
        private final String metricFile;
        private final long maxCloneSetID;
        private ClonesetMetricModel cmModel;

        public ClonesetMetricReader(String metricFile, long maxCloneSetID) {
            this.metricFile = metricFile;
            this.maxCloneSetID = maxCloneSetID;
        }

        public void run() {
            cmModel = new ClonesetMetricModel();
            try {
                cmModel.readCloneSetMetricFile(metricFile, maxCloneSetID);
            } catch (IOException e) {
                cmModel = null;
            }
        }

        public ClonesetMetricModel getModel() {
            return cmModel;
        }
    }
}

