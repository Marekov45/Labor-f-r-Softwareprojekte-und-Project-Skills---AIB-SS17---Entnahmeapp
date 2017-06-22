package client_aib_labswp_2017_ss_entnahmeapp.View.model.model_List;

/**
 *
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

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public void setProcedures(Procedure[] procedures) {
        this.procedures = procedures;
    }

    public void setObjectID(long objectID) {
        this.objectID = objectID;
    }
}
