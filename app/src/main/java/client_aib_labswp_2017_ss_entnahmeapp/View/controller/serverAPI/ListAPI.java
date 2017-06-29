package client_aib_labswp_2017_ss_entnahmeapp.View.controller.serverAPI;

import client_aib_labswp_2017_ss_entnahmeapp.View.model.model_List.PickList;
import client_aib_labswp_2017_ss_entnahmeapp.View.model.model_List.PrimerTube;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.List;

/**
 * Interface used to define the List API.
 */
public interface ListAPI {

    /**
     * Performs the GET request for one of the three list types. Returns a List with {@link PickList} objects.
     * The response body holds a List with {@link PickList} objects.
     * The List contains {@link PickList} objects from the given type of Procedure. The Type of Procedure must either
     * be "S", "M" or "E" for Sanger, Manual or Extra. The List can be empty.
     *
     * @param username      is not allowed to be {@code null} and must not be an empty
     *                      String (<code>""</code>).
     * @param password      is not allowed to be {@code null} and must not be an empty
     *                      String (<code>""</code>).
     * @param typeOfProcess must be either "S","M" or "E".
     * @return call object which can be enqueued as request
     */
    @GET("/app/list/{typeOfProcess}/{username}/{password}")
    Call<List<PickList>> getPicklist(@Path("typeOfProcess") String typeOfProcess, @Path("username") String username, @Path("password") String password);

    /**
     * Performs the GET request for every list type at once. Returns a List with {@link PickList} objects.
     * The List contains {@link PickList} objects form all types of processes (Sanger, Manual, Extra).
     * The List might be empty.
     *
     * @param username is not allowed to be {@code null} and must not be an empty
     *                 String (<code>""</code>).
     * @param password is not allowed to be {@code null} and must not be an empty
     *                 String (<code>""</code>).
     * @return call object which can be enqueued as request
     */
    @GET("/app/list/{username}/{password}")
    Call<List<PickList>> getAllPicklists(@Path("username") String username, @Path("password") String password);

    /**
     * Performs the GET request for gathered Primertubes. Returns a List with all gathered {@link PrimerTube} objects.
     * The List might be empty.
     *
     * @param username is not allowed to be {@code null} and must not be an empty
     *                 String (<code>""</code>).
     * @param password is not allowed to be {@code null} and must not be an empty
     *                 String (<code>""</code>).
     * @return call object which can be enqueued as request
     */
    @GET("/app/gatheredPrimer/{username}/{password}")
    Call<List<PrimerTube>> getAllGatheredPrimerTubes(@Path("username") String username, @Path("password") String password);

    /**
     * Performs the GET request for gathered Primertubes. Returns a list with search results.
     * The gathered {@link PrimerTube} objects are searched by their {@code name}. The List might be empty.
     *
     * @param name     the name of the primer. It is not allowed to be {@code null} and must not be an empty
     *                 String (<code>""</code>).
     * @param username is not allowed to be {@code null} and must not be an empty
     *                 String (<code>""</code>).
     * @param password is not allowed to be {@code null} and must not be an empty
     *                 String (<code>""</code>).
     * @return call object which can be enqueued as request
     */
    @GET("/app/search/gatheredPrimer/{name}/{username}/{password}")
    Call<List<PrimerTube>> getGatheredPrimerTubes(@Path("name") String name, @Path("username") String username, @Path("password") String password);

    /**
     * Handles the GET request for the last processed list. Returns a list with the last processed(worked off)
     * picklists for locations that can process "Sanger". The List might be empty.
     *
     * @param username is not allowed to be {@code null} and must not be an empty
     *                 String (<code>""</code>).
     * @param password is not allowed to be {@code null} and must not be an empty
     *                 String (<code>""</code>).
     * @return call object which can be enqueued as request
     */
    @GET("/app/sanger/{username}/{password}")
    Call<List<PickList>> getLastProcessedSanger(@Path("username") String username, @Path("password") String password);
}
