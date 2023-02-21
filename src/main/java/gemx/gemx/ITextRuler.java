package gemx.gemx;

import gemx.gemx.MultipleTextPane;
import gemx.gemx.TextPaneScrollListener;

public interface ITextRuler extends TextPaneScrollListener {
	public abstract boolean isVisible();
	public abstract void setVisible(boolean visible);
	public abstract void setTextPane(MultipleTextPane pane);
	public abstract void textScrolled();
	public abstract void update();
	public abstract void updateViewLocationDisplay();
	public abstract int getWidth();
	public void changeFocusedTextPane(int index);
}