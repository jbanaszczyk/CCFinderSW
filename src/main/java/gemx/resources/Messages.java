package gemx.resources;

import org.jetbrains.annotations.PropertyKey;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Messages {
    private static final String BUNDLE_NAME = "gemx_messages";
    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

    public static String getString(@PropertyKey(resourceBundle = BUNDLE_NAME) String key, Object... params) {
        try {
            var value = RESOURCE_BUNDLE.getString(key);
            return params.length == 0
                    ? value
                    : MessageFormat.format(value, params);
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }
}
