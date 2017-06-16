package client_aib_labswp_2017_ss_entnahmeapp.View.Controller.ServerAPI;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by User on 16.06.2017.
 */
public interface PrimerAPI {

    @GET("/app/takePrimer/{id}/{username}/{password}")
    Call<Void> takePrimer(@Path("id") long id, @Path("username") String username, @Path("password") String password);

    @PUT("/app/location/{id}/{username}/{password}")
    Call<Void> setLocation(@Path("id") long id, @Path("username") String username, @Path("password") String password);



}
