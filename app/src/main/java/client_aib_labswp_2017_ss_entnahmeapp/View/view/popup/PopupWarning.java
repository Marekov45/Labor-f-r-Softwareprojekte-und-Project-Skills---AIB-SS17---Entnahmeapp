package client_aib_labswp_2017_ss_entnahmeapp.View.view.popup;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import client.aib_labswp_2017_ss_entnahmeapp.R;
import client_aib_labswp_2017_ss_entnahmeapp.View.controller.serverAPI.CustomObserver;
import client_aib_labswp_2017_ss_entnahmeapp.View.controller.serverAPI.ListImpl;
import client_aib_labswp_2017_ss_entnahmeapp.View.controller.serverAPI.PrimerImpl;
import client_aib_labswp_2017_ss_entnahmeapp.View.controller.enumResponseCode.ResponseCode;
import client_aib_labswp_2017_ss_entnahmeapp.View.model.User;
import client_aib_labswp_2017_ss_entnahmeapp.View.model.model_List.PrimerTube;

/**
 * Created by neuma on 19.06.2017.
 */
public class PopupWarning extends AppCompatActivity implements CustomObserver {

    private TextView removeWarning;
    private TextView removeMessage;

    private Button btnconfirmwarning;

    private User uobj;
    private int positionGiven;
    private PrimerTube tube;
    private PrimerImpl primerImpl;
    private ListImpl listImpl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popupwarning);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .8), (int) (height * .32));



        listImpl = new ListImpl();
        listImpl.setCObserver(this);

        uobj = getIntent().getParcelableExtra("USER");
        removeWarning = (TextView) findViewById(R.id.removeWarning);
        removeMessage = (TextView) findViewById(R.id.removeMessage);

        btnconfirmwarning = (Button) findViewById(R.id.btn_confirmwarning);


        btnconfirmwarning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }



    @Override
    public void onResponseSuccess(Object o, ResponseCode code) {
        switch (code) {
            case REMOVEPRIMER:
                removePrimer(o);
                break;
        }
    }


    private void removePrimer(Object o) {
        Toast.makeText(this, "Primer wurde entsorgt", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onResponseError() {
        Toast.makeText(this, "ResponseError", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponseFailure() {
        Toast.makeText(this, "Failure", Toast.LENGTH_SHORT).show();
    }
}
