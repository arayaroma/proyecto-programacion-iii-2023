package cr.ac.una.clinicauna.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
//import org.apache.commons.compress.utils.IOUtils;
//import org.apache.commons.io.FileUtils;
import javafx.scene.image.Image;
import org.apache.commons.io.IOUtils;

/**
 * 
 * @author arayaroma
 * @author enajera
 */
public class ImageLoader {


//    public static String fileToString(File file) {
//        try {
//            return FileUtils.readFileToString(file, "UTF-8");
//        } catch (IOException ex) {
//            System.out.println(ex.getMessage());
//            return null;
//        }
//    }

    public static byte[] imageToByteArray(File file) {
        try {
            return IOUtils.toByteArray(new FileInputStream(file));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public static ByteArrayInputStream byteArrayToImage(byte[] image) {
        return new ByteArrayInputStream(image);
    }

    public static Image setImage(File file) {
        return new Image(file.toURI().toString());
    }

    public static Image setImage(byte[] image) {
        return new Image(byteArrayToImage(image));
    }

}
