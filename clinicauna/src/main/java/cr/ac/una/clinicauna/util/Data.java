package cr.ac.una.clinicauna.util;

import cr.ac.una.clinicauna.App;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 *
 * @author estebannajera
 */
public class Data {

    public static final ResourceBundle spanishBundle = ResourceBundle.getBundle(App.DOMAIN_PATH + "language/lang_es");
    public static final ResourceBundle englishBundle = ResourceBundle.getBundle(App.DOMAIN_PATH + "language/lang_en");
    public static String languageOption = "es";
    private static HashMap<String, Object> data = new HashMap<>();

    public static Object getData(String key) {
        return data.get(key);
    }

    public static void setData(String key, Object object) {
        data.put(key, object);
    }

    public static void removeData(String key) {
        data.remove(key);
    }

    public static ResourceBundle getEnglishBundle() {
        return englishBundle;
    }

    public static ResourceBundle getSpanishBundle() {
        return spanishBundle;
    }

    public static String getLanguageOption() {
        return languageOption;
    }

    public static void setLanguageOption(String languageOption) {
        Data.languageOption = languageOption;
    }

}
