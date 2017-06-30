package client_aib_labswp_2017_ss_entnahmeapp.View.model.model_List;

/**
 * The {@link Location} keeps every possible location of the primer.
 */
public class Location {

    private String locationName;
    private Procedure[] procedures;
    private long objectID;

    /**
     * Initializes the {@link Location}.
     *
     * @param locationName is not allowed to be {@code null} and must not be an empty
     *                     String (<code>""</code>).
     * @param procedures   procedures that can be processed in a lab.
     * @param objectID     unique id. It must not be {@code null}.
     */
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

    /**
     * @param locationName is not allowed to be {@code null} and must not be an empty
     *                     String (<code>""</code>).
     */
    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    /**
     * @param procedures procedures that can be processed in a lab.
     */
    public void setProcedures(Procedure[] procedures) {
        this.procedures = procedures;
    }

    /**
     * @param objectID unique id. It must not be {@code null}.
     */
    public void setObjectID(long objectID) {
        this.objectID = objectID;
    }
}
