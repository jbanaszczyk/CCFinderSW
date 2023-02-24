package gemx.ccfinderx;

import java.io.IOException;
import java.nio.file.Path;

public class CCFinderX {

    public static int invokePicosel(
            String tableFile,
            String outputFile,
            String column,
            String[] expressions) {
        throw new RuntimeException();
    }


    private class GemxException extends RuntimeException {
        public GemxException() {
        }
    }

    public void setModuleDirectory(String path) {
        throw new GemxException();
    }

    public int invokeCCFinderX(String[] args) {
        throw new GemxException();
    }

    public int[] getVersion() {
        int[] version =  {20,0,0,};
        return version;
    }

    public void openOfficialSiteTop(String pageSuffix) {
        throw new GemxException();
    }

    public void openOfficialSiteDocumentPage(String pageSuffix, String pageFileName) {
        throw new GemxException();
    }

    public byte[] openPrepFile(String fileName, String suffix) {
        throw new GemxException();
    }

    public void clearPrepFileCacheState() {
        throw new GemxException();
    }

    public String getPythonInterpreterPath() {
        throw new GemxException();
    }

    public static String getApplicationDataPath() {

//        var clazz = MethodHandles.lookup().lookupClass();
//        return CCFinderX.class.getProtectionDomain().getCodeSource().getLocation().getFile();

        try {
            return Path.of(".").toRealPath().toString();
        } catch (IOException e) {
            return System.getProperty("user.dir");
        }


    }

    public int getCurrentProcessId() {
        throw new GemxException();
    }

    public boolean isProcessAlive(int processId) {
        throw new GemxException();
    }


    public static CCFinderX theInstance = new CCFinderX();

}
