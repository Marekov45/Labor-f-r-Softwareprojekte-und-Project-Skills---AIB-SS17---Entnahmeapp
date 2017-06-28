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
import client_aib_labswp_2017_ss_entnahmeapp.View.model.NewLocation;
import client_aib_labswp_2017_ss_entnahmeapp.View.model.User;
import client_aib_labswp_2017_ss_entnahmeapp.View.model.model_List.PrimerStatus;
import client_aib_labswp_2017_ss_entnahmeapp.View.model.model_List.PrimerTube;


/**
 * {@link PopTracking} displays the popupwindow of a primer after it has been clicked in the list.
 * It supports the replacement of primers and the change of their current location.
 * Created by Marvin on 18.06.2017.
 */
public class PopTracking extends AppCompatActivity implements CustomObserver {

    private TextView actualLocation;
    private Button setNewLocation;
    private EditText message;
    private Button submit;
    private Button btnGoBack;
    private RadioGroup newStatusGroup;
    private Spinner spinnerLocation;

    private User uobj;
    private int positionGiven;
    private PrimerTube tube;
    private PrimerImpl primerImpl;
    private PrimerTube newTube;
    String location;

    /**
     * Initializes the activity.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously
     *                           being shut down then this Bundle contains the data it most recently supplied.
     *                           This value may be {@code null}.
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popupwindow_tracking);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        //Set the size of the popupwindow
        getWindow().setLayout((int) (width * .8), (int) (height * .66));

        primerImpl = new PrimerImpl();
        primerImpl.setCObserver(this);

        newStatusGroup = (RadioGroup) findViewById(R.id.statusGroup);
        actualLocation = (TextView) findViewById(R.id.txtLocation);
        message = (EditText) findViewById(R.id.editTxtMessage);
        spinnerLocation= (Spinner) findViewById(R.id.spinnerGuiWorkspace);
        spinnerLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (spinnerLocation.getSelectedItemPosition()) {
                    case 0:
                        location = "";
                        setNewLocation.setEnabled(false);
                        break;
                    case 1:
                        location = "Robot1";
                        setNewLocation.setEnabled(true);
                        break;
                    case 2:
                        location = "Robot2";
                        setNewLocation.setEnabled(true);
                        break;
                    case 3:
                        location = "Workspace1";
                        setNewLocation.setEnabled(true);
                        break;
                    case 4:
                        location = "Workspace2";
                        setNewLocation.setEnabled(true);
                        break;
                    case 5:
                        location = "Workspace3";
                        setNewLocation.setEnabled(true);
                        break;
                    case 6:
                        location = "Workspace4";
                        setNewLocation.setEnabled(true);
                        break;
                    case 7:
                        location = "Workspace5";
                        setNewLocation.setEnabled(true);
                        break;
                    case 8:
                        location = "Workspace6";
                        setNewLocation.setEnabled(true);
                        break;
                    case 9:
                        location = "Workspace7";
                        setNewLocation.setEnabled(true);
                        break;
                    case 10:
                        location = "Workspace8";
                        setNewLocation.setEnabled(true);
                        break;
                    case 11:
                        location = "Workspace9";
                        setNewLocation.setEnabled(true);
                        break;
                    case 12:
                        location = "Workspace10";
                        setNewLocation.setEnabled(true);
                        break;
                    case 13:
                        location = "Workspace11";
                        setNewLocation.setEnabled(true);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                setNewLocation.setEnabled(false);

            }
        });
        setNewLocation = (Button) findViewById(R.id.btnChangeLocation);
        submit = (Button) findViewById(R.id.btnNewPrimer);
        btnGoBack = (Button) findViewById(R.id.btnOK);
        uobj = getIntent().getParcelableExtra(getString(R.string.intentUser));
        tube = getIntent().getParcelableExtra(getString(R.string.intentTube));
        positionGiven = getIntent().getIntExtra(getString(R.string.intentPosition), 0);
        final int position = positionGiven - 1;
        actualLocation.setText(tube.getCurrentLocation());
        submit.setEnabled(true);
        setNewLocation.setEnabled(false);


        btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewLocation locationObj = new NewLocation(location);
                //closes the popupwindow if nothing has been changed
                if (newTube == null && locationObj.getNewLocation().toString().equals("")) {
                    finish();
                } else {
                    //only replaces the primer
                    if (newTube != null && locationObj.getNewLocation().toString().equals("")) {
                        final Intent intentNewTube = new Intent();
                        intentNewTube.putExtra(getString(R.string.intentNewTube), (Parcelable) newTube);
                        intentNewTube.putExtra(getString(R.string.intentPosition), positionGiven);
                        setResult(Activity.RESULT_OK, intentNewTube);
                        finish();
                        // only changes the current location
                    } else if (newTube == null && !locationObj.getNewLocation().toString().equals("")) {
                        final Intent intentNewPosition = new Intent();
//                        locationObj = new NewLocation(newLocation.getText().toString());
                        intentNewPosition.putExtra(getString(R.string.intentNewLocation), (Parcelable) locationObj);
                        intentNewPosition.putExtra(getString(R.string.intentActualTube), (Parcelable) tube);
                        intentNewPosition.putExtra(getString(R.string.intentPosition), positionGiven);
                        setResult(Activity.RESULT_OK, intentNewPosition);
                        finish();
                    } else if (newTube != null && !locationObj.getNewLocation().toString().equals("")) {
                        final Intent intentNewTube = new Intent();
                        intentNewTube.putExtra(getString(R.string.intentNewTube), (Parcelable) newTube);
                        intentNewTube.putExtra(getString(R.string.intentPosition), positionGiven);
                        setResult(Activity.RESULT_OK, intentNewTube);
                        finish();
                    }

                }
            }
        });
        //enables or disables submit button depending on the radiobutton that is checked
        newStatusGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioempty:
                        message.setHint(getString(R.string.replacementHintOptional));
                        submit.setEnabled(true);
                        break;
                    case R.id.radiobroken:
                    case R.id.radionotfound:
                    case R.id.radionoinfo:
                        message.setText("");
                        message.setHint(getString(R.string.replacementHintRequired));
                        submit.setEnabled(false);
                        checkIfMessageEmpty();
                        break;
                }
            }
        });

        // makes REST request for replacement of a primer if button has been clicked
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                primerImpl.removeAndGetNewPrimer(tube.getObjectID(), uobj.getUsername(), uobj.getPassword(), createPrimerStatus());

            }
        });

        //makes REST request for change of current location if button has been clicked
        setNewLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewLocation locationObj = new NewLocation(location);
                primerImpl.sendLocation(tube.getObjectID(), uobj.getUsername(), uobj.getPassword(), locationObj.getNewLocation());
            }
        });


    }

    /**
     * Checks if message for the reason of a primer replacement is empty. If that is the case, the submit button
     * is disabled, otherwise it is enabled.
     */
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

