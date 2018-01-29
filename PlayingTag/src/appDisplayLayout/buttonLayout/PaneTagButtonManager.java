package appDisplayLayout.buttonLayout;

import appDisplayLayout.listLayout.ListManager;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import runApplication.Main;

public class PaneTagButtonManager extends PaneButtonManager{

    public PaneTagButtonManager(Main app){
        super(app);
    }

    public void addTaggerButton(TextField text, ObservableList<String> tagOptions, Button searchDirectoryButton){
        Button addTagButton = new Button("Add Tag");

        addTagButton.setOnAction(event -> {
            if (!text.getText().equals("") && (this.getApp().getImageFile() != null) && !text.getText().startsWith("@")
                    && !text.getText().contains(".")
                    && !this.getApp().getImageFile().getFileName().equals(text.getText())){
                String oldName = this.getImageName();
                this.getApp().getImageFile().addTags(text.getText());
                this.getApp().getImgManager().addToAllAvailableTags(text.getText());
                String newName = this.getImageName();
                tagOptions.clear();
                tagOptions.addAll(this.getApp().getImgManager().getAllAvailableTags());
                searchDirectoryButton.fire();
                this.getApp().getLog().getLogger().info("Adding Tags: " + oldName + " changed to " + newName);
            }
        });

        PaneButton newButton = new PaneButton(addTagButton, 1,1, Pos.CENTER_LEFT);
        this.addButtonToGrid(newButton, this.getApp().getBase().getTagSubGrid());
    }

    //Helper Method for Logging Changes
    private String getImageName(){
        String temp = "";
        for (int i=0; i<this.getApp().getImageFile().getCurTags().size(); i++){
            temp += " " + this.getApp().getImageFile().getCurTags().get(i);
        }

        return this.getApp().getImageFile().getFileName() + temp + this.getApp().getImageFile().getFileType();
    }

    public void addRemoveTagButton(TextField text, ObservableList<String> tagOptions, Button searchDirectoryButton){
        Button removeTagButton = new Button("Remove Tag");

        removeTagButton.setOnAction(event -> {
            if (!text.getText().equals("")){
                String oldName = this.getImageName();
                this.getApp().getImageFile().removeTags(text.getText());
                String newName = this.getImageName();

                tagOptions.clear();
                tagOptions.addAll(this.getApp().getImgManager().getAllAvailableTags());
                searchDirectoryButton.fire();
                this.getApp().getLog().getLogger().info("Removing Tags: " + oldName + " changed to " + newName);
            }
        });

        PaneButton newButton = new PaneButton(removeTagButton, 2,1, Pos.CENTER_LEFT);
        this.addButtonToGrid(newButton, this.getApp().getBase().getTagSubGrid());
    }

    public void addTagsBoxButton(ComboBox tagsBox, TextField text){
        Button tagsBoxButton = new Button("Use Tag");

        tagsBoxButton.setOnAction(event -> {
            if (tagsBox.getValue() != null){
                text.setText(tagsBox.getValue().toString());
            }
        });

        PaneButton newButton = new PaneButton(tagsBoxButton, 2,3, Pos.CENTER_LEFT);
        this.addButtonToGrid(newButton, this.getApp().getBase().getTagSubGrid());
    }

    public void addReverseNameButton(ListManager listManager, Button searchDirectoryButton){
        Button pastNameButton = new Button("Reverse Name");

        pastNameButton.setOnAction(event -> {
            if(!listManager.getLog().getSelectionModel().getSelectedItems().toString().equals("")) {
                String oldName = this.getImageName();
                this.getApp().getImageFile().revertToPastName(listManager.getLog().getSelectionModel().
                        getSelectedItems().toString());
                String newName = this.getImageName();

                searchDirectoryButton.fire();
                this.getApp().getLog().getLogger().info("Using Past Name: " + oldName + " changed to " + newName);
            }
        });

        PaneButton newButton = new PaneButton(pastNameButton, 0,4, Pos.CENTER_LEFT);
        this.addButtonToGrid(newButton, this.getApp().getBase().getTagSubGrid());
    }

    public void addSaveButton(){
        Button saveButton = new Button("Save tags");

        saveButton.setOnAction(event -> this.getApp().getSaveData().exportData(this.getApp().getContainer()));

        PaneButton newButton = new PaneButton(saveButton, 2, 0, Pos.CENTER);
        this.addButtonToGrid(newButton, this.getApp().getBase().getImageSubGrid());
    }
}
