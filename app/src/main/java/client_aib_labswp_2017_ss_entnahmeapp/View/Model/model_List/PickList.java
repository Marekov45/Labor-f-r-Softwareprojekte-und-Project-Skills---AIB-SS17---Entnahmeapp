package client_aib_labswp_2017_ss_entnahmeapp.View.Model.model_List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.hhn.exceptions.database.FetchException;
import de.hhn.exceptions.database.StoreException;
import de.hhn.restservice_lab.model.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lschl on 09.05.2017.
 */
public class PickList extends PersistentObject {

    private LocalDateTime entryDate;
    private Location destination;

    private List<PrimerTube> pickList;

    //Ignored for the app communication
    @JsonIgnore
    private PrimerListStatus primerListStatus;

    /**
     * create a valid {@link PickList} object from an order and the list of required primers. The status of the list
     * will be {@link PrimerListStatus#IN_QUEUE}l
     *
     * @param order
     *         can not be {@code null}
     * @param primerTubes
     *         can not be {@code null}
     * @param connection
     *         can not be {@code null}
     *
     * @throws AssertionError
     *         if given parameter is invalid
     * @throws FetchException
     *         if a database error occurs
     */
    public PickList(Order order, List<PrimerTube> primerTubes, Connection connection) throws FetchException {
        super(order.getListID());
        entryDate = LocalDateTime.now();
        destination = Location.getLocationByName(connection, order.getDestination());
        this.pickList = primerTubes;
        primerListStatus = PrimerListStatus.getPrimerListStatusByName(connection, PrimerListStatus.IN_QUEUE);
    }

    public PickList(long objectID, LocalDateTime entryDate, Location destination, List<PrimerTube> primerTubes, PrimerListStatus primerListStatus) {
        super(objectID);

        this.entryDate = entryDate;
        this.destination = destination;
        this.pickList = primerTubes;
        this.primerListStatus = primerListStatus;
    }

    /**
     * create a {@link PickList} that can not be processed. The status of the list will be set to failed. The {@link
     * PrimerTube} objects from {@link PickList#getPickList()} will only contain the {@link PrimerTube#getPrimerUID()}
     *
     * @param order
     *         can not be {@code null}
     * @param connection
     *         can not be {@code null}
     *
     * @throws AssertionError
     *         if given parameter is invalid
     * @throws FetchException
     *         if a database error occurs
     */

    public PickList(Order order, Connection connection) throws FetchException {
        super(order.getListID());
        entryDate = LocalDateTime.now();
        destination = Location.getLocationByName(connection, order.getDestination());
        primerListStatus = PrimerListStatus.getPrimerListStatusByName(connection, PrimerListStatus.FAILED);
        pickList = new ArrayList<>();
//        for (String primerUID : order.getPrimerUID()) {
//            pickList.add(new PrimerTube(primerUID));
//        }
    }

    @Override
    public long store(Connection connection) throws StoreException {

        try {
            PreparedStatement checkID = connection.prepareStatement("SELECT * FROM PickList WHERE id = ?");
            checkID.setLong(1, getObjectID());
            ResultSet resultSet = checkID.executeQuery();

            if (resultSet.next() == false) {
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "INSERT INTO PickList (id,EntryDate,LocationID,PrimerListStatusID) VALUES (?,?,?,?)");

                preparedStatement.setLong(1, getObjectID());
                checkIfTimeStampNull(preparedStatement, 2, parseLocaleDatTimeinTimestamp(entryDate));
                preparedStatement.setLong(3, getDestination().getObjectID());
                isObjectNull(4, preparedStatement, primerListStatus);
                preparedStatement.execute();

                for (PrimerTube primerTube : pickList) {
                    long l = primerTube.store(connection);
                    setPrimerListIDinPrimerTube(l, connection);
                }

                return getObjectID();
            } else {

                PreparedStatement preparedStatement = connection.prepareStatement("UPDATE PickList SET EntryDate = ?,LocationID = ?,PrimerListStatusID = ? WHERE id = ? ");
                checkIfTimeStampNull(preparedStatement, 1, parseLocaleDatTimeinTimestamp(entryDate));
                preparedStatement.setLong(2, getDestination().getObjectID());
                isObjectNull(3, preparedStatement, primerListStatus);
                preparedStatement.setLong(4, getObjectID());
                preparedStatement.execute();

                for (PrimerTube primerTube : pickList) {
                    long l = primerTube.store(connection);
                    setPrimerListIDinPrimerTube(l, connection);
                }

                return getObjectID();
            }
        } catch (Exception e) {
            throw new StoreException("Error occurred while storing the PickList.", e);
        }
    }

    //******************************************************************************************************************
    //Get and Set Methods
    //******************************************************************************************************************

    public LocalDateTime getEntryDate() {
        return entryDate;
    }

    public Location getDestination() {
        return destination;
    }

    public void setDestination(Location destination) {
        this.destination = destination;
    }

    public List<PrimerTube> getPickList() {
        return pickList;
    }

    public void setPickList(List<PrimerTube> pickList) {
        this.pickList = pickList;
    }

    public PrimerListStatus getPrimerListStatus() {
        return primerListStatus;
    }

    public void setPrimerListStatus(PrimerListStatus primerListStatus) {
        this.primerListStatus = primerListStatus;
    }

    private void setPrimerListIDinPrimerTube(long tubeID, Connection connection) throws StoreException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE PrimerTube SET PickListID = ? WHERE id = ?");
            preparedStatement.setLong(1, getObjectID());
            preparedStatement.setLong(2, tubeID);
            preparedStatement.execute();
        } catch (Exception e) {

            throw new StoreException(e);
        }
    }
}
