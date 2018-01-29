package appDisplayLayout.listLayout;

import javafx.event.EventHandler;
import runApplication.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import manageFiles.ImageFile;
import tags.TagManager;

import java.awt.event.MouseEvent;
import java.io.File;

public class ListManager {

    private Main app;
    private ListView files;
    private ListView log;

    public ListManager(Main app){
        this.app = app;
    }


    /**Adds the two lists that contain the files of the current directory and the log of name changes of an imageFile. */
    public void addLists(){
        ListView fileList = new ListView<String>();
        app.getBase().getGrid().add(fileList, 0, 4);
        fileList.setPrefHeight(250);
        this.files = fileList;

        ListView logList = new ListView<String>();
        app.getBase().getGrid().add(logList, 0, 5);
        logList.setPrefHeight(250);
        this.log = logList;
    }


    /** Adds functionality to the file list. On a mouse click the selected file is checked to see if it is an image or
     * a directory. If it is a directory, the file list is updated to show all images and subdirectories in the
     * directory. If it is an image file, the current imageFile of the Main is changed. If the imageFile already
     * exists in imageManager, the imageFile is set to that value. If not, the new imageFile is added. The log
     * of name changes is also changed
     *
     * @param searchDirectoryButton the search directory button is used throughout the program in
     * order to update the display
     *@param text  The text field containing the current directory path
     *@param imageDirLocation The text field containing the currently selected file path
     *@param picture The ImageView that will be updated with the image*/
    public void addFunctionToFileList(TextField text, Button searchDirectoryButton, ImageView picture,
                                      TextField imageDirLocation){

        files.setOnMouseClicked(event -> {
            String fileName = files.getSelectionModel().getSelectedItems().toString();
            File file = new File(text.getText() + "/" + fileName.substring(1, fileName.lastIndexOf("]")));
            String name = file.toString();

            if (file.isDirectory()){
                text.setText(file.toString());
                searchDirectoryButton.fire();
            }
            else {
                Image image = new Image(file.toURI().toString(), 350, 350, true, true);
                picture.setImage(image);

                String imgName = fileName.substring(fileName.lastIndexOf("/") + 1, fileName.lastIndexOf("."));
                String imgPath = text.getText() + fileName.substring(0, fileName.lastIndexOf("[")) + fileName.substring(fileName.lastIndexOf("[") + 1, fileName.lastIndexOf("/") +1 );
                String imgType = fileName.substring(fileName.lastIndexOf("."), fileName.lastIndexOf("]"));

                this.app.setImageFile(new ImageFile(imgName, new TagManager(), imgPath, imgType));

                if (this.app.getImgManager().allImage.isEmpty()){
                    this.app.getImgManager().addImageFile(this.app.getImageFile());
                }else{
                    int i = this.app.getImgManager().exist(name);
                    if (i!=-1) {
                        this.app.setImageFile(this.app.getImgManager().allImage.get(i));
                    }else{
                        this.app.getImgManager().addImageFile(this.app.getImageFile());
                    }
                }



                ObservableList items = FXCollections.observableArrayList(
                        this.app.getImageFile().getAllPastName());
                log.setItems(items);

                imageDirLocation.setText(name);
            }
        });

    }


    public ListView getFilesList() {
        return files;
    }

    public ListView getLog() {
        return log;
    }
}
