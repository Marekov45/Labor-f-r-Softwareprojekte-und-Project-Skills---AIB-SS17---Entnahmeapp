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
 * {@link PopPicklist} displays the popupwindow of a primer after it has been clicked in the list.
 * It supports the replacement of primers.
 */
public class PopPicklist extends AppCompatActivity implements CustomObserver {

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
        setContentView(R.layout.popupwindow);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;
        //Set the size of the popupwindow
        getWindow().setLayout((int) (width * .8), (int) (height * .62));

        primerImpl = new PrimerImpl();
        primerImpl = new PrimerImpl();
        primerImpl.setCObserver(this);

        TextView primerName = (TextView) findViewById(R.id.tvPrimerName);
        TextView shownName = (TextView) findViewById(R.id.tvNameActTube);
        TextView primerID = (TextView) findViewById(R.id.tvPrimerTubeID);
        TextView shownID = (TextView) findViewById(R.id.tvIDActTube);
        TextView primerLOT = (TextView) findViewById(R.id.tvLOT);
        TextView shownLOT = (TextView) findViewById(R.id.tvLOTActTube);
        TextView primerManufacturer = (TextView) findViewById(R.id.tvManufacturerActTube);
        TextView shownManufacturer = (TextView) findViewById(R.id.tvManufacturerActTube);
        TextView primerNote = (TextView) findViewById(R.id.tvNote);
        reasonforNewPrimerGroup = (RadioGroup) findViewById(R.id.reasonNewPrimerGroup);

        message = (EditText) findViewById(R.id.editTextNote);
        submit = (Button) findViewById(R.id.btnSubmit);
        btnGoBack = (Button) findViewById(R.id.btnclose);

        uobj = getIntent().getParcelableExtra(getString(R.string.intentUser));
        tube = getIntent().getParcelableExtra(getString(R.string.intentTube));
        positionGiven = getIntent().getIntExtra(getString(R.string.intentPosition), 0);
        final int position = positionGiven - 1;


        shownName.setText(tube.getName());
        shownID.setText(tube.getPrimerTubeID());
        shownLOT.setText(tube.getLotNr());
        shownManufacturer.setText(tube.getManufacturer());
        submit.setEnabled(true);

        btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //closes the popupwindow if nothing has been changed
                if (newTube == null) {
                    finish();
                } else {
                    //replaces the primer
                    final Intent intentNewTube = new Intent();
                    intentNewTube.putExtra(getString(R.string.intentNewTube), (Parcelable) newTube);
                    intentNewTube.putExtra(getString(R.string.intentPosition), positionGiven);
                    setResult(Activity.RESULT_OK, intentNewTube);
                    finish();
                }
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
     * Calls method that replaces the old primer with a new one.
     *
     * @param o    the response body for the corresponding REST request. It is {@code null}.
     * @param code it must not be {@code null}.
     */
    @Override
    public void onResponseSuccess(Object o, ResponseCode code) {
        switch (code) {
            case REMOVEANDREPLACEPRIMER:
                receiveNewPrimer(o);
                break;
        }
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
                builder.setMessage(getString(R.string.newListMessage));
                // add a button
                builder.setPositiveButton(getString(R.string.btnOK), null);
                // create and show the alert dialog
                AlertDialog dialog = builder.create();
                dialog.show();
                break;
        }
    }
}


