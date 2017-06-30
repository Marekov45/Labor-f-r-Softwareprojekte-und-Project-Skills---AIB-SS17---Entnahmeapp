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
 * {@link PopSanger} displays the popupwindow of a primer after it has been clicked in the list.
 * It supports the replacement of primers and the change of their current location.
 */
public class PopSanger extends AppCompatActivity implements CustomObserver {

    private TextView primerName;
    private TextView shownName;
    private TextView primerLOT;
    private TextView shownLOT;
    private TextView shownLocation;
    private TextView primerNote;
    private EditText message;
    private Button submit;
    private Button btnGoBack;
    private RadioGroup reasonforNewPrimerGroup;
    private Button btnNewPosition;
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
        setContentView(R.layout.popupwindow_sanger);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;
        //set the size of the popupwindow
        getWindow().setLayout((int) (width * .8), (int) (height * .75));

        primerImpl = new PrimerImpl();
        primerImpl = new PrimerImpl();
        primerImpl.setCObserver(this);

        primerName = (TextView) findViewById(R.id.tvPrimerName);
        shownName = (TextView) findViewById(R.id.tvNameActTube);

        primerLOT = (TextView) findViewById(R.id.tvLOT);
        shownLOT = (TextView) findViewById(R.id.tvLOTActTube);

        shownLocation = (TextView) findViewById(R.id.tvActLocation);
        primerNote = (TextView) findViewById(R.id.tvNote);
        reasonforNewPrimerGroup = (RadioGroup) findViewById(R.id.reasonNewPrimerGroup);


        message = (EditText) findViewById(R.id.editTextNote);
        submit = (Button) findViewById(R.id.btnSubmit);
        btnGoBack = (Button) findViewById(R.id.btnclose);
        spinnerLocation = (Spinner) findViewById(R.id.spinnerGuiWorkspace);

        //spinner with every possible location to choose from
        spinnerLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (spinnerLocation.getSelectedItemPosition()) {
                    case 0:
                        location = "";
                        btnNewPosition.setEnabled(false);
                        break;
                    case 1:
                        location = getResources().getStringArray(R.array.workspace_array)[1];
                        btnNewPosition.setEnabled(true);
                        break;
                    case 2:
                        location = getResources().getStringArray(R.array.workspace_array)[2];
                        btnNewPosition.setEnabled(true);
                        break;
                    case 3:
                        location = getResources().getStringArray(R.array.workspace_array)[3];
                        btnNewPosition.setEnabled(true);
                        break;
                    case 4:
                        location = getResources().getStringArray(R.array.workspace_array)[4];
                        btnNewPosition.setEnabled(true);
                        break;
                    case 5:
                        location = getResources().getStringArray(R.array.workspace_array)[5];
                        btnNewPosition.setEnabled(true);
                        break;
                    case 6:
                        location = getResources().getStringArray(R.array.workspace_array)[6];
                        btnNewPosition.setEnabled(true);
                        break;
                    case 7:
                        location = getResources().getStringArray(R.array.workspace_array)[7];
                        btnNewPosition.setEnabled(true);
                        break;
                    case 8:
                        location = getResources().getStringArray(R.array.workspace_array)[8];
                        btnNewPosition.setEnabled(true);
                        break;
                    case 9:
                        location = getResources().getStringArray(R.array.workspace_array)[9];
                        btnNewPosition.setEnabled(true);
                        break;
                    case 10:
                        location = getResources().getStringArray(R.array.workspace_array)[10];
                        btnNewPosition.setEnabled(true);
                        break;
                    case 11:
                        location = getResources().getStringArray(R.array.workspace_array)[11];
                        btnNewPosition.setEnabled(true);
                        break;
                    case 12:
                        location = getResources().getStringArray(R.array.workspace_array)[12];
                        btnNewPosition.setEnabled(true);
                        break;
                    case 13:
                        location = getResources().getStringArray(R.array.workspace_array)[13];
                        btnNewPosition.setEnabled(true);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                btnNewPosition.setEnabled(false);
            }
        });

        uobj = getIntent().getParcelableExtra(getString(R.string.intentUser));
        tube = getIntent().getParcelableExtra(getString(R.string.intentTube));
        positionGiven = getIntent().getIntExtra(getString(R.string.intentPosition), 0);
        final int position = positionGiven - 1;

        shownName.setText(tube.getName());
        shownLOT.setText(tube.getLotNr());
        shownLocation.setText(tube.getCurrentLocation());
        submit.setEnabled(true);
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
                        final Intent intentNewPostition = new Intent();
                        intentNewPostition.putExtra(getString(R.string.intentNewLocation), (Parcelable) locationObj);
                        intentNewPostition.putExtra(getString(R.string.intentActualTube), (Parcelable) tube);
                        intentNewPostition.putExtra(getString(R.string.intentPosition), positionGiven);
                        setResult(Activity.RESULT_OK, intentNewPostition);
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

        btnNewPosition = (Button) findViewById(R.id.btnNewPosition);
        btnNewPosition.setEnabled(false);
        //makes REST request for change of current location if button has been clicked
        btnNewPosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewLocation locationObj = new NewLocation(location);
                primerImpl.sendLocation(tube.getObjectID(), uobj.getUsername(), uobj.getPassword(), locationObj.getNewLocation());
            }
        });
        //enables or disables submit button depending on the radiobutton that is checked
        reasonforNewPrimerGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
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
     * Calls one of two methods that either replaces the primer with a new one or notifies the {@link User} that the location
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
                break;
        }
    }

    /**
     * Notifies the {@link User} that the current location has been changed.
     */
    private void sendNewLocation() {
        Toast.makeText(this, R.string.newLocationMessage, Toast.LENGTH_SHORT).show();
    }

    /**
     * Notifies the {@link User} that the primer has been replaced.
     *
     * @param o the new {@link PrimerTube} that replaces the old one. The replacement can be {@code null},
     *          if there is no {@link PrimerTube} left.
     */
    private void receiveNewPrimer(Object o) {

        Toast.makeText(this, R.string.replacementMessage, Toast.LENGTH_SHORT).show();
        newTube = (PrimerTube) o;
    }

    /**
     * Returns a statuscode based on the radiobutton that has been checked.
     *
     * @return the reason for the replacement of a primer
     */
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
     * Notifies the {@link User} when something went wrong with the request.
     */
    @Override
    public void onResponseError(Object o, ResponseCode code) {
        Toast.makeText(this, R.string.restError, Toast.LENGTH_SHORT).show();
    }

    /**
     * Notifies the {@link User} when something went wrong with the request.
     */
    @Override
    public void onResponseFailure(ResponseCode code) {
        Toast.makeText(this, R.string.restFailure, Toast.LENGTH_SHORT).show();
        switch (code) {
            case REMOVEANDREPLACEPRIMER:
                // setup the alert builder
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(getString(R.string.noReplacementMessage));
                builder.setMessage(getString(R.string.loadListMessage));
                // add a button
                builder.setPositiveButton(getString(R.string.btnOK), null);
                // create and show the alert dialog
                AlertDialog dialog = builder.create();
                dialog.show();
                break;
        }
    }
}


