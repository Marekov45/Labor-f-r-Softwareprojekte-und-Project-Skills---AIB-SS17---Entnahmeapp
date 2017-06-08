package client_aib_labswp_2017_ss_entnahmeapp.View.Controller.ServerAPI;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by User on 06.06.2017.
 */
public interface LoginAPI {

    @GET("/authentication/{username}/{password}")
    Call<Boolean> getAuthentification(@Path("username") String username, @Path("password") String password);
}
