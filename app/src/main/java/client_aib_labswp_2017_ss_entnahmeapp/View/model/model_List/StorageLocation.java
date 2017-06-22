package client_aib_labswp_2017_ss_entnahmeapp.View.model.model_List;


import android.os.Parcel;
import android.os.Parcelable;

/**
 *
 */
public class StorageLocation implements Parcelable{

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

    protected StorageLocation(Parcel in) {
        tower = in.readInt();
        box = in.readInt();
        row = in.readInt();
        objectID = in.readLong();
    }

    public static final Creator<StorageLocation> CREATOR = new Creator<StorageLocation>() {
        @Override
        public StorageLocation createFromParcel(Parcel in) {
            return new StorageLocation(in);
        }

        @Override
        public StorageLocation[] newArray(int size) {
            return new StorageLocation[size];
        }
    };

    /**
     * @return String representation of {@link StorageLocation} object.
     */
    @Override
    public String toString() {
        return "T: " + tower + "; B: " + box + "; Col: " + column + "; Row: " + row;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(tower);
        dest.writeInt(box);
        dest.writeInt(row);
        dest.writeLong(objectID);
    }
}
