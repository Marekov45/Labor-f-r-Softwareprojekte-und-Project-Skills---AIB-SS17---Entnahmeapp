package client_aib_labswp_2017_ss_entnahmeapp.View.Controller.ServerAPI;

import client_aib_labswp_2017_ss_entnahmeapp.View.Controller.enumResponseCode.ResponseCode;
import client_aib_labswp_2017_ss_entnahmeapp.View.Model.PickList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.net.ssl.HttpsURLConnection;
import java.util.List;

/**
 * Created by User on 08.06.2017.
 */
public class ListImpl {

    private final String BASE_URL = "http://10.0.2.2:8080/";
    private Retrofit mRetrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
    private ListAPI listAPI = mRetrofit.create(ListAPI.class);
    private CustomObserver cObserver;

    public void requestList (String name, String password, String typeOfProcess){

        Call<List> call = listAPI.getPicklist(name, password, typeOfProcess);
        call.enqueue(new Callback<List>() {
            @Override
            public void onResponse(Call<List> call, Response<List> response) {
                if(response.code()== HttpsURLConnection.HTTP_OK){
                    List pimerList = response.body();
                    cObserver.onResponseSuccess(pimerList, ResponseCode.LIST);

                }else{
                    cObserver.onResponseError();
                }
            }

            @Override
            public void onFailure(Call<List> call, Throwable t) {
                cObserver.onResponseFailure();
            }
        });
    }
    public void setCObserver(CustomObserver customObserver) {
        this.cObserver = customObserver;
    }
}

