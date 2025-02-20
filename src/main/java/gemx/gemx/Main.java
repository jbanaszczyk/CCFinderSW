package gemx.gemx;

import gemx.ccfinderx.CCFinderX;
import gemx.constants.ApplicationVersion;
import gemx.constants.CcfxDefaultSettings;
import gemx.constants.GemXDefaultSettings;
import gemx.gemx.dialogs.AboutDialog;
import gemx.gemx.scatterplothelper.PlottingColors;
import gemx.resources.Messages;
import gemx.resources.MetricColors;
import gemx.resources.TextColors;
import gemx.utility.TemporaryFileManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Properties;

public class Main {
    private static final String sVerMajor = "verMajor";
    private static final String sVerMinor1 = "verMinor1";
    private static final String sVerMinor2 = "verMinor2";
    private static final String sSettingPreprocessor = "settingPreprocessor";
    private static final String sSettingMinimumCloneLength = "settingMinimumCloneLength";
    private static final String sSettingMinimumTKS = "settingMinimumTKS";
    private static final String sSettingEncoding = "settingEncoding";
    private static final String sSettingChunkSize = "settingChunkSize";
    private static final String sSettingShaperLevel = "settingShaperLevel";
    private static final String sSettingUsePMatch = "settingUsePMatch";
    private static final String sSettingUsePreprocessCache = "settingUsePreprocessCache";
    private static final String sSettingMaxWorkerThreads = "settingMaxWorkerThreads";
    private static final String sSettingCalcFileMetricAlways = "settingCalcFileMetricAlways";
    private static final String sSettingCalcCloneMetricAlways = "settingCalcCloneMetricAlways";
    private static final String sSettingResizeScatterPlot = "settingResizeScatterPlot";
    private static final String sSettingNtipleSourceTextPane = "settingNtipleSourceTextPane";
    private static final String sSettingAllFileViewModeEnabled = "settingAllFileViewModeEnabled";
    private static final String sSettingColorSchemeFile = "settingColorSchemeFile";
    private static final String sSettingClonesetTableClickToShowPair = "settingClonesetTableClickToShowPair";
    private static final String sSettingUsePrescreening = "settingUsePrescreening";
    private static final String sSettingResetScopeItemInContextMenus = "settingResetScopeItemInContextMenus";
    private static String propertyFile = "gemx.properties";

    static {
        String path = CCFinderX.getApplicationDataPath();
        if (path.length() > 0) {
            propertyFile = path + java.io.File.separator + "gemx.properties";
        }
    }

    public String settingPreprocessor = CcfxDefaultSettings.initPreprocessor;
    public int settingMinimumCloneLength = CcfxDefaultSettings.initMinimumCloneLength;
    public int settingMinimumTKS = CcfxDefaultSettings.initMinimumTKS;
    public String settingEncoding = CcfxDefaultSettings.initEncoding;
    public int settingChunkSize = CcfxDefaultSettings.initChunkSize;
    public int settingShaperLevel = CcfxDefaultSettings.initShaperLevel;
    public boolean settingUsePMatch = CcfxDefaultSettings.initUsePMatch;
    public boolean settingUsePreprocessCache = CcfxDefaultSettings.initUsePreprocessCache;
    public int settingMaxWorkerThreads = CcfxDefaultSettings.initMaxWorkerThreads;
    public boolean settingCalcFileMetricAlways = GemXDefaultSettings.initCalcFileMetricAlways;
    public boolean settingCalcCloneMetricAlways = GemXDefaultSettings.initCalcCloneMetricAlways;
    public boolean settingResizeScatterPlot = GemXDefaultSettings.initResizeScatterPlot;
    public int settingNtipleSourceTextPane = GemXDefaultSettings.initNtipleSourceTextPane;
    public boolean settingAllFileViewModeEnabled = GemXDefaultSettings.initAllFileViewModeEnabled;
    public String settingColorSchemeFile = GemXDefaultSettings.initColorSchemeFile;
    public boolean settingClonesetTableClickToShowPair = GemXDefaultSettings.initClonesetTableClickToShowPair;
    public boolean settingUsePrescreening = CcfxDefaultSettings.initUsePrescreening;
    public boolean settingResetScopeItemInContextMenus = GemXDefaultSettings.initResetScopeItemInContextMenus;

