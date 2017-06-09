package client_aib_labswp_2017_ss_entnahmeapp.View.Model.model_List;


/**
 * Created by SimonHauckLaptop on 27.04.2017.
 */
public class StorageLocation {

    private int tower;
    private int box;
    private char column;
    private int row;
    private long objectID;

    public StorageLocation(int tower, int box, char column, int row, long objectID) {
        this.tower = tower;
        this.box = box;
        this.column = column;
        this.row = row;
        this.objectID = objectID;
    }

    public StorageLocation() {
    }

    public int getTower() {
        return tower;
    }

    public int getBox() {
        return box;
    }

    public char getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    public long getObjectID() {
        return objectID;
    }

    public void setTower(int tower) {
        this.tower = tower;
    }

    public void setBox(int box) {
        this.box = box;
    }

    public void setColumn(char column) {
        this.column = column;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setObjectID(long objectID) {
        this.objectID = objectID;
    }
}
