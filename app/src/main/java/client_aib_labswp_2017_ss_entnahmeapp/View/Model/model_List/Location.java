package client_aib_labswp_2017_ss_entnahmeapp.View.Model.model_List;

import de.hhn.exceptions.database.FetchException;
import de.hhn.exceptions.database.StoreException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.InvalidParameterException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by SHauck on 12.05.2017.
 */
public class Location extends PersistentObject {

    private static final Logger log = LoggerFactory.getLogger(Location.class);

    private final String locationName;
    private final Procedure[] procedures;

    public Location(long objectID, String name, Procedure[] procedures) {
        super(objectID);
        this.locationName = name;
        this.procedures = procedures;
    }

    public Location(String name, Procedure[] procedures) {
        super(INVALID_OBJECT_ID);
        this.locationName = name;
        this.procedures = procedures;
    }

    /**
     * returns the {@link Location} with the corresponding name from the database
     *
     * @param connection
     *         can not be {@code null}
     * @param name
     *         must not be {@code null} or empty
     *
     * @return the location
     * @throws FetchException
     *         if a database error occurs
     * @throws InvalidParameterException
     *         if no corresponding Element can be found
     */
    public static Location getLocationByName(Connection connection, String name) throws FetchException {
        log.info("getLocationByName called with name: " + name);
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT l.id, l.name, p.id, p.name, p.shortcut " +
                            "FROM location l, location_procedure lp, procedure p " +
                            "WHERE l.id = lp.locationid " +
                            "AND lp.procedureid = p.id " +
                            "AND l.name = ?");
            preparedStatement.setString(1, name);
            boolean locationInDatabase = false;
            ResultSet resultSet = preparedStatement.executeQuery();

            long id = 0;
            String locName = "";
            List<Procedure> proceduresList = new ArrayList<>();

            while (resultSet.next()) { //Location is in database
                locationInDatabase = true;
                id = resultSet.getLong(1);
                locName = resultSet.getString(2);
                proceduresList.add(new Procedure(resultSet.getLong(3), resultSet.getString(4), resultSet.getString(5)));
            }

            if (!locationInDatabase) { //Location is not in database. Exception, later handling from new locations
                throw new InvalidParameterException("The object with the corresponding name could not be found in the database. Name: " + name);
            } else { //Location is in database
                Procedure[] procedures = proceduresList.toArray(new Procedure[proceduresList.size()]);
                return new Location(id, locName, procedures);
            }
        } catch (SQLException e) {
            throw new FetchException("A database error occured...", e);
        }
    }

    /**
     * this method stores or updates the Location in the Database
     * if the object id is INVALID_OBJECT_ID the method adds a new Location to the database
     * if the PrimerListStatus id is already in the database it updates the Location in the DB
     *
     * @param connection needs a active connection to the database
     * @return long , the ID of the object in the database
     * @throws SQLException if there is a error in the database
     */
    @Override
    public long store(Connection connection) throws StoreException {

        try {
            if (getObjectID() == INVALID_OBJECT_ID) {

                long[] proceduresIDs = new long[procedures.length];

                for (int i = 0; i < procedures.length; i++) {

                    proceduresIDs[i] = procedures[i].store(connection);
                }

                PreparedStatement preparedStatement = connection.prepareStatement(
                        "INSERT INTO Location(Name) VALUES (?)", PreparedStatement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, locationName);
                preparedStatement.execute();

                ResultSet keys = preparedStatement.getGeneratedKeys();
                keys.next();
                long id = keys.getLong(1);
                setObjectID(id);

                for (int i = 0; i < proceduresIDs.length; i++) {

                    PreparedStatement preparedStatement1 = connection.prepareStatement("INSERT INTO Location_Procedure(LocationID,ProcedureID) VALUES (?,?)");
                    preparedStatement1.setLong(1, getObjectID());
                    preparedStatement1.setLong(2, proceduresIDs[i]);
                    preparedStatement1.execute();
                }
                return getObjectID();
            } else {

                PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Location SET name = ? WHERE id = ? ");
                preparedStatement.setString(1, locationName);
                preparedStatement.setLong(2, getObjectID());
                preparedStatement.execute();

                return getObjectID();
            }
        }
        catch (Exception e){
            throw new StoreException("Error occurred while storing the Location",e);
        }
    }

    /**
     * @return a String representation of the Location
     */
    @Override
    public String toString() {
        return "{id: " + getObjectID() + ", name: " + getLocationName() + ", Procedures: " + Arrays.toString(getProcedures()) + "}";
    }

    //******************************************************************************************************************
    //Get and Set Methods
    //******************************************************************************************************************

    public String getLocationName() {
        return locationName;
    }

    public Procedure[] getProcedures() {
        return procedures;
    }
}
