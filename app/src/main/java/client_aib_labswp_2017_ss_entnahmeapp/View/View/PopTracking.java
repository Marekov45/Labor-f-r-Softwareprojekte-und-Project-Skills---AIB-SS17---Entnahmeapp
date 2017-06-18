package client_aib_labswp_2017_ss_entnahmeapp.View.View;

import android.app.Activity;
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
import client_aib_labswp_2017_ss_entnahmeapp.View.Controller.ServerAPI.CustomObserver;
import client_aib_labswp_2017_ss_entnahmeapp.View.Controller.ServerAPI.PrimerImpl;
import client_aib_labswp_2017_ss_entnahmeapp.View.Controller.enumResponseCode.ResponseCode;
import client_aib_labswp_2017_ss_entnahmeapp.View.Model.User;
import client_aib_labswp_2017_ss_entnahmeapp.View.Model.model_List.PrimerStatus;
import client_aib_labswp_2017_ss_entnahmeapp.View.Model.model_List.PrimerTube;


/**
 * Created by Marvin on 18.06.2017.
 */
public class PopTracking extends AppCompatActivity implements CustomObserver {

    private TextView actualLocation;
    private EditText newLocation;
    private Button setNewLocation;
    private TextView primerNote;
    private EditText message;
    private Button submit;
    private Button btnGoBack;
    private RadioGroup newStatusGroup;
    private User uobj;
    private int positionGiven;
    private PrimerTube tube;
    private PrimerImpl primerImpl;
    private PrimerTube newTube;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popupwindow_tracking);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .8), (int) (height * .69));

        primerImpl = new PrimerImpl();
        primerImpl = new PrimerImpl();
        primerImpl.setCObserver(this);

        primerNote = (TextView) findViewById(R.id.txtMessage);
        newStatusGroup = (RadioGroup) findViewById(R.id.statusGroup);
        actualLocation = (TextView) findViewById(R.id.txtLocation);
        message = (EditText) findViewById(R.id.editTxtMessage);
        newLocation = (EditText) findViewById(R.id.txtNewLocationTube);
        setNewLocation = (Button) findViewById(R.id.btnChangeLocation);
        submit = (Button) findViewById(R.id.btnNewPrimer);
        btnGoBack = (Button) findViewById(R.id.btnOK);

        uobj = getIntent().getParcelableExtra("USER");
        tube = getIntent().getParcelableExtra("TUBE");
        positionGiven = getIntent().getIntExtra("POSITION", 0);
        final int position = positionGiven - 1;
        actualLocation.setText(tube.getCurrentLocation());
        submit.setEnabled(false);
        setNewLocation.setEnabled(false);
        checkIfNewLocationEmpty();

        btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (newTube == null) {
                    finish();
                } else {
                    final Intent intentNewTube = new Intent();
                    intentNewTube.putExtra("NEWTUBE", (Parcelable) newTube);
                    intentNewTube.putExtra("POSITION", positionGiven);
                    setResult(Activity.RESULT_OK, intentNewTube);
                    finish();
                }
            }
        });

        newStatusGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
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

        setNewLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                primerImpl.sendLocation(tube.getObjectID(),uobj.getUsername(),uobj.getPassword(),newLocation.getText().toString());
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

    private void checkIfNewLocationEmpty() {
        newLocation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (s.toString().trim().length() == 0) {
                    setNewLocation.setEnabled(false);
                } else {
                    setNewLocation.setEnabled(true);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() == 0) {
                    setNewLocation.setEnabled(false);
                } else {
                    setNewLocation.setEnabled(true);
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
            case SENDLOCATION:
                sendNewLocation(o);
        }
    }

    private void receiveNewPrimer(Object o) {
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
        newTube = (PrimerTube) o;
    }
    private void sendNewLocation(Object o) {
        Toast.makeText(this, "New Location has been set", Toast.LENGTH_SHORT).show();
    }

    private int chooseReason() {

        int selectedID = newStatusGroup.getCheckedRadioButtonId();

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
