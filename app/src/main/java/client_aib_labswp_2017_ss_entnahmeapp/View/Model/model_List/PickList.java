package client_aib_labswp_2017_ss_entnahmeapp.View.Model.model_List;

import client_aib_labswp_2017_ss_entnahmeapp.View.Model.test.DemoResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;
import java.util.List;

/**
 *
 */
//@JsonIgnoreProperties(ignoreUnknown = true)
public class PickList {

    private Location destination;
    private DemoResponse entryDate;

    private List<PrimerTube> pickList;
    private long objectID;

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

    public void setDestination(Location destination) {
        this.destination = destination;
    }

    public void setPickList(List<PrimerTube> pickList) {
        this.pickList = pickList;
    }

    public void setObjectID(long objectID) {
        this.objectID = objectID;
    }

    public DemoResponse getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(DemoResponse entryDate) {
        this.entryDate = entryDate;
    }
}
