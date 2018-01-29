package appDisplayLayout.buttonLayout;

import javafx.geometry.Pos;
import javafx.scene.control.Button;


/**This class's purpose is purely to store and give the information of a specific button in the grid pane of the
 * display*/
public class PaneButton {
    private Button button;
    private int colIndex;
    private int rowIndex;
    private Pos position;

    /**The class constructor. It sets the variables of the class based on the arguments given
     *
     * @param button The button that the PaneButton keeps track of. It has its own functionality and parameters
     * @param colIndex The column index of the button's cell within the grid
     * @param rowIndex The row index of the button's cell within the grid
     * @param position The position of the button within the grid cell*/
    public PaneButton(Button button, int colIndex, int rowIndex, Pos position){
        this.button = button;
        this.colIndex = colIndex;
        this.rowIndex = rowIndex;
        this.position = position;
    }

    /**The getter method for the variable button.
     * @return the button variable of this instance of class PaneButton*/
    public Button getButton() {
        return button;
    }


    /**The getter method for the variable button.
     * @return the column index of the button's cell in the grid*/
    public int getColIndex() {
        return colIndex;
    }

    /**The getter method for the variable button.
     * @return the row index of the button's cell in the grid*/
    public int getRowIndex() {
        return rowIndex;
    }

    /**The getter method for the variable button.
     * @return the position of the button in the grid cell*/
    public Pos getPosition() {
        return position;
    }
}
