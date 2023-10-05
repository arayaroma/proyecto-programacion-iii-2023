package cr.ac.una.clinicauna.util;

import cr.ac.una.clinicauna.App;
import java.util.ResourceBundle;

/**
 *
 * @author estebannajera
 */
public class Data {

    public static final ResourceBundle spanishBundle = ResourceBundle.getBundle(App.DOMAIN_PATH + "language/lang_es");
    public static final ResourceBundle englishBundle = ResourceBundle.getBundle(App.DOMAIN_PATH + "language/lang_en");
    public static String languageOption = "es";

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
