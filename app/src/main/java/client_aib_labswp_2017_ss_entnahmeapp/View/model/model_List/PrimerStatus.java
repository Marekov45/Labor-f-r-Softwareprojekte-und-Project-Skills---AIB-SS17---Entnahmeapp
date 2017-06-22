package client_aib_labswp_2017_ss_entnahmeapp.View.model.model_List;

/**
 * Created by User on 17.06.2017.
 */
public class PrimerStatus {
    private String message;
    private int statusCode;

    public PrimerStatus(String message, int statusCode){
        this.message=message;
        this.statusCode= statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
