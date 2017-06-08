package client_aib_labswp_2017_ss_entnahmeapp.View.Model.model_List;

/**
 * Represents a procedure that can be processed in a lab.
 * Created by User
 */
public class Procedure{

    private String shortCut;
    private String procedureName;

    public Procedure(String shortCut, String procedureName) {
        this.shortCut = shortCut;
        this.procedureName = procedureName;
    }

    public Procedure() {
    }

    public String getShortCut() {
        return shortCut;
    }

    public String getProcedureName() {
        return procedureName;
    }
}
