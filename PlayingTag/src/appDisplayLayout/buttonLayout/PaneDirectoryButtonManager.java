package appDisplayLayout.buttonLayout;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import runApplication.Main;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class PaneDirectoryButtonManager extends PaneButtonManager{

    public PaneDirectoryButtonManager(Main app){
        super(app);
    }

    /** Add previous directory button to the grid. Button takes the user to the parent directory of the current
     * directory. The button does nothing if the user is already in user.home.
     *
     * @param text Text field that gives the input of what the current directory is.
     * @param searchDirectoryButton as said previously, the search directory button is used throughout the program in
     * order to update the display*/

    public void addPreviousDirectoryButton(TextField text, Button searchDirectoryButton){
        Button previousDirectory = new Button("Previous directory");

        previousDirectory.setOnAction(e -> {
            String dir = text.getText();
            if (!dir.isEmpty() && !dir.equals(System.getProperty("user.home")) &&
                    dir.contains(System.getProperty("user.home"))) {
                try {
                    text.setText(dir.substring(0, dir.lastIndexOf("/")));
                    searchDirectoryButton.fire();
                } catch (NullPointerException ignored) {
                }
            }
        });


    PaneButton newButton = new PaneButton(previousDirectory, 2, 2, Pos.TOP_LEFT);
        this.addButtonToGrid(newButton, this.getApp().getBase().getGrid());
    }

    /** Adds an open directory button to the grid. Button goes into the desktop and opens the selected directory in the
     * default file browser on the OS.
     *
     * @param text The text field that gives the directory path*/
    public void addOpenDirectoryButton(TextField text){
        Button openDirectory = new Button("Open directory");

        openDirectory.setOnAction(event -> {
            if (Desktop.isDesktopSupported()) {
                new Thread(() -> {Desktop desktop = Desktop.getDesktop();
                    File dirToOpen;
                    try {
                        dirToOpen = new File(text.getText());
                        try {
                            desktop.open(dirToOpen);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } catch (IllegalArgumentException iae) {
                        System.out.println("File Not Found");
                    }}).start();
            }

            //Adapted from: https://stackoverflow.com/questions/15875295/open-a-folder-in-explorer-using-java
        });

        PaneButton newButton = new PaneButton(openDirectory, 2, 3, Pos.TOP_LEFT);
        this.addButtonToGrid(newButton, this.getApp().getBase().getGrid());
    }

    /** Adds a button that sets the root directory of the program. Places the button in column 3, row 1 of the main
     * grid
     *
     * @param app The instance of Main that uses this method and contains the root that will be changed
     * @param text The text field form where the input is taken for the new root directory*/
    public void addSetRootDirectoryButton(TextField text, Main app){
        Button newRootDirectory = new Button("Set Root");

        newRootDirectory.setOnAction(event ->{
            if(new File(text.getText()).isDirectory()){
                app.setRoot(text.getText());
            }
        });
        PaneButton newButton = new PaneButton(newRootDirectory, 3, 1, Pos.CENTER_LEFT);
        this.addButtonToGrid(newButton, this.getApp().getBase().getGrid());
    }
}
