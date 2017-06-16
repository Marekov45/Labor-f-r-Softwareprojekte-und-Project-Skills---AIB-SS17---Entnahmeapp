package client_aib_labswp_2017_ss_entnahmeapp.View.Controller.ServerAPI;

import client_aib_labswp_2017_ss_entnahmeapp.View.Model.model_List.Location;
import client_aib_labswp_2017_ss_entnahmeapp.View.Model.model_List.PrimerTube;
import retrofit2.Call;
import retrofit2.http.Body;
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
    Call<Void> sendLocation(@Path("id") long id, @Path("username") String username, @Path("password") String password, @Body String location);

    @PUT("/app/remove/replace/{id}/{username}/{password}")
    Call<PrimerTube> removeAngGetNewPrimer(@Path("id") long id, @Path("username") String username, @Path("password") String password, @Body String message, @Body int status);

    @PUT("/app/remove/{id}/{username}/{password}")
    Call<Void> removePrimer(@Path("id") long id, @Path("username") String username, @Path("password") String password, @Body String message, @Body int status);

    @GET("/app/returnPrimer/{id}/{username}/{password}")
    Call<Void> returnPrimer(@Path("id") long id, @Path("username") String username, @Path("password") String password);


}
