package gemx.resources;

import gemx.utility.JsonHelper;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

import java.util.HashMap;

public class TextColors {
    private static final RGB defaultWhite = new RGB(255, 255, 255);
    private static final RGB defaultBlack = new RGB(0, 0, 0);
    private static final RGB defaultClonePairColor = new RGB(225, 225, 225);
    private static final RGB defaultSelectedClonePairColor = new RGB(225, 247, 250);
    private static final RGB defaultReservedWordColor = new RGB(120, 0, 0);
    private static final RGB defaultNeglectedTextColor = new RGB(0, 160, 0);

    private static final RGB defaultRulerScrollviewFrameColor = new RGB(255, 255, 0);
    private static final RGB defaultRulerScrollviewDraggingFrameColor = new RGB(208, 208, 0);
    private static final RGB defaultRulerWhite = new RGB(255, 255, 255);
    private static final RGB defaultRulerBackgroundColor = new RGB(0x70, 0x6a, 0x5a); // Negishi Iro
    private static final RGB defaultRulerGray = new RGB(192, 192, 192);

    private static final Color[] white = new Color[1];
    private static final Color[] black = new Color[1];
    private static final Color[] clonePairColor = new Color[1];
    private static final Color[] selectedClonePairColor = new Color[1];
    private static final Color[] reservedWordColor = new Color[1];
    private static final Color[] neglectedTextColor = new Color[1];

    private static final Color[] rulerScrollviewFrameColor = new Color[1];
    private static final Color[] rulerScrollviewDraggingFrameColor = new Color[1];
    private static final Color[] rulerWhite = new Color[1];
    private static final Color[] rulerBackgroundColor = new Color[1];
    private static final Color[] rulerGray = new Color[1];

    public static Color getWhite() {
        return white[0];
    }

    public static Color getBlack() {
        return black[0];
    }

    public static Color getClonePair() {
        return clonePairColor[0];
    }

    public static Color getSelectedClonePair() {
        return selectedClonePairColor[0];
    }

    public static Color getReservedWord() {
        return reservedWordColor[0];
    }

    public static Color getNeglectedText() {
        return neglectedTextColor[0];
    }

    public static Color getRulerScrollviewFrame() {
        return rulerScrollviewFrameColor[0];
    }

    public static Color getRulerScrollviewDraggingFrame() {
        return rulerScrollviewDraggingFrameColor[0];
    }

    public static Color getRulerWhite() {
        return rulerWhite[0];
    }

    public static Color getRulerBackground() {
        return rulerBackgroundColor[0];
    }

    public static Color getRulerGray() {
        return rulerGray[0];
    }

    public static void reloadColor(Display display, HashMap<String, Object> settings) {
        dispose();
        initialize(display, settings);
    }

    public static void initialize(Display display, HashMap<String, Object> settings) {
        {
            RGB rgb = JsonHelper.readRGBFromSettings(settings, "/sourcetext/background/");
            if (rgb == null) {
                rgb = defaultWhite;
            }
            white[0] = new Color(display, rgb);
        }
        {
            RGB rgb = JsonHelper.readRGBFromSettings(settings, "/sourcetext/foreground/");
            if (rgb == null) {
                rgb = defaultBlack;
            }
            black[0] = new Color(display, rgb);
        }
        {
            RGB rgb = JsonHelper.readRGBFromSettings(settings, "/sourcetext/clonepair_background/");
            if (rgb == null) {
                rgb = defaultClonePairColor;
            }
            clonePairColor[0] = new Color(display, rgb);
        }
        {
            RGB rgb = JsonHelper.readRGBFromSettings(settings, "/sourcetext/selected_clonepair_background/");
            if (rgb == null) {
                rgb = defaultSelectedClonePairColor;
            }
            selectedClonePairColor[0] = new Color(display, rgb);
        }
        {
            RGB rgb = JsonHelper.readRGBFromSettings(settings, "/sourcetext/reservedword_foreground/");
            if (rgb == null) {
                rgb = defaultReservedWordColor;
            }
            reservedWordColor[0] = new Color(display, rgb);
        }
        {
            RGB rgb = JsonHelper.readRGBFromSettings(settings, "/sourcetext/neglectedtext_foreground/");
            if (rgb == null) {
                rgb = defaultNeglectedTextColor;
            }
            neglectedTextColor[0] = new Color(display, rgb);
        }

        {
            RGB rgb = JsonHelper.readRGBFromSettings(settings, "/sourcetext_ruler/background/");
            if (rgb == null) {
                rgb = defaultRulerBackgroundColor;
            }
            rulerBackgroundColor[0] = new Color(display, rgb);
        }
        {
            RGB rgb = JsonHelper.readRGBFromSettings(settings, "/sourcetext_ruler/scrollview_frame/");
            if (rgb == null) {
                rgb = defaultRulerScrollviewFrameColor;
            }
            rulerScrollviewFrameColor[0] = new Color(display, rgb);
        }
        {
            RGB rgb = JsonHelper.readRGBFromSettings(settings, "/sourcetext_ruler/scrollview_dragging_frame/");
            if (rgb == null) {
                rgb = defaultRulerScrollviewDraggingFrameColor;
            }
            rulerScrollviewDraggingFrameColor[0] = new Color(display, rgb);
        }
        {
            RGB rgb = JsonHelper.readRGBFromSettings(settings, "/sourcetext_ruler/filebody_line/");
            if (rgb == null) {
                rgb = defaultRulerWhite;
            }
            rulerWhite[0] = new Color(display, rgb);
        }
        {
            RGB rgb = JsonHelper.readRGBFromSettings(settings, "/sourcetext_ruler/clone_line/");
            if (rgb == null) {
                rgb = defaultRulerGray;
            }
            rulerGray[0] = new Color(display, rgb);
        }
    }

    public static void dispose() {
        white[0].dispose();
        black[0].dispose();
        clonePairColor[0].dispose();
        selectedClonePairColor[0].dispose();
        reservedWordColor[0].dispose();
        neglectedTextColor[0].dispose();

        rulerScrollviewFrameColor[0].dispose();
        rulerWhite[0].dispose();
        rulerBackgroundColor[0].dispose();
        rulerGray[0].dispose();
    }
}


