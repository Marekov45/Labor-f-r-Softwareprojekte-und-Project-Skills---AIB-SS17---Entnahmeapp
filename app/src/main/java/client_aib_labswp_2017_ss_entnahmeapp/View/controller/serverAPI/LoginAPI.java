package client_aib_labswp_2017_ss_entnahmeapp.View.controller.serverAPI;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Interface used to define the Login API.
 */
public interface LoginAPI {

    /**
     * Performs the GET Request for the user authentication. If the request is successful, the response body will contain
     * a boolean value if the {@link client_aib_labswp_2017_ss_entnahmeapp.View.model.User} is authorized to execute requests.
     *
     * @param username is not allowed to be {@code null} and must not be an empty
     *                 String (<code>""</code>).
     * @param password is not allowed to be {@code null} and must not be and empty
     *                 String (<code>""</code>).
     * @return call object which can be enqueued as request
     */
    @GET("/authentication/{username}/{password}")
    Call<Boolean> getAuthentification(@Path("username") String username, @Path("password") String password);
}
