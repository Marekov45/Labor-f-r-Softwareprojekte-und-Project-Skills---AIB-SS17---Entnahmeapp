package client_aib_labswp_2017_ss_entnahmeapp.View.controller.serverAPI;

import client_aib_labswp_2017_ss_entnahmeapp.View.controller.enumResponseCode.ResponseCode;
import client_aib_labswp_2017_ss_entnahmeapp.View.model.model_List.PickList;
import client_aib_labswp_2017_ss_entnahmeapp.View.model.model_List.PrimerTube;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.List;

/**
 * Created by User on 08.06.2017.
 */
public class ListImpl {

    private final String BASE_URL = "http://192.168.2.108:8080/";

//    public static final Gson GSON = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssX").create();

    private Retrofit mRetrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
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
                    System.out.println(primerList.size());
                    cObserver.onResponseSuccess(primerList, ResponseCode.LIST);

                } else {
                    try {
                        System.out.println(response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    cObserver.onResponseError();
                }
            }

            @Override
            public void onFailure(Call<List<PickList>> call, Throwable t) {
                t.printStackTrace();
                cObserver.onResponseFailure();
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
                    System.out.println(primerListAllProc.size());
                    cObserver.onResponseSuccess(primerListAllProc, ResponseCode.COMPLETELIST);
                } else {
                    try {
                        System.out.println(response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    cObserver.onResponseError();
                }
            }

            @Override
            public void onFailure(Call<List<PickList>> call, Throwable t) {
                t.printStackTrace();
                cObserver.onResponseFailure();
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
                    System.out.println(gatheredPrimerListAll.size());
                    cObserver.onResponseSuccess(gatheredPrimerListAll, ResponseCode.COMPLETEGATHEREDLIST);
                } else {
                    try {
                        System.out.println(response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    cObserver.onResponseError();
                }
            }

            @Override
            public void onFailure(Call<List<PrimerTube>> call, Throwable t) {
                t.printStackTrace();
                cObserver.onResponseFailure();
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
                    System.out.println(gatheredPrimerList.size());
                    cObserver.onResponseSuccess(gatheredPrimerList, ResponseCode.GATHEREDLIST);
                } else {
                    try {
                        System.out.println(response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    cObserver.onResponseError();
                }
            }

            @Override
            public void onFailure(Call<List<PrimerTube>> call, Throwable t) {
                t.printStackTrace();
                cObserver.onResponseFailure();
            }
        });
    }



    public void requestLastSangerList(String name, String password){
        Call<List<PickList>> call= listAPI.getLastProcessedSanger(name, password);
        call.enqueue(new Callback<List<PickList>>() {
            @Override
            public void onResponse(Call<List<PickList>> call, Response<List<PickList>> response) {
                if(response.isSuccessful()){
                    List<PickList> primerList = response.body();
                    System.out.println(primerList.size());
                    cObserver.onResponseSuccess(primerList, ResponseCode.LASTSANGER);
                }else{
                    try {
                        System.out.println(response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    cObserver.onResponseError();
                }
            }

            @Override
            public void onFailure(Call<List<PickList>> call, Throwable t) {
                t.printStackTrace();
                cObserver.onResponseFailure();
            }
        });
    }

    public void setCObserver(CustomObserver customObserver) {
        this.cObserver = customObserver;
    }
}

