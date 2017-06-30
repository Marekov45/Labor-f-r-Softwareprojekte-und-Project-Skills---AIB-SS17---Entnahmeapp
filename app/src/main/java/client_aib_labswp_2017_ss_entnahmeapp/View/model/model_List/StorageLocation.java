package client_aib_labswp_2017_ss_entnahmeapp.View.model.model_List;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * The {@link StorageLocation} contains the coordinate of the storage places for a primer.
 */
public class StorageLocation implements Parcelable {

    private int tower;
    private int box;
    private char column;
    private int row;
    private long objectID;

    /**
     * Initializes the {@link StorageLocation}.
     *
     * @param tower    coordinate for the tower.
     * @param box      coordinate for the box.
     * @param column   coordinate for the column.
     * @param row      coordinate for the row.
     * @param objectID unique id. It must not be {@code null}.
     */
    public StorageLocation(int tower, int box, char column, int row, long objectID) {
        this.tower = tower;
        this.box = box;
        this.column = column;
        this.row = row;
        this.objectID = objectID;
    }

    public StorageLocation() {
    }

    /**
     * Initializes the Parcel.
     *
     * @param in container for a message.
     */
    protected StorageLocation(Parcel in) {
        tower = in.readInt();
        box = in.readInt();
        row = in.readInt();
        objectID = in.readLong();
    }

    //Generates instances of this Parcelable class from a Parcel.
    public static final Creator<StorageLocation> CREATOR = new Creator<StorageLocation>() {
        /**
         * Create a new instance of the Parcelable class, instantiating it from the given Parcel whose data had
         * previously been written by
         *
         * @param in the Parcel to read the object's data from.
         * @return A new instance of the Parcelable class
         */
        @Override
        public StorageLocation createFromParcel(Parcel in) {
            return new StorageLocation(in);
        }

        /**
         * Creates a new array of the Parcelable class.
         *
         * @param size size of the array.
         * @return An array of the Parcelable class, with every entry initialized to null
         */
        @Override
        public StorageLocation[] newArray(int size) {
            return new StorageLocation[size];
        }
    };

    /**
     * @return String representation of a {@link StorageLocation} object.
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

    /**
     * @param tower must be greater than zero (<code>0</code>).
     */
    public void setTower(int tower) {
        this.tower = tower;
    }

    /**
     * @param box must be greater than zero (<code>0</code>).
     */
    public void setBox(int box) {
        this.box = box;
    }

    /**
     * @param column must be greater than zero (<code>0</code>).
     */
    public void setColumn(char column) {
        this.column = column;
    }

    /**
     * @param row must be greater than zero (<code>0</code>).
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * @param objectID must not be {@code null}.
     */
    public void setObjectID(long objectID) {
        this.objectID = objectID;
    }

    /**
     * Describe the kinds of special objects contained in this Parcelable instance's marshaled representation.
     *
     * @return a bitmask indicating the set of special object types marshaled by this Parcelable object instance
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flattens the object in to a Parcel.
     *
     * @param dest  the Parcel in which the object should be written.
     * @param flags additional flags about how the object should be written.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(tower);
        dest.writeInt(box);
        dest.writeInt(row);
        dest.writeLong(objectID);
    }
}
