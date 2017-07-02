package client_aib_labswp_2017_ss_entnahmeapp.View.controller.serverAPI;

import client_aib_labswp_2017_ss_entnahmeapp.View.controller.enumResponseCode.ResponseCode;
import client_aib_labswp_2017_ss_entnahmeapp.View.model.model_List.PickList;
import client_aib_labswp_2017_ss_entnahmeapp.View.model.model_List.PrimerTube;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import java.io.IOException;
import java.util.List;

/**
 * {@link ListImpl} implements the REST requests regarding all list types.
 */
public class ListImpl {

    private final String BASE_URL = "http://10.0.2.2:8080/";

    public static final Gson GSON = new GsonBuilder().setLenient().create();
    // Initializes Retrofit REST API
    private Retrofit mRetrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(GSON))
            .build();
    private ListAPI listAPI = mRetrofit.create(ListAPI.class);
    private CustomObserver cObserver;


    public void requestList(String name, final String password, String typeOfProcess) {
        Call<List<PickList>> call = listAPI.getPicklist(typeOfProcess, name, password);
        call.enqueue(new Callback<List<PickList>>() {
            @Override
            public void onResponse(Call<List<PickList>> call, Response<List<PickList>> response) {
                if (response.isSuccessful()) {
                    List<PickList> primerList = response.body();
                    cObserver.onResponseSuccess(primerList, ResponseCode.LIST);

                } else {
                    try {
                        System.out.println(response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    cObserver.onResponseError(null, ResponseCode.LIST);
                }
            }

            @Override
            public void onFailure(Call<List<PickList>> call, Throwable t) {
                t.printStackTrace();
                cObserver.onResponseFailure(ResponseCode.LIST);
            }
        });
    }


    public void requestAllLists(String name, String password) {
        Call<List<PickList>> call = listAPI.getAllPicklists(name, password);
        call.enqueue(new Callback<List<PickList>>() {
            @Override
            public void onResponse(Call<List<PickList>> call, Response<List<PickList>> response) {
                if (response.isSuccessful()) {
                    List<PickList> primerListAllProc = response.body();
                    cObserver.onResponseSuccess(primerListAllProc, ResponseCode.COMPLETELIST);
                } else {
                    try {
                        System.out.println(response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    cObserver.onResponseError(null, ResponseCode.COMPLETELIST);
                }
            }

            @Override
            public void onFailure(Call<List<PickList>> call, Throwable t) {
                t.printStackTrace();
                cObserver.onResponseFailure(ResponseCode.COMPLETELIST);
            }
        });

    }

    public void requestAllGatheredPrimers(String name, String password) {
        Call<List<PrimerTube>> call = listAPI.getAllGatheredPrimerTubes(name, password);
        call.enqueue(new Callback<List<PrimerTube>>() {
            @Override
            public void onResponse(Call<List<PrimerTube>> call, Response<List<PrimerTube>> response) {
                if (response.isSuccessful()) {
                    List<PrimerTube> gatheredPrimerListAll = response.body();
                    cObserver.onResponseSuccess(gatheredPrimerListAll, ResponseCode.COMPLETEGATHEREDLIST);
                } else {
                    try {
                        System.out.println(response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    cObserver.onResponseError(null, ResponseCode.COMPLETEGATHEREDLIST);
                }
            }

            @Override
            public void onFailure(Call<List<PrimerTube>> call, Throwable t) {
                t.printStackTrace();
                cObserver.onResponseFailure(ResponseCode.COMPLETEGATHEREDLIST);
            }
        });
    }

    public void requestGatheredPrimers(String name, String username, String password) {
        Call<List<PrimerTube>> call = listAPI.getGatheredPrimerTubes(name, username, password);
        call.enqueue(new Callback<List<PrimerTube>>() {
            @Override
            public void onResponse(Call<List<PrimerTube>> call, Response<List<PrimerTube>> response) {
                if (response.isSuccessful()) {
                    List<PrimerTube> gatheredPrimerList = response.body();
                    cObserver.onResponseSuccess(gatheredPrimerList, ResponseCode.GATHEREDLIST);
                } else {
                    try {
                        System.out.println(response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    cObserver.onResponseError(null, ResponseCode.GATHEREDLIST);
                }
            }

            @Override
            public void onFailure(Call<List<PrimerTube>> call, Throwable t) {
                t.printStackTrace();
                cObserver.onResponseFailure(ResponseCode.GATHEREDLIST);
            }
        });
    }

    public void requestLastSangerList(String name, String password) {
        Call<List<PickList>> call = listAPI.getLastProcessedSanger(name, password);
        call.enqueue(new Callback<List<PickList>>() {
            @Override
            public void onResponse(Call<List<PickList>> call, Response<List<PickList>> response) {
                if (response.isSuccessful()) {
                    List<PickList> primerList = response.body();
                    cObserver.onResponseSuccess(primerList, ResponseCode.LASTSANGER);
                } else {
                    try {
                        System.out.println(response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    cObserver.onResponseError(null, ResponseCode.LASTSANGER);
                }
            }

            @Override
            public void onFailure(Call<List<PickList>> call, Throwable t) {
                t.printStackTrace();
                cObserver.onResponseFailure(ResponseCode.LASTSANGER);
            }
        });
    }

    /**
     * @param customObserver is not allowed to be {@code null}.
     */
    public void setCObserver(CustomObserver customObserver) {
        this.cObserver = customObserver;
    }
}