    private static boolean parseBooleanLikeThing(String str) {
        try {
            int i = Integer.parseInt(str);
            return i != 0;
        } catch (NumberFormatException e) {
            boolean b = Boolean.parseBoolean(str);
            return b;
        }
    }

    private static boolean makeDirectoryIfNotExist(String file) {
        File targetFile = new File(file);
        File dir = targetFile.getParentFile();
        if (dir == null) {
            // the file is on current directory, so no need to make the directory.
            return true;
        }

        if (!dir.exists()) {
            return dir.mkdirs();
        }

        return true;
    }

    private static void scan_command(String[] args, MainWindow mainWindow) {
        String cloneDataFile = null;
        for (int i = 0; i < args.length; ++i) {
            String argi = args[i];
            if (argi.equals("-open")) {
                cloneDataFile = args[1];
                break;
            }
        }
        if (cloneDataFile != null) {
            mainWindow.do_open_clone_data_file_i(cloneDataFile);
        }
    }

    private static String readColorConfigFile() {
        final String fileName = CCFinderX.getApplicationDataPath() + "/colors.json";
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), StandardCharsets.UTF_8));
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                builder.append(line);
            }
            in.close();
            return builder.toString();
        } catch (UnsupportedEncodingException e) {
            return null;
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            return null;
        }
    }

    public static void main(String[] args, WidgetsFactory widgetsFactory) {
        String defaultColorConfigString = "{}";
        String colorConfigString = readColorConfigFile();
        pathjson.Converter jsonConv = new pathjson.Converter();
        LinkedHashMap<String, Object> settings = jsonConv.stringToMap(colorConfigString == null ? defaultColorConfigString : colorConfigString);

        Display display = new Display();
        {
            AboutDialog splash = AboutDialog.createAsSplash(display);
            if (!splash.openAsSplash(new InitializationTask(display))) {
                return;
            }
        }

        Shell shell = new Shell(display);
        shell.setMinimumSize(200, 100);
        Main main = new Main();
        main.loadProperties(shell);

        MetricColors.initialize(display, settings);
        PlottingColors.initialize(display, settings);
        TextColors.initialize(display, settings);

        MainWindow mainWindow = new MainWindow(main, widgetsFactory);
        mainWindow.open(shell);
        try {
            Image img = gemx.resources.ImageManager.loadImage(display, "logonew64.png");
            shell.setImage(img);

            scan_command(args, mainWindow);
            try {
                while (!shell.isDisposed()) {
                    if (!display.readAndDispatch()) {
                        display.sleep();
                    }
                }
            } finally {
                main.saveProperties(shell);
            }
            if (img != null) {
                img.dispose();
            }
        } finally {
            mainWindow.dispose();
            TextColors.dispose();
            PlottingColors.dispose();
            MetricColors.dispose();
            gemx.utility.SystemColorManager.dispose();
            display.dispose();

            TemporaryFileManager.dispose();
        }
    }

    private void setDefaultIfMissing(Properties config, String propertyName, String defaultValue) {
        String s = config.getProperty(propertyName);
        if (s == null || s.length() == 0) {
            config.setProperty(propertyName, defaultValue);
        }
    }

    private Properties createProperties() {
        Properties config = new Properties();

        config.setProperty(sVerMajor, String.valueOf(gemx.constants.ApplicationVersion.verMajor));
        config.setProperty(sVerMinor1, String.valueOf(gemx.constants.ApplicationVersion.verMinor1));
        config.setProperty(sVerMinor2, String.valueOf(gemx.constants.ApplicationVersion.verMinor2));

        config.setProperty(sSettingPreprocessor, settingPreprocessor);
        config.setProperty(sSettingMinimumCloneLength, String.valueOf(settingMinimumCloneLength));
        config.setProperty(sSettingMinimumTKS, String.valueOf(settingMinimumTKS));
        config.setProperty(sSettingEncoding, settingEncoding);
        config.setProperty(sSettingChunkSize, String.valueOf(settingChunkSize));
        config.setProperty(sSettingShaperLevel, String.valueOf(settingShaperLevel));
        config.setProperty(sSettingUsePMatch, String.valueOf(settingUsePMatch));
        config.setProperty(sSettingUsePreprocessCache, String.valueOf(settingUsePreprocessCache));
        config.setProperty(sSettingMaxWorkerThreads, String.valueOf(settingMaxWorkerThreads));
        config.setProperty(sSettingCalcFileMetricAlways, String.valueOf(settingCalcFileMetricAlways));
        config.setProperty(sSettingCalcCloneMetricAlways, String.valueOf(settingCalcCloneMetricAlways));
        config.setProperty(sSettingResizeScatterPlot, String.valueOf(settingResizeScatterPlot));
        config.setProperty(sSettingNtipleSourceTextPane, String.valueOf(settingNtipleSourceTextPane));
        config.setProperty(sSettingAllFileViewModeEnabled, String.valueOf(settingAllFileViewModeEnabled));
        config.setProperty(sSettingColorSchemeFile, settingColorSchemeFile);
        config.setProperty(sSettingClonesetTableClickToShowPair, String.valueOf(settingClonesetTableClickToShowPair));
        config.setProperty(sSettingUsePrescreening, String.valueOf(settingUsePrescreening));
        config.setProperty(sSettingResetScopeItemInContextMenus, String.valueOf(settingResetScopeItemInContextMenus));

        return config;
    }

    protected void loadProperties(Shell shell) {
        CCFinderX check = CCFinderX.theInstance;

        boolean noPropertyFileExists = false;
        Properties config = new Properties();
        try {
            boolean propertyFileVersionMismatch = false;
            InputStream inputStream = new FileInputStream(new File(propertyFile));
            config.load(inputStream);

            noPropertyFileExists = true;

            int v0 = Integer.parseInt(config.getProperty(sVerMajor));
            int v1 = Integer.parseInt(config.getProperty(sVerMinor1));
            int v2 = Integer.parseInt(config.getProperty(sVerMinor2));

            propertyFileVersionMismatch |= !(v0 == ApplicationVersion.verMajor && v1 == ApplicationVersion.verMinor1 && v2 == ApplicationVersion.verMinor2);
            if (propertyFileVersionMismatch) {
                MessageBox box1 = new MessageBox(shell,
                        SWT.ICON_WARNING | SWT.OK | SWT.CANCEL);
                box1.setText("Warning - GemX");
                box1.setMessage(
                        Messages.getString("gemx.Main.S_PROPERTY_FILE_VERSION_MISMATCH")
                                + "\n"
                                + Messages.getString("gemx.Main.S_INITIALIZE_THE_PROPERTY_FILE_P")
                );
                if (box1.open() == SWT.CANCEL) {
                    System.exit(0);
                }
                noPropertyFileExists = true;
            }

            inputStream.close();
        } catch (FileNotFoundException e) {
            noPropertyFileExists = true;
        } catch (IOException e) {
            MessageBox box1 = new MessageBox(shell,
                    SWT.ICON_ERROR | SWT.OK);
            box1.setText("Error - GemX");
            box1.setMessage(
                    Messages.getString("gemx.Main.S_FILE_IO_ERROR_IN_READING_PROPERTY_FILE")
            );
            System.exit(1);
        }

        if (noPropertyFileExists) {
            config = createProperties();
        }

        {
            setDefaultIfMissing(config, sSettingPreprocessor, CcfxDefaultSettings.initPreprocessor);
            setDefaultIfMissing(config, sSettingMinimumCloneLength, String.valueOf(CcfxDefaultSettings.initMinimumCloneLength));
            setDefaultIfMissing(config, sSettingMinimumTKS, String.valueOf(CcfxDefaultSettings.initMinimumTKS));
            setDefaultIfMissing(config, sSettingEncoding, CcfxDefaultSettings.initEncoding);
            setDefaultIfMissing(config, sSettingChunkSize, String.valueOf(CcfxDefaultSettings.initChunkSize));
            setDefaultIfMissing(config, sSettingShaperLevel, String.valueOf(CcfxDefaultSettings.initShaperLevel));
            setDefaultIfMissing(config, sSettingUsePMatch, String.valueOf(CcfxDefaultSettings.initUsePMatch));
            setDefaultIfMissing(config, sSettingUsePreprocessCache, String.valueOf(CcfxDefaultSettings.initUsePreprocessCache));
            setDefaultIfMissing(config, sSettingMaxWorkerThreads, String.valueOf(CcfxDefaultSettings.initMaxWorkerThreads));
            setDefaultIfMissing(config, sSettingCalcFileMetricAlways, String.valueOf(GemXDefaultSettings.initCalcFileMetricAlways));
            setDefaultIfMissing(config, sSettingCalcCloneMetricAlways, String.valueOf(GemXDefaultSettings.initCalcCloneMetricAlways));
            setDefaultIfMissing(config, sSettingResizeScatterPlot, String.valueOf(GemXDefaultSettings.initResizeScatterPlot));
            setDefaultIfMissing(config, sSettingNtipleSourceTextPane, String.valueOf(GemXDefaultSettings.initNtipleSourceTextPane));
            setDefaultIfMissing(config, sSettingAllFileViewModeEnabled, String.valueOf(GemXDefaultSettings.initAllFileViewModeEnabled));
            setDefaultIfMissing(config, sSettingColorSchemeFile, GemXDefaultSettings.initColorSchemeFile);
            setDefaultIfMissing(config, sSettingClonesetTableClickToShowPair, String.valueOf(GemXDefaultSettings.initClonesetTableClickToShowPair));
            setDefaultIfMissing(config, sSettingUsePrescreening, String.valueOf(CcfxDefaultSettings.initUsePrescreening));
        }
        {
            settingPreprocessor = config.getProperty(sSettingPreprocessor);
            settingMinimumCloneLength = Integer.parseInt(config.getProperty(sSettingMinimumCloneLength));
            settingMinimumTKS = Integer.parseInt(config.getProperty(sSettingMinimumTKS));
            settingEncoding = config.getProperty(sSettingEncoding);
            settingChunkSize = Integer.parseInt(config.getProperty(sSettingChunkSize));
            settingShaperLevel = Integer.parseInt(config.getProperty(sSettingShaperLevel));
            settingUsePMatch = parseBooleanLikeThing(config.getProperty(sSettingUsePMatch));
            settingUsePreprocessCache = parseBooleanLikeThing(config.getProperty(sSettingUsePreprocessCache));
            settingMaxWorkerThreads = Integer.parseInt(config.getProperty(sSettingMaxWorkerThreads));
            settingCalcFileMetricAlways = parseBooleanLikeThing(config.getProperty(sSettingCalcFileMetricAlways));
            settingCalcCloneMetricAlways = parseBooleanLikeThing(config.getProperty(sSettingCalcCloneMetricAlways));
            settingResizeScatterPlot = parseBooleanLikeThing(config.getProperty(sSettingResizeScatterPlot));
            settingNtipleSourceTextPane = Integer.parseInt(config.getProperty(sSettingNtipleSourceTextPane));
            settingAllFileViewModeEnabled = parseBooleanLikeThing(config.getProperty(sSettingAllFileViewModeEnabled));
            settingColorSchemeFile = config.getProperty(sSettingColorSchemeFile);
            settingClonesetTableClickToShowPair = parseBooleanLikeThing(config.getProperty(sSettingClonesetTableClickToShowPair));
            settingUsePrescreening = parseBooleanLikeThing(config.getProperty(sSettingUsePrescreening));
            settingResetScopeItemInContextMenus = parseBooleanLikeThing(config.getProperty(sSettingResetScopeItemInContextMenus));
        }


        try {
            if (!makeDirectoryIfNotExist(propertyFile)) {
                MessageBox box1 = new MessageBox(shell, SWT.ICON_ERROR | SWT.OK);
                box1.setText("Error - GemX");
                box1.setMessage(String.format("Fail to make directory for property file '%s'", propertyFile));
                box1.open();
                System.exit(1);
            }
            OutputStream outputStream = new FileOutputStream(new File(propertyFile));
            config.store(outputStream, "GemX properties");
            outputStream.close();
        } catch (IOException e) {
            MessageBox box1 = new MessageBox(shell,
                    SWT.ICON_ERROR | SWT.OK);
            box1.setText("Error - GemX");
            box1.setMessage(
                    Messages.getString("gemx.Main.S_FILE_IO_ERROR_IN_WRITING_PROPERTY_FILE")
            );
            System.exit(1);
        }
    }

    protected void saveProperties(Shell shell) {
        Properties config = new Properties();
        try {
            InputStream inputStream = new FileInputStream(new File(propertyFile));
            config.load(inputStream);
        } catch (IOException e) {
            // no recover
            System.exit(1);
        }


        config.setProperty(sSettingPreprocessor, settingPreprocessor);
        config.setProperty(sSettingMinimumCloneLength, String.valueOf(settingMinimumCloneLength));
        config.setProperty(sSettingMinimumTKS, String.valueOf(settingMinimumTKS));
        config.setProperty(sSettingEncoding, settingEncoding);
        config.setProperty(sSettingChunkSize, String.valueOf(settingChunkSize));
        config.setProperty(sSettingShaperLevel, String.valueOf(settingShaperLevel));
        config.setProperty(sSettingUsePMatch, String.valueOf(settingUsePMatch));
        config.setProperty(sSettingUsePreprocessCache, String.valueOf(settingUsePreprocessCache));
        config.setProperty(sSettingMaxWorkerThreads, String.valueOf(settingMaxWorkerThreads));
        config.setProperty(sSettingCalcFileMetricAlways, String.valueOf(settingCalcFileMetricAlways));
        config.setProperty(sSettingCalcCloneMetricAlways, String.valueOf(settingCalcCloneMetricAlways));
        config.setProperty(sSettingResizeScatterPlot, String.valueOf(settingResizeScatterPlot));
        config.setProperty(sSettingNtipleSourceTextPane, String.valueOf(settingNtipleSourceTextPane));
        config.setProperty(sSettingAllFileViewModeEnabled, String.valueOf(settingAllFileViewModeEnabled));
        config.setProperty(sSettingColorSchemeFile, settingColorSchemeFile);
        config.setProperty(sSettingClonesetTableClickToShowPair, String.valueOf(settingClonesetTableClickToShowPair));
        config.setProperty(sSettingUsePrescreening, String.valueOf(settingUsePrescreening));
        config.setProperty(sSettingResetScopeItemInContextMenus, String.valueOf(settingResetScopeItemInContextMenus));

        try {
            if (!makeDirectoryIfNotExist(propertyFile)) {
                MessageBox box1 = new MessageBox(shell, SWT.ICON_ERROR | SWT.OK);
                box1.setText("Error - GemX");
                box1.setMessage(String.format("Fail to make directory for property file '%s'", propertyFile));
                box1.open();
                System.exit(1);
            }
            OutputStream outputStream = new FileOutputStream(new File(propertyFile));
            config.store(outputStream, "GemX properties");
            outputStream.close();
        } catch (IOException e) {
            // no recover
            System.exit(1);
        }
    }
}

class InitializationTask implements Runnable {
    private final Display display;

    public InitializationTask(Display display) {
        this.display = display;
    }

    public void run() {
        gemx.utility.SystemColorManager.initialize(display);
    }
}