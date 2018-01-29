package serializabe;
import java.io.*;
import java.util.ArrayList;

/**
 * Save
 * Export give ArrayList into a serialize file
 * Imprt from a serialized file name "Save.ser"
 *
 * @author Gini Chin
 */

public class Save {

    /**
     * Export an ArrayList into a serializable file "Save.ser"
     * If the file already exist, it overwrites it
     * The ArrayList currently contains at index 0 ImageManager
     * index 1 a String (recording the directory the user is at before closing the program
     * The ArrayList can be expanded as the program grows
     *
     * @param  ArrayList o Contains ImageManager (index 0) and String (index 1)
     * @see Save.ser file in the same folder
     */
    public void exportData(ArrayList o){
        try {
            FileOutputStream fileOut = new FileOutputStream("Save.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(o);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    /**
     * Import for  a serializable file "Save.ser"
     * If the file does not exist, exception is captured
     * The Serializable should contain an ArrayList which contains
     *  index 0 ImageManager
     * index 1 a String (recording the directory the user is at before closing the program
     * The ArrayList in the serializable file will be put into the given ArrayList o
     *
     * @param  ArrayList o An ArrayList that will inherit the ArrayList from Save.ser
     */
    public Object loadData(ArrayList o) {
        try {
            FileInputStream fileIn = new FileInputStream("Save.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            o = (ArrayList) in.readObject();
            in.close();
            fileIn.close();
            return o;
        } catch (IOException i) {
            //i.printStackTrace();
            return "Nothing to Load From";
        } catch (ClassNotFoundException c) {
            //c.printStackTrace();
            return "SaveAndLoad class not found";
        }
    }
}
