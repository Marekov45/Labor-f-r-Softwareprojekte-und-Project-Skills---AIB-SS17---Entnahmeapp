package client_aib_labswp_2017_ss_entnahmeapp.View.Model.model_List;

/**
 * Created by SHauck on 12.05.2017.
 */
public class Location  {

    private String locationName;
    private Procedure[] procedures;
    private long objectID;

    public Location(String locationName, Procedure[] procedures, long objectID) {
        this.locationName = locationName;
        this.procedures = procedures;
        this.objectID = objectID;
    }

    public Location() {
    }

    public String getLocationName() {
        return locationName;
    }

    public Procedure[] getProcedures() {
        return procedures;
    }

    public long getObjectID() {
        return objectID;
    }
}
