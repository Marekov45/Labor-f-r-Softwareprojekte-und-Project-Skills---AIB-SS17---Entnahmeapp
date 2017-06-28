package client_aib_labswp_2017_ss_entnahmeapp.View.view.popup;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
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
import client_aib_labswp_2017_ss_entnahmeapp.View.controller.serverAPI.CustomObserver;
import client_aib_labswp_2017_ss_entnahmeapp.View.controller.serverAPI.PrimerImpl;
import client_aib_labswp_2017_ss_entnahmeapp.View.controller.enumResponseCode.ResponseCode;
import client_aib_labswp_2017_ss_entnahmeapp.View.model.User;
import client_aib_labswp_2017_ss_entnahmeapp.View.model.model_List.PrimerStatus;
import client_aib_labswp_2017_ss_entnahmeapp.View.model.model_List.PrimerTube;

/**
 * Created by User on 11.06.2017.
 */
public class PopPicklist extends AppCompatActivity implements CustomObserver {

    private TextView primerName;
    private TextView shownName;
    private TextView primerID;
    private TextView shownID;
    private TextView primerLOT;
    private TextView shownLOT;
    private TextView primerManufacturer;
    private TextView shownManufacturer;
    private TextView primerNote;
    private EditText message;

    private Button submit;
    private Button btnGoBack;
    private RadioGroup reasonforNewPrimerGroup;
    private RadioButton radioEmpty;

    private User uobj;
    private int positionGiven;
    private PrimerTube tube;
    private PrimerImpl primerImpl;
    private PrimerTube newTube;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popupwindow);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .8), (int) (height * .62));

        primerImpl = new PrimerImpl();
        primerImpl = new PrimerImpl();
        primerImpl.setCObserver(this);

        primerName = (TextView) findViewById(R.id.tvPrimerName);
        shownName = (TextView) findViewById(R.id.tvNameActTube);
        primerID = (TextView) findViewById(R.id.tvPrimerTubeID);
        shownID = (TextView) findViewById(R.id.tvIDActTube);
        primerLOT = (TextView) findViewById(R.id.tvLOT);
        shownLOT = (TextView) findViewById(R.id.tvLOTActTube);
        primerManufacturer = (TextView) findViewById(R.id.tvManufacturerActTube);
        shownManufacturer = (TextView) findViewById(R.id.tvManufacturerActTube);
        primerNote = (TextView) findViewById(R.id.tvNote);
        reasonforNewPrimerGroup = (RadioGroup) findViewById(R.id.reasonNewPrimerGroup);

        message = (EditText) findViewById(R.id.editTextNote);
        submit = (Button) findViewById(R.id.btnSubmit);
        btnGoBack=(Button) findViewById(R.id.btnclose);

        uobj = getIntent().getParcelableExtra("USER");
        tube = getIntent().getParcelableExtra("TUBE");
        positionGiven = getIntent().getIntExtra("POSITION", 0);
        final int position = positionGiven - 1;


        shownName.setText(tube.getName());
        shownID.setText(tube.getPrimerTubeID());
        shownLOT.setText(tube.getLotNr());
        shownManufacturer.setText(tube.getManufacturer());
        submit.setEnabled(true);

        btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(newTube==null){
                    finish();
                }else{
                    final Intent intentNewTube = new Intent();
                    intentNewTube.putExtra("NEWTUBE", (Parcelable) newTube);
                    intentNewTube.putExtra("POSITION", positionGiven);
                    setResult(Activity.RESULT_OK, intentNewTube);
                    finish();
                }
            }
        });

        reasonforNewPrimerGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioempty:
                        message.setHint("Grund optional");
                        submit.setEnabled(true);
                        break;
                    case R.id.radiobroken:
                    case R.id.radionotfound:
                    case R.id.radionoinfo:
                        message.setText("");
                        message.setHint("Grund zwingend nötig");
                        submit.setEnabled(false);
                        checkIfMessageEmpty();
                        break;
                }
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                primerImpl.removeAndGetNewPrimer(tube.getObjectID(), uobj.getUsername(), uobj.getPassword(), createPrimerStatus());

            }
        });


    }


    private void checkIfMessageEmpty() {
        message.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (s.toString().trim().length() == 0) {
                    submit.setEnabled(false);
                } else {
                    submit.setEnabled(true);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() == 0) {
                    submit.setEnabled(false);
                } else {
                    submit.setEnabled(true);
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
            case REMOVEANDREPLACEPRIMER:
                receiveNewPrimer(o);
                break;
        }
    }

    private void receiveNewPrimer(Object o) {
        if (o==null){
            // setup the alert builder
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Kein Ersatzprimer verfügbar.");
            builder.setMessage("Die Liste kann nicht abgearbeitet werden. Bitte laden Sie eine neue Liste.");
            // add a button
            builder.setPositiveButton("OK", null);
            // create and show the alert dialog
            AlertDialog dialog = builder.create();
            dialog.show();
        }else{
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
            newTube = (PrimerTube) o;
        }
    }

    private int chooseReason() {

        int selectedID = reasonforNewPrimerGroup.getCheckedRadioButtonId();

        switch (selectedID) {
            case R.id.radioempty:
                return 1;
            case R.id.radiobroken:
                return 2;
            case R.id.radionotfound:
                return 5;
            case R.id.radionoinfo:
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


