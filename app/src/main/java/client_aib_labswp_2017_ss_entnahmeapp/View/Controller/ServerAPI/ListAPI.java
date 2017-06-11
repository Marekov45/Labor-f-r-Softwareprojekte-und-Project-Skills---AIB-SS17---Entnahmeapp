package client_aib_labswp_2017_ss_entnahmeapp.View.Controller.ServerAPI;

import client_aib_labswp_2017_ss_entnahmeapp.View.Model.model_List.PickList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.List;

/**
 * Created by User on 08.06.2017.
 */
public interface ListAPI {

    @GET("/app/list/{typeOfProcess}/{username}/{password}")
    Call<List<PickList>> getPicklist(@Path("typeOfProcess") String typeOfProcess , @Path("username") String username, @Path("password") String password);

    @GET("/app/list/{username}/{password}")
    Call<List<PickList>> getAllPicklists(@Path("username") String username, @Path("password") String password);

    @GET("/app/takePrimer/{id}/{username}/{password}")
    Call takePrimer(@Path("id") long id, @Path("usermane") String username, @Path("password") String password);
}
