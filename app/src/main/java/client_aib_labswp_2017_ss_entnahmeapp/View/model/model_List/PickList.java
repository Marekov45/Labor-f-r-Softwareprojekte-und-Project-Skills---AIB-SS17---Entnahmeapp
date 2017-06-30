package client_aib_labswp_2017_ss_entnahmeapp.View.model.model_List;

import client_aib_labswp_2017_ss_entnahmeapp.View.model.parsedate.ParseDateResponse;

import java.util.List;

/**
 * The {@link PickList} contains every processed list of the day.
 */
public class PickList {

    private Location destination;
    private ParseDateResponse entryDate;
    private List<PrimerTube> pickList;
    private long objectID;

    /**
     * Initializes the {@link PickList}.
     *
     * @param destination must not be {@code null}.
     * @param pickList    must not be {@code null}. The list might be empty.
     * @param objectID    unique id. It must not be {@code null}.
     */
    public PickList(Location destination, List<PrimerTube> pickList, long objectID) {
        this.destination = destination;
        this.pickList = pickList;
        this.objectID = objectID;
    }

    public PickList() {
    }

    public Location getDestination() {
        return destination;
    }

    public List<PrimerTube> getPickList() {
        return pickList;
    }

    public long getObjectID() {
        return objectID;
    }

    /**
     * @param destination must not be {@code null}.
     */
    public void setDestination(Location destination) {
        this.destination = destination;
    }

    /**
     * @param pickList must not be {@code null}. The list might be empty.
     */
    public void setPickList(List<PrimerTube> pickList) {
        this.pickList = pickList;
    }

    /**
     * @param objectID unique id. It must not be {@code null}.
     */
    public void setObjectID(long objectID) {
        this.objectID = objectID;
    }

    public ParseDateResponse getEntryDate() {
        return entryDate;
    }

    /**
     * @param entryDate must not be in the future.
     */
    public void setEntryDate(ParseDateResponse entryDate) {
        this.entryDate = entryDate;
    }
}
