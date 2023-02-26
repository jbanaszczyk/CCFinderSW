package gemx.gemx.dialogs;

import gemx.ccfinderx.CCFinderX;
import gemx.resources.Messages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import java.util.Calendar;

public class AboutDialog {
    private final Shell parent;
    private final Shell shellC;
    private final Label label;
    private final CCFinderX ccfinderx = CCFinderX.theInstance;
    private Color backgroundColor;

    public AboutDialog(Shell shell) {
        this(shell, new Shell(shell, SWT.DIALOG_TRIM | SWT.PRIMARY_MODAL), true, true);
    }

//	private void showErrorMessage(String message) {
//		MessageBox mes = new MessageBox(shellC, SWT.OK | SWT.ICON_ERROR);
//		mes.setText("Error - GemX"); 
//		mes.setMessage(message);
//		mes.open();
//	}

    private AboutDialog(Shell parent, Shell shellC, boolean needButtons, boolean needAcknoledgement) {
        this.parent = parent;
        this.shellC = shellC;
        Display display = shellC.getDisplay();

        if (!needButtons) {
            backgroundColor = display.getSystemColor(SWT.COLOR_WHITE);
        }
        {
            GridLayout gridLayout = new GridLayout();
            gridLayout.numColumns = 1;
            gridLayout.marginHeight = 15;
            gridLayout.marginWidth = 15;
            gridLayout.horizontalSpacing = 15;
            shellC.setLayout(gridLayout);
        }
        if (backgroundColor != null) {
            shellC.setBackground(backgroundColor);
        }
        shellC.setText("About - GemX");

        GridData gridData;

        {
            Composite buttonsCompo = new Composite(shellC, SWT.NONE);
            gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
            gridData.horizontalSpan = 2;
            buttonsCompo.setLayoutData(gridData);
            if (backgroundColor != null) {
                buttonsCompo.setBackground(backgroundColor);
            }
            {
                GridLayout gridLayout = new GridLayout();
                gridLayout.numColumns = 2;
                gridLayout.marginHeight = 0;
                gridLayout.marginWidth = 0;
                gridLayout.horizontalSpacing = 10;
                gridLayout.makeColumnsEqualWidth = false;
                buttonsCompo.setLayout(gridLayout);
            }

            if (!needButtons) {
                Label labelIcon = new Label(buttonsCompo, SWT.NONE);
                if (backgroundColor != null) {
                    labelIcon.setBackground(backgroundColor);
                }
                Image img = gemx.resources.ImageManager.loadImage(display, "logonew_with_name.png");
                labelIcon.setImage(img);

                gridData = new GridData(GridData.HORIZONTAL_ALIGN_END);
                labelIcon.setLayoutData(gridData);
                new Label(buttonsCompo, SWT.NONE);
            }

            label = new Label(buttonsCompo, SWT.NONE);
            gridData = new GridData(GridData.FILL_HORIZONTAL);
            label.setLayoutData(gridData);
            if (backgroundColor != null) {
                label.setBackground(backgroundColor);
            }

            if (needButtons) {
                Label labelIcon = new Label(buttonsCompo, SWT.NONE);
                if (backgroundColor != null) {
                    labelIcon.setBackground(backgroundColor);
                }
                Image img = gemx.resources.ImageManager.loadImage(display, "logonew64.png");
                labelIcon.setImage(img);
            }

            String versionStr = String.format("%d.%d.%d.%d", gemx.constants.ApplicationVersion.verMajor,
                    gemx.constants.ApplicationVersion.verMinor1, gemx.constants.ApplicationVersion.verMinor2,
                    gemx.constants.ApplicationVersion.verFix);

            label.setText(
                    "GemX, the GUI front-end of CCFinderX version " + versionStr
                            + "  (C) 2009-2010 AIST. \n\n"
                            + (needAcknoledgement ? Messages.getString("gemx.AboutDialog.S_ACKNOLEDGEMENT") : "")
            );
        }

        if (needButtons) {
            Composite buttonsCompo = new Composite(shellC, SWT.NONE);
            gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
            buttonsCompo.setLayoutData(gridData);
            if (backgroundColor != null) {
                buttonsCompo.setBackground(backgroundColor);
            }
            {
                GridLayout gridLayout = new GridLayout();
                gridLayout.numColumns = 2;
                gridLayout.marginHeight = 0;
                gridLayout.marginWidth = 0;
                gridLayout.horizontalSpacing = 10;
                gridLayout.makeColumnsEqualWidth = false;
                buttonsCompo.setLayout(gridLayout);
            }

            Link linkToWebsite = new Link(buttonsCompo, SWT.NONE);
            linkToWebsite.setText("<a>http://www.ccfinder.net/</a>");
            linkToWebsite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
            linkToWebsite.addSelectionListener(new SelectionAdapter() {
                public void widgetSelected(SelectionEvent e) {
                    ccfinderx.openOfficialSiteTop(Messages.getString("gemx.AboutDialog.S_PAGE_SUFFIX"));
                    AboutDialog.this.shellC.dispose();
                }
            });

            Button button1 = new Button(buttonsCompo, SWT.PUSH);
            gridData = new GridData(GridData.FILL);
            gridData.widthHint = 150;
            button1.setLayoutData(gridData);
            button1.setText(Messages.getString("gemx.MainWindow.S_OK"));
            button1.addSelectionListener(new SelectionAdapter() {
                public void widgetSelected(SelectionEvent e) {
                    AboutDialog.this.shellC.dispose();
                }
            });

            button1.setFocus();
        }
    }

