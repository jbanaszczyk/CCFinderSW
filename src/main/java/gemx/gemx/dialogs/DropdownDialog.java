package gemx.gemx.dialogs;

import gemx.resources.Messages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class DropdownDialog {
    private final Shell parent;
    private final Shell shellC;
    private final Label label;
    private final Combo combo;
    private String resString;

    public DropdownDialog(Shell shell) {
        resString = null;
        parent = shell;

        shellC = new Shell(shell, SWT.DIALOG_TRIM | SWT.PRIMARY_MODAL);
        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 2;
        gridLayout.marginHeight = 15;
        gridLayout.marginWidth = 15;
        gridLayout.horizontalSpacing = 15;
        shellC.setLayout(gridLayout);

        GridData gridData;

        label = new Label(shellC, SWT.NULL);
        gridData = new GridData(GridData.FILL_HORIZONTAL);
        label.setLayoutData(gridData);

        Button button1 = new Button(shellC, SWT.PUSH);
        gridData = new GridData(GridData.FILL);
        gridData.widthHint = 100;
        button1.setLayoutData(gridData);
        button1.setText(Messages.getString("gemx.MainWindow.S_NEXT"));
        button1.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                resString = combo.getText();
                shellC.dispose();
            }
        });

        combo = new Combo(shellC, SWT.DROP_DOWN);
        gridData = new GridData(GridData.FILL_HORIZONTAL);
        gridData.widthHint = 200;
        combo.setLayoutData(gridData);
        combo.setFocus();
        combo.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.character == SWT.CR) {
                    resString = combo.getText();
                    shellC.dispose();
                }
            }
        });

        Button button2 = new Button(shellC, SWT.PUSH);
        gridData = new GridData(GridData.FILL_HORIZONTAL);
        gridData.widthHint = 100;
        button2.setLayoutData(gridData);
        button2.setText(Messages.getString("gemx.MainWindow.S_CANCEL"));
        button2.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                shellC.dispose();
            }
        });

        button1.setFocus();
    }

    public String getText() {
        return shellC.getText();
    }

    public void setText(String str) {
        shellC.setText(str);
    }

    public String getMessage() {
        return label.getText();
    }

    public void setMessage(String str) {
        label.setText(str);
    }

    public String open() {
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
        return resString;
    }

    public void add(String str, int i) {
        combo.add(str, i);
    }

    public void add(String str) {
        combo.add(str);
    }

    public void remove(int from, int to) {
        combo.remove(from, to);
    }

    public void removeString(int i) {
        combo.remove(i);
    }

    public void removeString(String str) {
        combo.remove(str);
    }

    public void removeAll() {
        combo.removeAll();
    }

    public String getString() {
        return resString;
    }

    public void setString(String str) {
        combo.setText(str);
    }
}
