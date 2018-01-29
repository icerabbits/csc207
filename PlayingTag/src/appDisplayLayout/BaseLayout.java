package appDisplayLayout;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class BaseLayout {

    private GridPane grid;
    private GridPane tagSubGrid;
    private GridPane imageSubGrid;

    public BaseLayout(){

    }

    public void buildBasicLayout(Stage stage){
        stage.setTitle("Playing Tag");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER); // Centers the grid
        grid.setHgap(10); // Sets the space between each pane
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25)); // Spaces around the edges

        GridPane tagSubGrid = new GridPane();
        tagSubGrid.setAlignment(Pos.TOP_LEFT);
        tagSubGrid.setHgap(10);
        tagSubGrid.setVgap(20);
        grid.add(tagSubGrid,1, 5);

        GridPane imageSubGrid = new GridPane();
        imageSubGrid.setAlignment(Pos.TOP_LEFT);
        imageSubGrid.setHgap(10);
        imageSubGrid.setVgap(20);
        grid.add(imageSubGrid,1, 3);

        Text sceneTitle = new Text("Welcome");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(sceneTitle, 0, 0, 2, 1);

        this.grid = grid;
        this.tagSubGrid = tagSubGrid;
        this.imageSubGrid = imageSubGrid;
    }

    public GridPane getGrid() {
        return grid;
    }

    public GridPane getImageSubGrid() {
        return imageSubGrid;
    }

    public GridPane getTagSubGrid() {
        return tagSubGrid;
    }

    public void addLabels(){
        Label fileLabel = new Label("File Directory: ");
        grid.add(fileLabel, 0, 1);

        Label tagInput =  new Label("Enter a Tag: ");
        tagSubGrid.add(tagInput, 0, 0);

        Label availableTagLabel = new Label("Available tags: ");
        tagSubGrid.add(availableTagLabel, 0, 3);
    }
}
