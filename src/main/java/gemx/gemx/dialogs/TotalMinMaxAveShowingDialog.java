package gemx.gemx.dialogs;

import gemx.res.Messages;
import gemx.utility.StringUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class TotalMinMaxAveShowingDialog {
    private final Shell parent;
    private final Shell shellC;

    private final Clipboard clipboard;

    private final Label[] labelNames;
    private final Label[] labelTotals;
    private final Label[] labelMins;
    private final Label[] labelAves;
    private final Label[] labelMaxs;
    private final int[] valueDigits;
    private final int[] valueTotals;
    private final int[] valueMins;
    private final double[] valueAves;
    private final int[] valueMaxs;
    private final Button buttonCopy;

    public TotalMinMaxAveShowingDialog(Shell shell, Clipboard clipboard_, int countOfValues) {
        valueTotals = new int[countOfValues];
        valueMins = new int[countOfValues];
        valueAves = new double[countOfValues];
        valueMaxs = new int[countOfValues];
        valueDigits = new int[countOfValues];

        for (int i = 0; i < countOfValues; ++i) {
            valueTotals[i] = 0;
            valueMins[i] = 0;
            valueAves[i] = 0;
            valueMaxs[i] = 100;
            valueDigits[i] = 0;
        }

        this.clipboard = clipboard_;
        parent = shell;
        shellC = new Shell(shell, SWT.DIALOG_TRIM | SWT.PRIMARY_MODAL);
        {
            GridLayout gridLayout = new GridLayout();
            gridLayout.numColumns = 5; // name, total, min, ave, max
            gridLayout.marginHeight = 15;
            gridLayout.marginWidth = 25;
            gridLayout.horizontalSpacing = 15;
            shellC.setLayout(gridLayout);
        }

        {
            Label dummy1 = new Label(shellC, SWT.NONE);
            GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
            gridData.widthHint = 80;
            dummy1.setLayoutData(gridData);

            Label totalColumnLabel = new Label(shellC, SWT.NONE);
            totalColumnLabel.setText(Messages.getString("gemx.TotalMinMaxAveShowingDialog.S_TOTAL"));
            gridData = new GridData(GridData.FILL_HORIZONTAL);
            gridData.widthHint = 80;
            totalColumnLabel.setLayoutData(gridData);

            Label minColumnLabel = new Label(shellC, SWT.NONE);
            minColumnLabel.setText(Messages.getString("gemx.MainWindow.S_Minumum"));
            gridData = new GridData(GridData.FILL_HORIZONTAL);
            gridData.widthHint = 80;
            minColumnLabel.setLayoutData(gridData);

            Label maxColumnLabel = new Label(shellC, SWT.NONE);
            maxColumnLabel.setText(Messages.getString("gemx.MainWindow.S_Maximum"));
            gridData = new GridData(GridData.FILL_HORIZONTAL);
            gridData.widthHint = 80;
            maxColumnLabel.setLayoutData(gridData);

            Label aveColumnLabel = new Label(shellC, SWT.NONE);
            aveColumnLabel.setText(Messages.getString("gemx.MinMaxAveShowingDialog.S_AVE"));
            gridData = new GridData(GridData.FILL_HORIZONTAL);
            gridData.widthHint = 80;
            aveColumnLabel.setLayoutData(gridData);
        }

        labelNames = new Label[countOfValues];
        labelTotals = new Label[countOfValues];
        labelMins = new Label[countOfValues];
        labelAves = new Label[countOfValues];
        labelMaxs = new Label[countOfValues];

        for (int i = 0; i < countOfValues; ++i) {
            labelNames[i] = new Label(shellC, SWT.NONE);
            {
                GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
                labelNames[i].setLayoutData(gridData);
            }
            labelTotals[i] = new Label(shellC, SWT.RIGHT | SWT.BORDER);
            {
                GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
                labelTotals[i].setLayoutData(gridData);
            }
            labelMins[i] = new Label(shellC, SWT.RIGHT | SWT.BORDER);
            {
                GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
                labelMins[i].setLayoutData(gridData);
            }
            labelMaxs[i] = new Label(shellC, SWT.RIGHT | SWT.BORDER);
            {
                GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
                labelMaxs[i].setLayoutData(gridData);
            }
            labelAves[i] = new Label(shellC, SWT.RIGHT | SWT.BORDER);
            {
                GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
                labelAves[i].setLayoutData(gridData);
            }
        }
        {
            Composite buttonsCompo = new Composite(shellC, SWT.NONE);
            {
                GridData gridData = new GridData(GridData.HORIZONTAL_ALIGN_END);
                gridData.horizontalSpan = 4;
                buttonsCompo.setLayoutData(gridData);
            }
            {
                GridLayout gridLayout = new GridLayout();
                gridLayout.numColumns = 3;
                gridLayout.marginHeight = 0;
                gridLayout.marginWidth = 0;
                gridLayout.horizontalSpacing = 10;
                gridLayout.makeColumnsEqualWidth = false;
                buttonsCompo.setLayout(gridLayout);
            }

            buttonCopy = new Button(buttonsCompo, SWT.NONE);
            {
                GridData gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
                gridData.widthHint = 150;
                buttonCopy.setLayoutData(gridData);
            }
            buttonCopy.setText(Messages.getString("gemx.MinMaxAveShowingDialog.S_COPY_TO_CLIPBOARD"));
            buttonCopy.addSelectionListener(new SelectionAdapter() {
                public void widgetSelected(SelectionEvent e) {
                    copyValues();
                }
            });

            Button okButton = new Button(buttonsCompo, SWT.NONE);
            {
                GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
                gridData.widthHint = 80;
                okButton.setLayoutData(gridData);
            }
            okButton.setText(Messages.getString("gemx.CcfxSettingsDialog.S_OK"));
            okButton.addSelectionListener(new SelectionAdapter() {
                public void widgetSelected(SelectionEvent e) {
                    shellC.dispose();
                }
            });
            okButton.setFocus();
        }
    }

    private String valuesToString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("Name\tTotal\tMin.\tMax.\tAverage");
        buffer.append(StringUtil.NewLineString);

        for (int i = 0; i < valueMins.length; ++i) {
            buffer.append(labelNames[i].getText());
            buffer.append('\t');
            buffer.append(labelTotals[i].getText());
            buffer.append('\t');
            buffer.append(labelMins[i].getText());
            buffer.append('\t');
            buffer.append(labelMaxs[i].getText());
            buffer.append('\t');
            buffer.append(labelAves[i].getText());
            buffer.append(StringUtil.NewLineString);
        }

        return buffer.toString();
    }

    private void copyValues() {
        clipboard.setContents(new Object[]{valuesToString()},
                new Transfer[]{TextTransfer.getInstance()});
    }

    public void setText(String text) {
        shellC.setText(text);
    }

    public void setName(int index, String name) {
        labelNames[index].setText(name);
    }

    public void setTooltip(int index, String text) {
        labelNames[index].setToolTipText(text);
        labelTotals[index].setToolTipText(text);
        labelMins[index].setToolTipText(text);
        labelAves[index].setToolTipText(text);
        labelMaxs[index].setToolTipText(text);
    }

    private String format_number(int value, int d) {
        if (d == 1) {
            return String.valueOf(value);
        }
        StringBuffer buf = new StringBuffer();
        int sign = value < 0 ? -1 : 1;
        value *= sign;
        if (sign < 0) {
            buf.append('-');
        }
        String num = String.format("%.3f", value / (double) d);
        buf.append(num);
        return buf.toString();
    }

    private String format_number(long value, int d) {
        if (d == 1) {
            return String.valueOf(value);
        }
        StringBuffer buf = new StringBuffer();
        int sign = value < 0 ? -1 : 1;
        value *= sign;
        if (sign < 0) {
            buf.append('-');
        }
        String num = String.format("%.3f", value / (double) d);
        buf.append(num);
        return buf.toString();
    }

    public void setMinMaxAve(int index, int rangeMin, int rangeMax, int digits, double average) {
        int d = 1;
        for (int i = 0; i < digits; ++i) {
            d *= 10;
        }
        labelTotals[index].setText("-");
        labelMins[index].setText(format_number(rangeMin, d));
        labelAves[index].setText(String.valueOf(average));
        labelMaxs[index].setText(format_number(rangeMax, d));
    }

    public void setTotalMinMaxAve(int index, Long rangeTotal, int rangeMin, int rangeMax, int digits, double average) {
        int d = 1;
        for (int i = 0; i < digits; ++i) {
            d *= 10;
        }
        if (rangeTotal != null) {
            labelTotals[index].setText(format_number(rangeTotal, d));
        } else {
            labelTotals[index].setText("-");
        }
        labelMins[index].setText(format_number(rangeMin, d));
        labelAves[index].setText(String.valueOf(average));
        labelMaxs[index].setText(format_number(rangeMax, d));
    }

    public int open() {
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
        return SWT.OK;
    }
}
