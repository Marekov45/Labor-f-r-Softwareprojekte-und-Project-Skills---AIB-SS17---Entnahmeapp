package client_aib_labswp_2017_ss_entnahmeapp.View.Model.model_List;

import de.hhn.exceptions.database.StoreException;
import de.hhn.restservice_storage.model.StorageCoordinate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by SimonHauckLaptop on 27.04.2017.
 */
public class StorageLocation extends PersistentObject {

    private int tower;
    private int box;
    private char column;
    private int row;

    public StorageLocation(long objectID, int tower, int box, char column, int row) {
        super(objectID);

        this.tower = tower;
        this.box = box;
        this.column = column;
        this.row = row;
    }

    public StorageLocation(int tower, int box, char column, int row) {
        super(INVALID_OBJECT_ID);

        this.tower = tower;
        this.box = box;
        this.column = column;
        this.row = row;
    }

    public StorageLocation(StorageCoordinate storageCoordinate) {
        super(INVALID_OBJECT_ID);
        this.tower = storageCoordinate.getTower();
        this.box = storageCoordinate.getBox();
        this.column = storageCoordinate.getColumn();
        this.row = storageCoordinate.getRow();
    }

    /**
     * @return String representation of {@link StorageLocation} onject.
     */
    @Override
    public String toString() {
        return "id: " + getObjectID() + "; Tower: " + tower + "; Box" + box + "; Column: " + column + "; Row: " + row;
    }

    /**
     * this method stores or updates the StorageLocation in the Database
     * if the object id is INVALID_OBJECT_ID the method adds a new StorageLocation to the database
     * if the PrimerListStatus id is already in the database it updates the StorageLocation in the DB
     *
     * @param connection
     *         needs a active connection to the database
     *
     * @return long , the ID of the object in the database
     * @throws SQLException
     *         if there is a error in the database
     */
    @Override
    public long store(Connection connection) throws StoreException {

        try {
            if (getObjectID() == INVALID_OBJECT_ID) {

                PreparedStatement preparedStatement = connection.prepareStatement(
                        "INSERT INTO storagelocation (Tower,Box,S_Column,Row) VALUES (?,?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
                preparedStatement.setInt(1, tower);
                preparedStatement.setInt(2, box);
                preparedStatement.setString(3, String.valueOf(column));
                preparedStatement.setInt(4, row);
                preparedStatement.execute();

                ResultSet keys = preparedStatement.getGeneratedKeys();
                keys.next();
                long id = keys.getLong(1);
                setObjectID(id);

                return getObjectID();
            } else {

                PreparedStatement preparedStatement = connection.prepareStatement("UPDATE storagelocation SET Tower = ?, Box = ?,S_Column = ?, Row = ? WHERE id = ? ");
                preparedStatement.setInt(1, tower);
                preparedStatement.setInt(2, box);
                preparedStatement.setString(3, String.valueOf(column));
                preparedStatement.setInt(4, row);
                preparedStatement.setLong(5, getObjectID());
                preparedStatement.execute();

                return getObjectID();
            }
        } catch (Exception e) {
            throw new StoreException("Error occurred while storing the StorageLocation", e);
        }
    }

    //******************************************************************************************************************
    //Get and Set Methods
    //******************************************************************************************************************
    public int getTower() {
        return tower;
    }

    public void setTower(int tower) {
        this.tower = tower;
    }

    public int getBox() {
        return box;
    }

    public void setBox(int box) {
        this.box = box;
    }

    public char getColumn() {
        return column;
    }

    public void setColumn(char column) {
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }
}
