package client_aib_labswp_2017_ss_entnahmeapp.View.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import client.aib_labswp_2017_ss_entnahmeapp.R;
import client_aib_labswp_2017_ss_entnahmeapp.View.Controller.ServerAPI.CustomObserver;
import client_aib_labswp_2017_ss_entnahmeapp.View.Controller.enumResponseCode.ResponseCode;

public class LastProcessedListActivity extends AppCompatActivity implements CustomObserver{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_processed_list);
    }

    @Override
    public void onResponseSuccess(Object o, ResponseCode code) {

    }

    @Override
    public void onResponseError() {

    }

    @Override
    public void onResponseFailure() {

    }
}
