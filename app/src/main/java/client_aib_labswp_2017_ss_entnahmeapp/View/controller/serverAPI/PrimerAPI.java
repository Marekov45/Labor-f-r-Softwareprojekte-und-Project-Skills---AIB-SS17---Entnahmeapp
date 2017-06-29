package client_aib_labswp_2017_ss_entnahmeapp.View.controller.serverAPI;

import client_aib_labswp_2017_ss_entnahmeapp.View.model.model_List.PrimerStatus;
import client_aib_labswp_2017_ss_entnahmeapp.View.model.model_List.PrimerTube;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Interface used to define the List API.
 */
public interface PrimerAPI {

    /**
     * Performs the GET Request for the withdrawal of a primer. Indicates that the {@link PrimerTube} is taken.
     * The location of the primer will be set to the given location.
     * in the picklist.
     *
     * @param id       the unique id from the picklist entry. It must be greater than zero <code>0</code>.
     * @param username is not allowed to be {@code null} and must not be an empty
     *                 String (<code>""</code>).
     * @param password is not allowed to be {@code null} and must not be an empty
     *                 String (<code>""</code>).
     * @return call object which can be enqueued as request
     */
    @GET("/app/takePrimer/{id}/{username}/{password}")
    Call<Void> takePrimer(@Path("id") long id, @Path("username") String username, @Path("password") String password);

    /**
     * Performs the PUT Request for a new location. Sets a new location for the corresponding primer.
     *
     * @param id       must be greater than zero <code>0</code>.
     * @param username is not allowed to be {@code null} and must not be an empty
     *                 String (<code>""</code>).
     * @param password is not allowed to be {@code null} and must not be an empty
     *                 String (<code>""</code>).
     * @param location it must not be {@code null}.
     * @return call object which can be enqueued as request
     */
    @PUT("/app/location/{id}/{username}/{password}")
    Call<Void> sendLocation(@Path("id") long id, @Path("username") String username, @Path("password") String password, @Body String location);

    /**
     * Performs the PUT Request for the replacement of a primer. Confirms that a {@link PrimerTube} will be removed
     * from the storage and a replacement is requested.
     *
     * @param id           must be greater than zero <code>0</code>.
     * @param username     is not allowed to be {@code null} and must not be an empty
     *                     String (<code>""</code>).
     * @param password     is not allowed to be {@code null} and must not be an empty
     *                     String (<code>""</code>).
     * @param primerstatus must not be {@code null}. The {@link PrimerStatus#getMessage()} can be empty, if the {@link
     *                     PrimerStatus#getStatusCode()} equals <code>1</code>.
     * @return call object which can be enqueued as request
     */
    @PUT("/app/remove/replace/{id}/{username}/{password}")
    Call<PrimerTube> removeAndGetNewPrimer(@Path("id") long id, @Path("username") String username, @Path("password") String password, @Body PrimerStatus primerstatus);

    /**
     * Performs the PUT Request for the removal of a primer. Confirms that a {@link PrimerTube} will be removed from the storage.
     *
     * @param id           must be greater than zero <code>0</code>.
     * @param username     is not allowed to be {@code null} and must not be an empty
     *                     String (<code>""</code>).
     * @param password     is not allowed to be {@code null} and must not be an empty
     *                     String (<code>""</code>).
     * @param primerstatus must not be {@code null}. The {@link PrimerStatus#getMessage()} can be empty, if the {@link
     *                     PrimerStatus#getStatusCode()} equals <code>1</code>.
     * @return call object which can be enqueued as request
     */
    @PUT("/app/remove/{id}/{username}/{password}")
    Call<Void> removePrimer(@Path("id") long id, @Path("username") String username, @Path("password") String password, @Body PrimerStatus primerstatus);

    /**
     * Performs the GET Request for the return of a primer. Indicates that the {@link PrimerTube} is returned to the Storage.
     * The primer is available for orders again.
     *
     * @param id       must be greater than zero <code>0</code>.
     * @param username is not allowed to be {@code null} and must not be an empty
     *                 String (<code>""</code>).
     * @param password is not allowed to be {@code null} and must not be an empty
     *                 String (<code>""</code>).
     * @return call object which can be enqueued as request
     */
    @GET("/app/returnPrimer/{id}/{username}/{password}")
    Call<Void> returnPrimer(@Path("id") long id, @Path("username") String username, @Path("password") String password);


}
