package cr.ac.una.clinicauna.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Language extends Properties {
   

    private static final long serialVersionUID = 1L;

    public Language(){

    }

    public Properties getSeleccionDeIdioma(String id) throws FileNotFoundException, IOException{
        String esp1 = "src\\main\\resources\\cr\\ac\\una\\clinicauna\\language\\es.properties";
        String eng1 = "src\\main\\resources\\cr\\ac\\una\\clinicauna\\language\\en.properties";
//        String eng1 = "src\\main\\java\\utils\\ingles.properties";
        
        var lang = new Properties();
        
        switch(id){
            case "es":
                lang.load(new FileReader(esp1));
                    break;
            case "en":
                lang.load(new FileReader(eng1));
                    break;
            default:
                lang.load(new FileReader(esp1));
        }
        return lang;
    }
}