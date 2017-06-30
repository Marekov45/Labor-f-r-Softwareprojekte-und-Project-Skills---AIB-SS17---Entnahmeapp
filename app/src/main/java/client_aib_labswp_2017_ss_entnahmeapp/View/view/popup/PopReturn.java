package client_aib_labswp_2017_ss_entnahmeapp.View.view.popup;

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
import client_aib_labswp_2017_ss_entnahmeapp.View.controller.serverAPI.CustomObserver;
import client_aib_labswp_2017_ss_entnahmeapp.View.controller.serverAPI.ListImpl;
import client_aib_labswp_2017_ss_entnahmeapp.View.controller.serverAPI.PrimerImpl;
import client_aib_labswp_2017_ss_entnahmeapp.View.controller.enumResponseCode.ResponseCode;
import client_aib_labswp_2017_ss_entnahmeapp.View.model.User;
import client_aib_labswp_2017_ss_entnahmeapp.View.model.model_List.PrimerStatus;
import client_aib_labswp_2017_ss_entnahmeapp.View.model.model_List.PrimerTube;
import client_aib_labswp_2017_ss_entnahmeapp.View.view.adapter.ListAdapterGatheredPrimer;


/**
 * {@link PopReturn} displays the popupwindow of a primer after it has been clicked in the list.
 * It supports the removal of primers.
 */
public class PopReturn extends AppCompatActivity implements CustomObserver {


    private Button btnDelete;
    private Button btnClose;
    private RadioGroup reasonforRemovalGroup;
    private ListImpl listImpl;
    private User uobj;
    private int positionGiven;
    private PrimerTube tube;
    private PrimerImpl primerImpl;
    private PrimerTube tubeToRemove;
    private EditText message;
    private ListView listView;
    private User userRemove;

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
        setContentView(R.layout.popup_return);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        //Set the size of the popupwindow
        getWindow().setLayout((int) (width * .8), (int) (height * .36));

        primerImpl = new PrimerImpl();
        primerImpl.setCObserver(this);
        listImpl = new ListImpl();
        listImpl.setCObserver(this);
        listView = (ListView) findViewById(R.id.listvGatheredPrimer);
        message = (EditText) findViewById(R.id.etxtMessage);


        reasonforRemovalGroup = (RadioGroup) findViewById(R.id.reasonRemovalGroup);


        btnDelete = (Button) findViewById(R.id.btnRemove);
        btnClose = (Button) findViewById(R.id.bttnclose);

        uobj = getIntent().getParcelableExtra(getString(R.string.intentUser));
        tube = getIntent().getParcelableExtra(getString(R.string.intentTube));
        positionGiven = getIntent().getIntExtra(getString(R.string.intentPosition), 0);
        tubeToRemove = getIntent().getParcelableExtra(getString(R.string.intentTubeRemove));
        userRemove = getIntent().getParcelableExtra(getString(R.string.intentUserRemove));
        final int position = positionGiven - 1;
        btnDelete.setEnabled(true);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //enables or disables submit button depending on the radiobutton that is checked
        reasonforRemovalGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbtnempty:
                        message.setHint(getString(R.string.replacementHintOptional));
                        btnDelete.setEnabled(true);
                        break;
                    case R.id.rbtnbroken:
                    case R.id.rbtnspoiled:
                    case R.id.rbtnnoinfo:
                        message.setText("");
                        message.setHint(getString(R.string.replacementHintRequired));
                        btnDelete.setEnabled(false);
                        checkIfMessageEmpty();
                        break;
                }
            }
        });

        // makes REST request for removal of a primer if button has been clicked
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tube == null) {
                    primerImpl.removePrimer(tubeToRemove.getObjectID(), userRemove.getUsername(), userRemove.getPassword(), createPrimerStatus());
                } else {
                    primerImpl.removePrimer(tube.getObjectID(), uobj.getUsername(), uobj.getPassword(), createPrimerStatus());
                    final Intent intentNewTube = new Intent();
                    intentNewTube.putExtra(getString(R.string.intentTubeRemoveAlt), (Parcelable) tube);
                    intentNewTube.putExtra(getString(R.string.intentPosition), positionGiven);
                    setResult(Activity.RESULT_OK, intentNewTube);

                }
                finish();
            }
        });


    }

    /**
     * Checks if message for the reason of a primer removal is empty. If that is the case, the submit button
     * is disabled, otherwise it is enabled.
     */
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


    /**
     * Calls method that removes the primer.
     *
     * @param o    the response body for the corresponding REST request. It is {@code null}.
     * @param code it must not be {@code null}.
     */
    @Override
    public void onResponseSuccess(Object o, ResponseCode code) {
        switch (code) {
            case REMOVEPRIMER:
                removePrimer();
                break;
        }
    }

    /**
     * Notifies the {@link User} that the primer has been removed.
     */
    private void removePrimer() {
        Toast.makeText(this, R.string.removalMessage, Toast.LENGTH_SHORT).show();
    }

    /**
     * Returns a statuscode based on the radiobutton that has been checked.
     *
     * @return the reason for the removal of a primer
     */
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


    /**
     * Sets the {@link PrimerStatus} for the removed primer.
     *
     * @return the status of the removed primer. The message of the status can be empty, if the statuscode is 1.
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
    }
}



