package gemx.gemx;

public interface ITextRuler extends TextPaneScrollListener {
    boolean isVisible();

    void setVisible(boolean visible);

    void setTextPane(MultipleTextPane pane);

    void textScrolled();

    void update();

    void updateViewLocationDisplay();

    int getWidth();

    void changeFocusedTextPane(int index);
}