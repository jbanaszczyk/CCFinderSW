package gemx.utility;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

import java.util.HashMap;

public class SystemColorManager {
    private static ColorNameAndCode[] colorNameAndCodes;
    private static HashMap<String, RGB> nameToRgbTable;

    public static void initialize(Display display) {
        colorNameAndCodes = new ColorNameAndCode[]{
                new ColorNameAndCode("SWT.COLOR_BLACK", SWT.COLOR_BLACK),
                new ColorNameAndCode("SWT.COLOR_RED", SWT.COLOR_RED),
                new ColorNameAndCode("SWT.COLOR_DARK_RED", SWT.COLOR_DARK_RED),
                new ColorNameAndCode("SWT.COLOR_GREEN", SWT.COLOR_GREEN),
                new ColorNameAndCode("SWT.COLOR_DARK_GREEN", SWT.COLOR_DARK_GREEN),
                new ColorNameAndCode("SWT.COLOR_YELLOW", SWT.COLOR_YELLOW),
                new ColorNameAndCode("SWT.COLOR_DARK_YELLOW", SWT.COLOR_DARK_YELLOW),
                new ColorNameAndCode("SWT.COLOR_BLUE", SWT.COLOR_BLUE),
                new ColorNameAndCode("SWT.COLOR_DARK_BLUE", SWT.COLOR_DARK_BLUE),
                new ColorNameAndCode("SWT.COLOR_MAGENTA", SWT.COLOR_MAGENTA),
                new ColorNameAndCode("SWT.COLOR_DARK_MAGENTA", SWT.COLOR_DARK_MAGENTA),
                new ColorNameAndCode("SWT.COLOR_CYAN", SWT.COLOR_CYAN),
                new ColorNameAndCode("SWT.COLOR_DARK_CYAN", SWT.COLOR_DARK_CYAN),
                new ColorNameAndCode("SWT.COLOR_GRAY", SWT.COLOR_GRAY),
                new ColorNameAndCode("SWT.COLOR_DARK_GRAY", SWT.COLOR_DARK_GRAY),
                new ColorNameAndCode("SWT.COLOR_WIDGET_DARK_SHADOW", SWT.COLOR_WIDGET_DARK_SHADOW),
                new ColorNameAndCode("SWT.COLOR_WIDGET_NORMAL_SHADOW", SWT.COLOR_WIDGET_NORMAL_SHADOW),
                new ColorNameAndCode("SWT.COLOR_WIDGET_LIGHT_SHADOW", SWT.COLOR_WIDGET_LIGHT_SHADOW),
                new ColorNameAndCode("SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW", SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW),
                new ColorNameAndCode("SWT.COLOR_WIDGET_BACKGROUND", SWT.COLOR_WIDGET_BACKGROUND),
                new ColorNameAndCode("SWT.COLOR_WIDGET_BORDER", SWT.COLOR_WIDGET_BORDER),
                new ColorNameAndCode("SWT.COLOR_WIDGET_FOREGROUND", SWT.COLOR_WIDGET_FOREGROUND),
                new ColorNameAndCode("SWT.COLOR_LIST_FOREGROUND", SWT.COLOR_LIST_FOREGROUND),
                new ColorNameAndCode("SWT.COLOR_LIST_BACKGROUND", SWT.COLOR_LIST_BACKGROUND),
                new ColorNameAndCode("SWT.COLOR_LIST_SELECTION", SWT.COLOR_LIST_SELECTION),
                new ColorNameAndCode("SWT.COLOR_LIST_SELECTION_TEXT", SWT.COLOR_LIST_SELECTION_TEXT),
                new ColorNameAndCode("SWT.COLOR_INFO_FOREGROUND", SWT.COLOR_INFO_FOREGROUND),
                new ColorNameAndCode("SWT.COLOR_INFO_BACKGROUND", SWT.COLOR_INFO_BACKGROUND),
                new ColorNameAndCode("SWT.COLOR_TITLE_FOREGROUND", SWT.COLOR_TITLE_FOREGROUND),
                new ColorNameAndCode("SWT.COLOR_TITLE_BACKGROUND", SWT.COLOR_TITLE_BACKGROUND),
                new ColorNameAndCode("SWT.COLOR_TITLE_BACKGROUND_GRADIENT", SWT.COLOR_TITLE_BACKGROUND_GRADIENT),
                new ColorNameAndCode("SWT.COLOR_TITLE_INACTIVE_FOREGROUND", SWT.COLOR_TITLE_INACTIVE_FOREGROUND),
                new ColorNameAndCode("SWT.COLOR_TITLE_INACTIVE_BACKGROUND", SWT.COLOR_TITLE_INACTIVE_BACKGROUND),
                new ColorNameAndCode("SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT", SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT),
        };

        nameToRgbTable = new HashMap<String, RGB>();
        for (int i = 0; i < colorNameAndCodes.length; ++i) {
            nameToRgbTable.put(colorNameAndCodes[i].name, display.getSystemColor(colorNameAndCodes[i].code).getRGB());
        }
    }

    public static void dispose() {
    }

    public static RGB getRGBOfColor(String name) {
        if (nameToRgbTable.containsKey(name)) {
            return nameToRgbTable.get(name);
        }
        return null;
    }

    private static class ColorNameAndCode {
        public final String name;
        public final int code;

        public ColorNameAndCode(String name, int code) {
            this.name = name;
            this.code = code;
        }
    }
}