    public static AboutDialog createAsSplash(Display display) {
        Shell shell = new Shell(SWT.ON_TOP);
        AboutDialog dialog = new AboutDialog(shell, shell, false, false);
        return dialog;
    }

    static private int remainingDays(int exd) {
        Calendar cal = Calendar.getInstance();
        long time = cal.getTimeInMillis();

        /* int curyear = */
        cal.get(Calendar.YEAR);
        /* int curmonth = */
        cal.get(Calendar.MONTH);
        /* int curday = */
        cal.get(Calendar.DAY_OF_MONTH);

        int exyear = exd / 10000;
        int exmonth = exd / 100 % 100;
        if (exmonth > 0) {
            exmonth -= 1;
        }
        int exday = exd % 100;

        Calendar excal = Calendar.getInstance();
        excal.set(exyear, exmonth, exday);
        excal.setTimeZone(cal.getTimeZone());
        long extime = excal.getTimeInMillis();

        if (time > extime) {
            return -1;
        }

        long remaindays = (extime - time) / 1000 / 60 / 60 / 24;

        return (int) remaindays;
    }

    protected String strProduct() {
        return "GemX";
    }

    private void showErrorMessageAndOpenTroubleshootingPage(String message) {
        MessageBox mes = new MessageBox(shellC, SWT.YES | SWT.NO | SWT.ICON_ERROR);
        mes.setText("Error - GemX");
        mes.setMessage(message + "\n\n" + "Open the troubleshooting page in www.ccfinder.net?");
        int r = mes.open();
        if (r == SWT.YES) {
            ccfinderx.openOfficialSiteDocumentPage(Messages.getString("gemx.MainWindow.S_DOCUMENT_PAGE_LANG"), "troubleshooting.html");
        }
    }

    private int confirmMessage(String message) {
        MessageBox mes = new MessageBox(shellC, SWT.OK | SWT.CANCEL | SWT.ICON_INFORMATION);
        mes.setText("Confirmation - GemX");
        mes.setMessage(message);
        int r = mes.open();
        return r;
    }

    public boolean openAsSplash(Runnable backgroundTask) {
        shellC.pack();
        Rectangle shellRect = shellC.getBounds();
        Rectangle dispRect = shellC.getDisplay().getBounds();
        int x = dispRect.x + (dispRect.width - shellRect.width) / 2;
        int y = dispRect.y + (dispRect.height - shellRect.height) / 2;
        shellC.setLocation(x, y);
        shellC.open();

        if (backgroundTask != null) {
            backgroundTask.run();
        }

        {
            String exeDir = gemx.utility.ExecutionModuleDirectory.get();
            assert exeDir != null;
            if (exeDir.indexOf('%') >= 0) {
                showErrorMessageAndOpenTroubleshootingPage("INSTALLATION ERROR:\n"
                        + "Non-ASCII characters may not be included in path of installation directory.");
                System.exit(1);
            }

//			if (! utility.PythonVersionChecker.check(shellC)) {
//				showErrorMessageAndOpenTroubleshootingPage("INSTALLATION ERROR:\n" 
//						+ "Python interpreter was not found. \n" 
//						+ "Install Python version 2.5 or later.\n" 
//						+ "When two or more Python interpreters are installed, \n" 
//						+ "set enviornment variable CCFINDERX_PYTHON_INTERPRETER_PATH."); 
//				System.exit(1);
//			}

        }

        shellC.setVisible(false);

        return true;
    }

    public void open() {
        shellC.pack();
        shellC.setLocation(parent.getLocation().x
                + (parent.getSize().x - shellC.getSize().x) / 2, parent
                .getLocation().y
                + (parent.getSize().y - shellC.getSize().y) / 2);
        shellC.open();

        while (!shellC.isDisposed()) {
            if (!shellC.getDisplay().readAndDispatch()) {
                shellC.getDisplay().sleep();
            }
        }
    }
}

