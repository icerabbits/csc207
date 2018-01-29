package appDisplayLayout.buttonLayout;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import manageFiles.ImageFile;
import runApplication.Main;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class PaneImageButtonManager extends PaneButtonManager{

    public PaneImageButtonManager(Main app){
        super(app);
    }


    /**Helper method used in the move image button. It takes a string, uses it to initialize an instance of File, which
     * is then used to intitialize an instance of Path. Using the static File method move, we take the old file path of
     * the image and change it to fall under the new file path. The imageFile's fileDir variable is also changed in
     * order to keep the class pointing to the same image.
     *
     * @param dirPath the desired directory that the file will be moved to.
     * @param imageFile The image file associated with the selected image*/
    private static void moveImageFileToDir(String dirPath, ImageFile imageFile) {
        if (imageFile != null && !dirPath.equals("")) {
            File oldDir = new File(imageFile.getFileDir());
            String fileName = imageFile.getName();
            Path oldDirPath = oldDir.toPath();
            Path source = oldDirPath.resolve(fileName);
            File newDir = new File(dirPath);
            Path newDirPath = newDir.toPath();
            try {
                Files.move(source, newDirPath.resolve(source.getFileName()), REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();

            }
            imageFile.setFileDir(newDirPath.toString());
        }
        //Adapted from: https://docs.oracle.com/javase/8/docs/api/java/nio/file/Files.html#move-java.nio.file.
        //Path-java.nio.file.Path-java.nio.file.CopyOption...-
    }


    /** Adds move image button to the grid. The button takes the currently selected image and moves it to the current
     * directory.
     *
     * @param searchDirectoryButton the search directory button is used throughout the program in
     * order to update the display
     * @param text The text field that gives the input used to determine the directory the image will be moved to
     * */
    public void addMoveImageButton(TextField text, Button searchDirectoryButton){
        Button moveImageToDirectoryButton = new Button("Place Image");

        moveImageToDirectoryButton.setOnAction(event -> {
            if(new File(text.getText()).isDirectory()){
                moveImageFileToDir(text.getText(), this.getApp().getImageFile());
                searchDirectoryButton.fire();
            }

        });

        PaneButton newButton = new PaneButton(moveImageToDirectoryButton, 0, 0, Pos.CENTER_LEFT);
        this.addButtonToGrid(newButton, this.getApp().getBase().getImageSubGrid());
    }


    /** Adds open image button to the grid. Button takes the current image and opens it in the desktop using the default
     * image viewer of the OS.
     **/
    public void addOpenImageButton(){
        Button openImageButton = new Button("Open Image");

        openImageButton.setOnAction(event -> {
            try {
                if (Desktop.isDesktopSupported()) {
                    File directory = new File(this.getApp().getImageFile().getFileDir());
                    Path directoryPath = directory.toPath();
                    Path imagePath = directoryPath.resolve(this.getApp().getImageFile().getName());
                    Desktop.getDesktop().open(imagePath.toFile());
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        });

        PaneButton newButton = new PaneButton(openImageButton, 3, 0, Pos.CENTER_LEFT);
        this.addButtonToGrid(newButton, this.getApp().getBase().getImageSubGrid());

    }

    /** Adds search similar image button to the grid. The button calls the method searchSimilar from class ImageFile
     **/

    public void addSearchSimilarImageButton(){
        Button searchSimilarImageButton = new Button("Search Image");

        searchSimilarImageButton.setOnAction(event -> {
            try {
                this.getApp().getImageFile().searchSimilar();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        PaneButton newButton = new PaneButton(searchSimilarImageButton, 1, 0, Pos.CENTER_RIGHT);
        this.addButtonToGrid(newButton, this.getApp().getBase().getImageSubGrid());
    }
}
