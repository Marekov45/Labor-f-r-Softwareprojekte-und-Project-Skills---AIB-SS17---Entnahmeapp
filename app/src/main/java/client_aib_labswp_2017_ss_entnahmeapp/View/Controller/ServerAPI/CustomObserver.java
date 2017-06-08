package client_aib_labswp_2017_ss_entnahmeapp.View.Controller.ServerAPI;

/**
 * Created by User on 08.06.2017.
 */
public interface CustomObserver {

    /**
     *
     * @param o
     */
    public void onResponseSuccess(Object o);

    public void onResponseError();

    public void onResponseFailure();
}
