package client_aib_labswp_2017_ss_entnahmeapp.View.Model.model_List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by lschl on 09.05.2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PickList {

    private Location destination;

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
}
