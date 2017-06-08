package client_aib_labswp_2017_ss_entnahmeapp.View.Model.model_List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.hhn.exceptions.database.FetchException;
import de.hhn.exceptions.database.StoreException;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Created by SHauck on 17.05.2017.
 */
public abstract class PersistentObject {

    public static final long INVALID_OBJECT_ID = 0;
    public long objectID;

    public PersistentObject(long objectID) {
        this.objectID = objectID;
    }


    /**
     * @return the id of the objects
     */
    public long getObjectID() {
        return objectID;
    }

    public void setObjectID(long id) {
        this.objectID = id;
    }

    /**
     * return if the object is already stored in the database
     *
     * @return true if the object is stored, false if not
     */
    @JsonIgnore
    public boolean isPersistent() {
        if (objectID != INVALID_OBJECT_ID || objectID > 0) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * this method checks if the string is null or not
     * and sets the String in the {@link PreparedStatement}
     *
     * @param statement {@link PreparedStatement}
     * @param i int
     * @param s  {@link String}
     * @throws SQLException
     */
    public void checkIfStringNullPreparedStatement(PreparedStatement statement, int i, String s) throws SQLException{

        if (s == null) {
            statement.setNull(i, Types.VARCHAR);
        }
        else {
            statement.setString(i, s);
        }
    }

    /**
     * this method checks if the Date is null or not
     * and sets the String in the {@link PreparedStatement}
     *
     * @param statement {@link PreparedStatement}
     * @param i int
     * @param d {@link Date}
     * @throws SQLException
     */
    public void checkIfDateNullPreparedStatement(PreparedStatement statement, int i,Date d)throws SQLException{

        if (d == null) {
            statement.setNull(i, Types.DATE);
        }
        else {
            java.sql.Date sqlDate = new java.sql.Date(d.getTime());
            statement.setDate(i, sqlDate);
        }
    }


    public void checkIfTimeStampNull(PreparedStatement statement,int i,Timestamp t)throws FetchException{

        try{
            if (t == null) {
                statement.setNull(i, Types.TIMESTAMP);
            }
            else {
                statement.setTimestamp(i,t);
            }
        }
        catch (SQLException e){

            throw new FetchException("error in checkIfTimeStampNull",e);
        }

    }



    /**
     * prases a {@link LocalDateTime} object in a {@link java.sql.Date}
     * @param dateTime {@link LocalDateTime}
     * @return date {@link java.sql.Date}
     */
    public java.sql.Date parseLocalDateTime(LocalDateTime dateTime) {

        if (dateTime != null) {
            return new java.sql.Date(java.sql.Date.valueOf(dateTime.toLocalDate()).getTime());
        }
        else {
            return null;
        }
    }

    /**
     * prases a {@link LocalDateTime} object in a {@link Timestamp}
     * @param localDateTime {@link LocalDateTime}
     * @return timestamp {@link Timestamp}
     */
    public Timestamp parseLocaleDatTimeinTimestamp(LocalDateTime localDateTime){

        Timestamp timestamp = Timestamp.valueOf(localDateTime);

        return timestamp;
    }

    /**
     * checks if the given object is NULL
     *
     * @param index int
     * @param preparedStatement {@link PreparedStatement}
     * @param object {@link PersistentObject}
     * @throws SQLException
     */
    public void isObjectNull(int index, PreparedStatement preparedStatement,PersistentObject object)throws FetchException {

        try {
            if(object == null){
                preparedStatement.setNull(index, Types.BIGINT);
            }
            else {
                preparedStatement.setLong(index,object.getObjectID());
            }
        }
        catch (SQLException e){
            throw new FetchException("error in isObjectNull",e);
        }
    }


    /**
     * store and updated the object
     *
     * @return
     */
    public abstract long store(Connection connection) throws SQLException, StoreException;
}
