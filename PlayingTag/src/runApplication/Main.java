package runApplication;

import appDisplayLayout.BaseLayout;
import appDisplayLayout.buttonLayout.PaneButtonManager;
import appDisplayLayout.buttonLayout.PaneDirectoryButtonManager;
import appDisplayLayout.buttonLayout.PaneImageButtonManager;
import appDisplayLayout.buttonLayout.PaneTagButtonManager;
import appDisplayLayout.listLayout.ListManager;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import logger.Log;
import manageFiles.ImageFile;
import manageFiles.ImageManager;
import serializabe.Save;

import java.io.IOException;
import java.util.ArrayList;

/**
 *This class runs the program and calls the serializable data from Save
 *
 * */
public class Main extends Application{

    private BaseLayout base = new BaseLayout();
    private ImageFile imageFile = null;
    private ImageManager imgManager = new ImageManager();
    private Save saveData = new Save();
    private ArrayList container = new ArrayList();
    private String root;
    private TextField rootDirectoryText;
    private Log log = new Log("Playing Tag Log");

    public Main() throws IOException {
    }

    public static void main(String[] args) {
        launch(args);
    }

    /** Method start does as the name implies and starts the program. It checks for pre-existing save data and calls
     * helper methods in order to build the javaFx scene.
     * @param primaryStage This is the stage that the program will be displayed on*/
    @Override
    public void start(Stage primaryStage) throws Exception {
        checkSaveData();

        buildStage(primaryStage);

        primaryStage.show();
    }

    /** This method calls method buildBasicLayout() from the variable base in order to lay down the ground work of
     * building the stage display. It initializes the button and list managers that will add the buttons and lists
     * into the program. It creates labels using base.addLabels and creates a text field. Since the search button is
     * intricate to many functionalities of the lists and buttons, it is intitialized here using the PaneButtonManager.
     * The scene is also scaled and set here.
     *
     * @param stage The stage the program is displayed on*/

    private void buildStage(Stage stage){
        base.buildBasicLayout(stage);

        ListManager listManager = new ListManager(this);

        PaneButtonManager paneButtonManager = new PaneButtonManager(this);

        PaneDirectoryButtonManager dirButtonManager = new PaneDirectoryButtonManager(this);

        PaneTagButtonManager tagButtonManager = new PaneTagButtonManager(this);

        PaneImageButtonManager imgButtonManager = new PaneImageButtonManager(this);

        base.addLabels();

        rootDirectoryText = new TextField(root);
        base.getGrid().add(rootDirectoryText, 1, 1);

        listManager.addLists();

        paneButtonManager.addSearchDirectoryButton(rootDirectoryText, listManager.getFilesList());

        buildTagStationLayout(paneButtonManager.getSearchDirectoryButton(), tagButtonManager, listManager);

        buildFileStationLayout(imgButtonManager, dirButtonManager, listManager,
                paneButtonManager.getSearchDirectoryButton());

        Scene scene = new Scene(base.getGrid(), 1000, 800);
        stage.setScene(scene);
    }

    /** The method builds the interactable parts of the display that deal with the tags of each image. It creates the
     * text field used to add and delete tags, the dropdown list of tags, and the buttons for each of the program's
     * capabilities relating to the tags.
     *
     * @param listManager this list manager is used as the argument for the addReverseNameButton
     * @param paneTagButtonManager this tag button manager initializes all of the buttons in the grid
     * @param searchDirectoryButton as said previously, the search directory button is used throughout the program in
     * order to update the display*/

    private void buildTagStationLayout(Button searchDirectoryButton,
                                       PaneTagButtonManager paneTagButtonManager, ListManager listManager){
        TextField tagText = new TextField();
        base.getTagSubGrid().add(tagText, 0, 1);

        ObservableList<String> tagOptions =
                FXCollections.observableArrayList(imgManager.getAllAvailableTags());
        ComboBox tagsBox = new ComboBox(tagOptions);
        tagsBox.setVisibleRowCount(6);

        base.getTagSubGrid().add(tagsBox, 1, 3);

        paneTagButtonManager.addTaggerButton(tagText, tagOptions, searchDirectoryButton);

        paneTagButtonManager.addRemoveTagButton(tagText, tagOptions, searchDirectoryButton);

        paneTagButtonManager.addTagsBoxButton(tagsBox, tagText);

        paneTagButtonManager.addSaveButton();

        paneTagButtonManager.addReverseNameButton(listManager, searchDirectoryButton);
    }

    /** The method builds the interactable parts of the display that deal with the files on the computer, including the
     * file list and buttons at the top of the display. It creates an ImageView that the program uses to view any
     * selected image. It creates a text field is used to see the current path of the image.
     *
     * @param paneImageButtonManager This is used to intialize buttons that are used to modify the image file
     * @param paneDirectoryButtonManager This is used to initialize buttons that are used to navigate directories
     * @param searchDirectoryButton as said previously, the search directory button is used throughout the program in
     * order to update the display*/

    private void buildFileStationLayout(PaneImageButtonManager paneImageButtonManager,
                                        PaneDirectoryButtonManager paneDirectoryButtonManager,
                                        ListManager listManager, Button searchDirectoryButton){

        ImageView picture = new ImageView();
        base.getGrid().add(picture, 1, 4);

        TextField imagePathText = new TextField();
        base.getGrid().add(imagePathText, 1, 2);

        searchDirectoryButton.fire();

        listManager.addFunctionToFileList(rootDirectoryText, searchDirectoryButton,
                picture, imagePathText);

        paneImageButtonManager.addMoveImageButton(rootDirectoryText, searchDirectoryButton);

        paneDirectoryButtonManager.addOpenDirectoryButton(rootDirectoryText);

        paneDirectoryButtonManager.addPreviousDirectoryButton(rootDirectoryText, searchDirectoryButton);

        paneImageButtonManager.addOpenImageButton();

        paneImageButtonManager.addSearchSimilarImageButton();

        paneDirectoryButtonManager.addSetRootDirectoryButton(rootDirectoryText, this);
    }

    /** Getter method for the base variable of Main.
     *
     * @return BaseLayout This is the base variable of this instance of Main.*/
    public BaseLayout getBase() {
        return base;
    }

    public void setImageFile(ImageFile newImageFile){
        this.imageFile = newImageFile;
    }

    /** Getter method for the imageFile variable of Main.
     *
     * @return BaseLayout This is the base variable of this instance of Main.*/
    public ImageFile getImageFile() {
        return imageFile;
    }

    public ImageManager getImgManager() {
        return imgManager;
    }

    public Save getSaveData() {
        return saveData;
    }

    public ArrayList getContainer() {
        return container;
    }

    public Log getLog() {
        return log;
    }

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    /**Checks if save data exists. If it does, the method calls the data into the program. If it does not, the method
     * creates a new instance of it. Container is serialized with root and imgManger as its contents.
     **/

    private void checkSaveData(){
        if (saveData.loadData(container).equals("Nothing to Load From")) {
            imgManager = new ImageManager();
            root = System.getProperty("user.home");
            //defaultPath;
        }

        else {
            container = (ArrayList) saveData.loadData(container);
            imgManager = (ImageManager) container.get(0);
            root = (String) container.get(1);
        }
    }

    /** Stops and exits the program. Any data created during the running session that relates to tags or the root
     * directory is saved.*/
    @Override
    public void stop(){
        container.clear();
        container.add(imgManager);
        container.add(root);
        saveData.exportData(container);
    }
}
