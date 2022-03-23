package GUI.Model;

import java.io.File;

/**
 * Model of AppController.
 * Stores the current openFile.
 *
 * @author
 *      Amorison Nathan
 *      Lemaire Emilien
 * @version 1.0.0
 */
public class AppModel {
    private File loadedFile;

    //constructors

    /**
     * Constructor.
     */
    public AppModel(){

    }

    //setters

    /**
     * Set the file.
     * @param file
     *      Scene file.
     */
    public void setFile(File file){
        loadedFile = file;
    }

    //getters

    /**
     * Get the file.
     * @return
     *      Scene file.
     */
    public File getFile(){
        return loadedFile;
    }
}
