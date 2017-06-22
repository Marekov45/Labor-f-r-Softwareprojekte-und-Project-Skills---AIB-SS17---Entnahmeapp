package client_aib_labswp_2017_ss_entnahmeapp.View.controller.serverAPI;

import client_aib_labswp_2017_ss_entnahmeapp.View.controller.enumResponseCode.ResponseCode;
import client_aib_labswp_2017_ss_entnahmeapp.View.model.model_List.PrimerStatus;
import client_aib_labswp_2017_ss_entnahmeapp.View.model.model_List.PrimerTube;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

/**
 * Created by User on 16.06.2017.
 */
public class PrimerImpl {
    private final String BASE_URL = "http://192.168.2.108:8080/";

//    public static final Gson GSON = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssX").create();

    private Retrofit mRetrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private PrimerAPI primerAPI = mRetrofit.create(PrimerAPI.class);
    private CustomObserver cObserver;

    public void takePrimer(long id, String name, String password) {
        Call<Void> call = primerAPI.takePrimer(id, name, password);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    cObserver.onResponseSuccess(null, ResponseCode.TAKEPRIMER);
                } else {
                    try {
                        System.out.println(response.code());
                        System.out.println(response.errorBody().string());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    cObserver.onResponseError();
                }
            }


            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                t.printStackTrace();
                cObserver.onResponseFailure();
            }
        });
    }

    public void returnPrimer(long id, String name, String password) {
        Call<Void> call = primerAPI.returnPrimer(id, name, password);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    cObserver.onResponseSuccess(null, ResponseCode.RETURNPRIMER);
                } else {
                    try {
                        System.out.println(response.code());
                        System.out.println(response.errorBody().string());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    cObserver.onResponseError();
                }
            }


            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                t.printStackTrace();
                cObserver.onResponseFailure();
            }
        });
    }

    public void sendLocation(long id, String name, String password, String location) {
        Call<Void> call = primerAPI.sendLocation(id,name,password,location);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    cObserver.onResponseSuccess(null, ResponseCode.SENDLOCATION);
                }
                else {
                    try {
                        System.out.println(response.code());
                        System.out.println(response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    cObserver.onResponseError();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                t.printStackTrace();
                cObserver.onResponseFailure();
            }
        });
    }
    public void removeAndGetNewPrimer(long id, String name, String password, PrimerStatus primerStatus) {
        Call<PrimerTube> call = primerAPI.removeAndGetNewPrimer(id, name, password, primerStatus);
        call.enqueue(new Callback<PrimerTube>() {
            @Override
            public void onResponse(Call<PrimerTube> call, Response<PrimerTube> response) {
                if(response.isSuccessful()){
                    PrimerTube primerTube = response.body();
                    cObserver.onResponseSuccess(primerTube, ResponseCode.REMOVEANDREPLACEPRIMER);
                }else{
                    try {
                        System.out.println(response.code());
                        System.out.println(response.errorBody().string());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    cObserver.onResponseError();
                }
            }

            @Override
            public void onFailure(Call<PrimerTube> call, Throwable t) {
                t.printStackTrace();
                cObserver.onResponseFailure();
            }
        });
    }

    public void removePrimer(long id, String name, String password, PrimerStatus primerStatus) {
        Call<Void> call = primerAPI.removePrimer(id, name, password, primerStatus);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    cObserver.onResponseSuccess(null, ResponseCode.REMOVEPRIMER);
                }else{
                    try {
                        System.out.println(response.code());
                        System.out.println(response.errorBody().string());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    cObserver.onResponseError();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                t.printStackTrace();
                cObserver.onResponseFailure();
            }
        });
    }



    public void setCObserver(CustomObserver customObserver) {
        this.cObserver = customObserver;
    }

}
