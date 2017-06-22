package client_aib_labswp_2017_ss_entnahmeapp.View.model.model_List;

/**
 * Represents a procedure that can be processed in a lab.
 * Created by User
 */
public class Procedure {

    private String shortCut;
    private String procedureName;
    private long objectID;

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

    public void setShortCut(String shortCut) {
        this.shortCut = shortCut;
    }

    public void setProcedureName(String procedureName) {
        this.procedureName = procedureName;
    }

    public void setObjectID(long objectID) {
        this.objectID = objectID;
    }
}
