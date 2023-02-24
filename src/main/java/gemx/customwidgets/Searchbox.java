package gemx.customwidgets;

import gemx.res.Messages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

public class Searchbox extends Composite {
    private final Combo searchText;
    private final java.util.ArrayList<SearchboxListener> listeners = new java.util.ArrayList<SearchboxListener>();
    private final Button ignoreCaseCheckbox;

    private final ToolItem pictureItem;
    private final Image magnifyGlassImage;
    private final Image cancelImage;

    private boolean isIgnoreCase;

    public Searchbox(Composite sc, int style) {
        super(sc, style);

        final Display display = sc.getDisplay();
        magnifyGlassImage = gemx.resources.ImageManager.loadImage(display, "search.png"); //$NON-NLS-1$
        cancelImage = gemx.resources.ImageManager.loadImage(display, "cancel.png"); //$NON-NLS-1$

        {
            GridLayout layout = new GridLayout(3, false);
            layout.marginHeight = 0;
            layout.marginWidth = 0;
            layout.horizontalSpacing = 0;
            layout.makeColumnsEqualWidth = false;
            this.setLayout(layout);
        }
        searchText = new Combo(this, SWT.DROP_DOWN);
        searchText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        final ToolBar toolBar = new ToolBar(this, SWT.FLAT);
        {
            pictureItem = new ToolItem(toolBar, SWT.PUSH);
            pictureItem.setImage(magnifyGlassImage);
            pictureItem.addSelectionListener(new SelectionAdapter() {
                public void widgetSelected(SelectionEvent e) {
                    Searchbox.this.searchCancel();
                }
            });
        }
        {
            Image image = gemx.resources.ImageManager.loadImage(display, "up.png"); //$NON-NLS-1$
            ToolItem item = new ToolItem(toolBar, SWT.PUSH);
            item.setImage(image);
            item.addSelectionListener(new SelectionAdapter() {
                public void widgetSelected(SelectionEvent e) {
                    String text = Searchbox.this.getText();
                    if (text != null) {
                        Searchbox.this.searchBackward(text);
                    }
                }
            });
        }
        {
            Image image = gemx.resources.ImageManager.loadImage(display, "down.png"); //$NON-NLS-1$
            ToolItem item = new ToolItem(toolBar, SWT.PUSH);
            item.setImage(image);
            item.addSelectionListener(new SelectionAdapter() {
                public void widgetSelected(SelectionEvent e) {
                    String text = Searchbox.this.getText();
                    if (text != null) {
                        Searchbox.this.searchForward(text);
                    }
                }
            });
        }

        ignoreCaseCheckbox = new Button(this, SWT.CHECK);
        ignoreCaseCheckbox.setText(Messages.getString("gemx.Searchbox.S_IGNORE_CASE"));  //$NON-NLS-1$
        ignoreCaseCheckbox.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent arg0) {
                Button b = (Button) arg0.widget;
                isIgnoreCase = b.getSelection();
            }

        });
        isIgnoreCase = false;

        searchText.setText(""); //$NON-NLS-1$
        searchText.addSelectionListener(new SelectionAdapter() {
            public void widgetDefaultSelected(SelectionEvent e) {
                if (e.detail == SWT.CANCEL) {
                    Searchbox.this.searchCancel();
                } else {
                    final String text = Searchbox.this.getText();
                    if (text != null) {
                        final Combo combo = Searchbox.this.searchText;
                        int entryIndex = -1;
                        for (int i = 0; i < combo.getItemCount(); ++i) {
                            String ti = combo.getItem(i);
                            if (ti.equals(text)) {
                                entryIndex = i;
                                break; // for i
                            }
                        }
                        if (entryIndex < 0) {
                            combo.add(text, 0);
                        } else {
                            combo.remove(entryIndex);
                            combo.add(text, 0);
                        }
                        if (combo.getItemCount() > 12) {
                            combo.remove(12);
                        }
                        Searchbox.this.searchForward(text);
                    }
                }
            }
        });
    }

    public void addSearchboxListener(SearchboxListener listener) {
        listeners.add(listener);
    }

    public String getText() {
        return searchText.getText();
    }

    public void setText(String text) {
        searchText.setText(text);
    }

    public boolean isIgnoreCase() {
        return isIgnoreCase;
    }

    public void setIgnoreCase(boolean isIgnore) {
        isIgnoreCase = isIgnore;
        ignoreCaseCheckbox.setSelection(isIgnore);
    }

    public void searchForward(String text) {
        if (pictureItem != null) {
            pictureItem.setImage(cancelImage);
        }
        String shownText = searchText.getText();
        if (!shownText.equals(text)) {
            searchText.setText(text);
        }
        for (SearchboxListener listener : listeners) {
            SearchboxData data = new SearchboxData();
            data.text = text;
            data.isIgnoreCase = isIgnoreCase;
            listener.searchForward(data);
        }
    }

    public void searchBackward(String text) {
        if (pictureItem != null) {
            pictureItem.setImage(cancelImage);
        }
        String shownText = searchText.getText();
        if (!shownText.equals(text)) {
            searchText.setText(text);
        }
        for (SearchboxListener listener : listeners) {
            SearchboxData data = new SearchboxData();
            data.text = text;
            data.isIgnoreCase = isIgnoreCase;
            listener.searchBackward(data);
        }
    }

    public void searchCancel() {
        if (pictureItem != null) {
            pictureItem.setImage(magnifyGlassImage);
        }
        searchText.setText(""); //$NON-NLS-1$
        for (SearchboxListener listener : listeners) {
            SearchboxData data = new SearchboxData();
            data.text = null;
            data.isIgnoreCase = isIgnoreCase;
            listener.searchCanceled(data);
        }
    }
}
