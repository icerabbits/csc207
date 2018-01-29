package appDisplayLayout.buttonLayout;

import runApplication.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import manageFiles.FileManager;

import java.io.File;

/**This class's purpose is to create, add functionality, and add PaneButtons to a given grid from Main.*/

public class PaneButtonManager {
    private Button searchDirectoryButton;
    private Main app;

    public PaneButtonManager(Main app){
        this.app = app;
    }

    public Button getSearchDirectoryButton() {
        return searchDirectoryButton;
    }

    public Main getApp() {
        return app;
    }

    /** Adds a button to a given grid. it creates an Hbox for the button to be placed in and the box itself placed in
     * a cell in the grid using the column and row index given by the PaneButton
     *
     * @param button PaneButton has the info on the button and the indexes
     * @param thisGrid The given grid that the button will be placed in*/
    protected void addButtonToGrid(PaneButton button, GridPane thisGrid){
        HBox hBoxBtn = new HBox(10);
        hBoxBtn.setAlignment(button.getPosition());
        hBoxBtn.getChildren().add(button.getButton());
        thisGrid.add(hBoxBtn, button.getColIndex(), button.getRowIndex());
    }

    /**This method adds a search directory button to the grid. The method used each time the button is called checks
     * if an input is a valid directory and no higher than user.home, and then if it is it updates the files list. If it
     * does not satisfy the if statement, the text in the text field is set to user.home.
     *
     * @param fileList The list of files in the directory
     * @param text The text field where the input is taken*/
    public void addSearchDirectoryButton(TextField text, ListView fileList){
        Button searchDirectory = new Button("Search directory");

        // When button is clicked, lists the valid image files of that subdirectory.
        searchDirectory.setOnAction(e -> {
            String dir = text.getText();
            if(dir.startsWith(System.getProperty("user.home")) && new File(dir).isDirectory())
            {ObservableList items = FXCollections.observableArrayList(
                    new FileManager(dir).getSubAndImgDir());
                fileList.setItems(items);
            }
            else{
                text.setText(System.getProperty("user.home"));
            }
        });


    PaneButton newButton = new PaneButton(searchDirectory, 2, 1, Pos.TOP_LEFT);
        this.searchDirectoryButton = searchDirectory;
        this.addButtonToGrid(newButton, app.getBase().getGrid());
    }
}
