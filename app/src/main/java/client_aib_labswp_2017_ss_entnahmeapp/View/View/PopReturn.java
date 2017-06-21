package client_aib_labswp_2017_ss_entnahmeapp.View.View;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.*;
import client.aib_labswp_2017_ss_entnahmeapp.R;
import client_aib_labswp_2017_ss_entnahmeapp.View.Controller.ServerAPI.CustomObserver;
import client_aib_labswp_2017_ss_entnahmeapp.View.Controller.ServerAPI.ListImpl;
import client_aib_labswp_2017_ss_entnahmeapp.View.Controller.ServerAPI.PrimerImpl;
import client_aib_labswp_2017_ss_entnahmeapp.View.Controller.enumResponseCode.ResponseCode;
import client_aib_labswp_2017_ss_entnahmeapp.View.Model.User;
import client_aib_labswp_2017_ss_entnahmeapp.View.Model.model_List.PrimerStatus;
import client_aib_labswp_2017_ss_entnahmeapp.View.Model.model_List.PrimerTube;

import java.util.List;


/**
 * Created by Marvin on 21.06.2017.
 */
public class PopReturn extends AppCompatActivity implements CustomObserver {


    private Button btnDelete;
    private Button btnClose;
    private RadioGroup reasonforRemovalGroup;
    private RadioButton radioEmpty;
    private ListImpl listImpl;
    private User uobj;
    private int positionGiven;
    private PrimerTube tube;
    private PrimerImpl primerImpl;
    private PrimerTube newTube;
    private EditText message;
    private ListView listView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_return);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .8), (int) (height * .32));


        primerImpl = new PrimerImpl();
        primerImpl.setCObserver(this);
        listImpl = new ListImpl();
        listImpl.setCObserver(this);
        listView = (ListView) findViewById(R.id.listvGatheredPrimer);
        message = (EditText) findViewById(R.id.etxtMessage);


        reasonforRemovalGroup = (RadioGroup) findViewById(R.id.reasonRemovalGroup);


        btnDelete = (Button) findViewById(R.id.btnRemove);
        btnClose = (Button) findViewById(R.id.bttnclose);

        uobj = getIntent().getParcelableExtra("USER");
        tube = getIntent().getParcelableExtra("TUBE");
        positionGiven = getIntent().getIntExtra("POSITION", 0);
        final int position = positionGiven - 1;
        btnDelete.setEnabled(false);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        reasonforRemovalGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbtnNoReason:
                        btnDelete.setEnabled(false);
                        break;
                    case R.id.rbtnempty:
                    case R.id.rbtnbroken:
                    case R.id.rbtnspoiled:
                    case R.id.rbtnnoinfo:
                        btnDelete.setEnabled(true);
                        break;
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                primerImpl.removePrimer(tube.getObjectID(), uobj.getUsername(), uobj.getPassword(), createPrimerStatus());
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
        Toast.makeText(this, "Primer has been removed", Toast.LENGTH_SHORT).show();
    }

    private int chooseReason() {

        int selectedID = reasonforRemovalGroup.getCheckedRadioButtonId();

        switch (selectedID) {
            case R.id.rbtnempty:
                return 1;
            case R.id.rbtnbroken:
                return 2;
            case R.id.rbtnspoiled:
                return 4;
            case R.id.rbtnnoinfo:
                return 199;
        }
        return 0;
    }


    private PrimerStatus createPrimerStatus() {
        PrimerStatus status = new PrimerStatus("", 0);
        if (message.getText().toString().matches("")) {
            status.setMessage("");
            status.setStatusCode(chooseReason());
        } else {
            status.setMessage(message.getText().toString());
            status.setStatusCode(chooseReason());
        }
        return status;
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



