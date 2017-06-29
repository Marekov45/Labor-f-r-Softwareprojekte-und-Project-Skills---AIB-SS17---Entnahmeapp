package client_aib_labswp_2017_ss_entnahmeapp.View.controller.serverAPI;

import client_aib_labswp_2017_ss_entnahmeapp.View.controller.enumResponseCode.ResponseCode;

/**
 * Interface used to define a custom observer.
 */
public interface CustomObserver {

    /**
     * Called if the REST request has been successful
     *
     * @param o    the content of the response body for the corresponding REST request. It must not be {@code null}.
     * @param code it must not be {@code null}.
     */
    public void onResponseSuccess(Object o, ResponseCode code);

    /**
     * Called if the http status has not been ok.
     *
     * @param o    the content of the response body for the corresponding REST request. It must not be {@code null}.
     * @param code it must not be {@code null}.
     */
    public void onResponseError(Object o, ResponseCode code);

    public void onResponseFailure(ResponseCode code);
}
