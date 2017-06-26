package client_aib_labswp_2017_ss_entnahmeapp.View.view.popup;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.*;
import client.aib_labswp_2017_ss_entnahmeapp.R;
import client_aib_labswp_2017_ss_entnahmeapp.View.controller.serverAPI.CustomObserver;
import client_aib_labswp_2017_ss_entnahmeapp.View.controller.serverAPI.ListImpl;
import client_aib_labswp_2017_ss_entnahmeapp.View.controller.serverAPI.PrimerImpl;
import client_aib_labswp_2017_ss_entnahmeapp.View.controller.enumResponseCode.ResponseCode;
import client_aib_labswp_2017_ss_entnahmeapp.View.model.User;
import client_aib_labswp_2017_ss_entnahmeapp.View.model.model_List.PrimerStatus;
import client_aib_labswp_2017_ss_entnahmeapp.View.model.model_List.PrimerTube;
import client_aib_labswp_2017_ss_entnahmeapp.View.view.adapter.ListAdapterGatheredPrimer;


/**
 * Created by Marvin on 21.06.2017.
 */
public class PopReturn extends AppCompatActivity implements CustomObserver {


    private Button btnDelete;
    private Button btnClose;
    private RadioGroup reasonforRemovalGroup;
//    private RadioButton radioEmpty;
    private ListImpl listImpl;
    private User uobj;
    private int positionGiven;
    private PrimerTube tube;
    private PrimerImpl primerImpl;
    private PrimerTube tubeToRemove;
    private EditText message;
    private ListView listView;
    private User userRemove;

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
        tubeToRemove = getIntent().getParcelableExtra("PRIMERTUBETOREMOVE");
        userRemove= getIntent().getParcelableExtra("USERREMOVE");
        final int position = positionGiven - 1;
        btnDelete.setEnabled(true);

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
                    case R.id.rbtnempty:
                        message.setHint("Grund optional");
                        btnDelete.setEnabled(true);
                        break;
                    case R.id.rbtnbroken:
                    case R.id.rbtnspoiled:
                    case R.id.rbtnnoinfo:
                        message.setText("");
                        message.setHint("Grund zwingend nötig");
                        btnDelete.setEnabled(false);
                        checkIfMessageEmpty();
                        break;
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (tube==null){
                    primerImpl.removePrimer(tubeToRemove.getObjectID(), userRemove.getUsername(), userRemove.getPassword(), createPrimerStatus());

                }else{
                    primerImpl.removePrimer(tube.getObjectID(), uobj.getUsername(), uobj.getPassword(), createPrimerStatus());
                }
                finish();
            }
        });


    }

    private void checkIfMessageEmpty() {
        message.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (s.toString().trim().length() == 0) {
                    btnDelete.setEnabled(false);
                } else {
                    btnDelete.setEnabled(true);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() == 0) {
                    btnDelete.setEnabled(false);
                } else {
                    btnDelete.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

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



