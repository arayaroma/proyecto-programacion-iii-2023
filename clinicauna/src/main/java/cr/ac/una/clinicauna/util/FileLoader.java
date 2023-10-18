package cr.ac.una.clinicauna.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.commons.io.FileUtils;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author arayaroma
 */
public class FileLoader {

    /**
     * convert a file to a string
     *
     * @param file file to convert
     * @return string representation of the file
     */
    public static String fileToString(File file) {
        try {
            return FileUtils.readFileToString(file, "UTF-8");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    /**
     * select a file with a JavaFX FileChooser dialog and return the selected
     * file
     *
     * @param nameFilter name of the filter
     * @param filters filters
     * @return selected file
     */
    public static File selectFile(String nameFilter, String... filters) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(nameFilter, filters));
        return fileChooser.showOpenDialog(new Stage());
    }

    public static String chooseSavePath() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar Archivo");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Archivo de PDF", "*.pdf")
        );
        File file = fileChooser.showSaveDialog(new Stage());
        if (file != null) {
            return file.getAbsolutePath();

        }
        return "";
    }

    public static boolean createFile(String path, byte[] data) {
        try {
            InputStream b = new ByteArrayInputStream(data);
            OutputStream out = new FileOutputStream(path);
            int tamInput;
            byte[] datosPdf = new byte[1024];
            while ((tamInput = b.read(datosPdf)) != -1) {
                out.write(datosPdf, 0, tamInput);
            }
            return true;
        } catch (Exception e) {
            System.out.println(e.toString());
            return false;
        }
    }
}
