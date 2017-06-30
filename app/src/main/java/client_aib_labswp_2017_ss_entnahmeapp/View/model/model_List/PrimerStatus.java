package client_aib_labswp_2017_ss_entnahmeapp.View.model.model_List;

/**
 * {@link PrimerStatus } represents the status of a primer.
 */
public class PrimerStatus {
    private String message;
    private int statusCode;

    /**
     * Initializes the {@link PrimerStatus}.
     *
     * @param message    can be an empty String (<code>""</code>) if the {@link PrimerTube} is empty.
     * @param statusCode must be a valid status code.
     */
    public PrimerStatus(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    /**
     * @param message can be an empty String (<code>""</code>) if the {@link PrimerTube} is empty.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    /**
     * @param statusCode must be a valid status code.
     */
    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
