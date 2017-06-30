package client_aib_labswp_2017_ss_entnahmeapp.View.model.model_List;

/**
 * Represents a {@link Procedure} that can be processed in a lab.
 */
public class Procedure {

    private String shortCut;
    private String procedureName;
    private long objectID;

    /**
     * Initializes the {@link Procedure}.
     *
     * @param procedureName is not allowed to be {@code null} and must not be an empty
     *                      String (<code>""</code>).
     * @param shortCut      is not allowed to be {@code null} and must not be an empty
     *                      String (<code>""</code>).
     * @param objectID      unique id. it must not be {@code null}.
     */
    public Procedure(String shortCut, String procedureName, long objectID) {
        this.shortCut = shortCut;
        this.procedureName = procedureName;
        this.objectID = objectID;
    }

    public Procedure() {
    }

    public String getShortCut() {
        return shortCut;
    }

    public String getProcedureName() {
        return procedureName;
    }

    public long getObjectID() {
        return objectID;
    }

    /**
     * @param shortCut is not allowed to be {@code null} and must not be an empty
     *                 String (<code>""</code>).
     */
    public void setShortCut(String shortCut) {
        this.shortCut = shortCut;
    }

    /**
     * @param procedureName is not allowed to be {@code null} and must not be an empty
     *                      String (<code>""</code>).
     */
    public void setProcedureName(String procedureName) {
        this.procedureName = procedureName;
    }

    /**
     * @param objectID must not be {@code null}.
     */
    public void setObjectID(long objectID) {
        this.objectID = objectID;
    }
}
