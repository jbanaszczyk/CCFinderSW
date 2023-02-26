package gemx.resources;

import gemx.gemx.Main;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import java.lang.invoke.MethodHandles;

public class ImageManager {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private static final java.util.Map<String, Image> table = new java.util.HashMap<>();

    public static Image loadImage(Display display, String filename) {
        Image result = table.get(filename);
        if (result == null) {
            try {
                result = new Image(display, Main.class.getResourceAsStream("/icons/" + filename));
                table.put(filename, result);
            } catch (Exception e) {
                logger.warn("icon does not exists: {}", filename);
                return null;
            }
        }
        return result;
    }
}
