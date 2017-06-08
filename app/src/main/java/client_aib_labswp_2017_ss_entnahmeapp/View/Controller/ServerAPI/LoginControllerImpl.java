package client_aib_labswp_2017_ss_entnahmeapp.View.Controller.ServerAPI;

import client_aib_labswp_2017_ss_entnahmeapp.View.Controller.ServerAPI.LoginAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by User on 06.06.2017.
 */
public class LoginControllerImpl {

    private final String BASE_URL = "http://10.0.2.2:8080/";
    private Retrofit mRetrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
    private LoginAPI loginAPI = mRetrofit.create(LoginAPI.class);
    private CustomObserver cObserver;

    public void requestLogin(String name, String password) {
        Call<Boolean> call = loginAPI.getAuthentification(name, password);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    Boolean status = response.body();
                    cObserver.onResponseSuccess(status);
                } else {
                    cObserver.onResponseError();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                t.printStackTrace();
                cObserver.onResponseFailure();
            }
        });
    }

    public void setCObserver(CustomObserver customObserver) {
        this.cObserver = customObserver;
    }

    public boolean login(String name, String password) {


        //Call for rest

        //1
        //2
        //3
        //return
        if (name.equals("lea") && password.equals("123")) {
            return true;
        } else {
            return false;
        }
    }
}