    /**
     * Calls one of two methods that either replaces the primer with a new one or notifies the user that the location
     * has been changed, depending on the {@link ResponseCode} of the REST request.
     *
     * @param o    the response body for the corresponding REST request. It must not be {@code null}.
     * @param code it must not be {@code null}.
     */
    @Override
    public void onResponseSuccess(Object o, ResponseCode code) {
        switch (code) {
            case REMOVEANDREPLACEPRIMER:
                receiveNewPrimer(o);
                break;
            case SENDLOCATION:
                sendNewLocation();
        }
    }

    /**
     * Notifies the user that the primer has been replaced.
     *
     * @param o the new {@link PrimerTube} that replaces the old one. The replacement can be {@code null},
     *          if there is no {@link PrimerTube} left.
     */
    private void receiveNewPrimer(Object o) {
        if (o == null) {
            // setup the alert builder
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Kein Ersatzprimer verfügbar.");
            // add a button
            builder.setPositiveButton("OK", null);
            // create and show the alert dialog
            AlertDialog dialog = builder.create();
            dialog.show();
        } else {
            Toast.makeText(this, R.string.replacementMessage, Toast.LENGTH_SHORT).show();
            newTube = (PrimerTube) o;
        }
    }

    /**
     * Notifies the user that the current location has been changed.
     */
    private void sendNewLocation() {
        Toast.makeText(this, R.string.newLocationMessage, Toast.LENGTH_SHORT).show();
    }

    /**
     * Returns a statuscode based on the radiobutton that has been checked.
     *
     * @return the reason for the replacement of a primer
     */
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


    /**
     * Sets the {@link PrimerStatus} for the replaced primer.
     *
     * @return the status of the replaced primer. The message of the status can be empty, if the statuscode is 1.
     */
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

    /**
     * Notifies the user when something went wrong with the request.
     */
    @Override
    public void onResponseError() {
        Toast.makeText(this, R.string.restError, Toast.LENGTH_SHORT).show();
    }

    /**
     * Notifies the user when something went wrong with the request.
     */
    @Override
    public void onResponseFailure() {
        Toast.makeText(this, R.string.restFailure, Toast.LENGTH_SHORT).show();
    }
}
