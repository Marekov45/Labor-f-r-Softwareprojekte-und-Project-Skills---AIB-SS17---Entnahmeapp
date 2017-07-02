package client_aib_labswp_2017_ss_entnahmeapp.View.view.guis;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Parcelable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import client.aib_labswp_2017_ss_entnahmeapp.R;
import client_aib_labswp_2017_ss_entnahmeapp.View.controller.serverAPI.CustomObserver;
import client_aib_labswp_2017_ss_entnahmeapp.View.controller.serverAPI.ListImpl;
import client_aib_labswp_2017_ss_entnahmeapp.View.controller.serverAPI.PrimerImpl;
import client_aib_labswp_2017_ss_entnahmeapp.View.controller.enumResponseCode.ResponseCode;
import client_aib_labswp_2017_ss_entnahmeapp.View.model.User;
import client_aib_labswp_2017_ss_entnahmeapp.View.model.model_List.PrimerTube;
import client_aib_labswp_2017_ss_entnahmeapp.View.view.popup.PopReturn;
import client_aib_labswp_2017_ss_entnahmeapp.View.view.scan.ScanActivity;
import client_aib_labswp_2017_ss_entnahmeapp.View.view.adapter.ListAdapterGatheredPrimer;
import com.google.android.gms.vision.barcode.Barcode;

import java.util.List;

/**
 * {@link ReturnGUI} displays the GUI for the return of primers.
 */
public class ReturnGUI extends AppCompatActivity implements CustomObserver {

    private ListView listView;
    private ListImpl listImpl;
    private PrimerImpl primerImpl;
    public static final int REQUEST_CODE = 100;
    public static final int PERMISSION_REQUEST = 200;
    public static final int REQUEST_POPUP = 300;
    private User uobj;
    private ListAdapterGatheredPrimer adapter;
    private Context context;


    /**
     * Initializes the activity.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously
     *                           being shut down then this Bundle contains the data it most recently supplied
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lager_rueckgabe_gui);

        context = this;
        uobj = getIntent().getParcelableExtra(getString(R.string.intentUser));
        listImpl = new ListImpl();
        listImpl.setCObserver(this);
        primerImpl = new PrimerImpl();
        primerImpl.setCObserver(this);

        listView = (ListView) findViewById(R.id.listvGatheredPrimer);
        ViewGroup headerView = (ViewGroup) getLayoutInflater().inflate(R.layout.header_gathered_primer, listView, false);
        listView.addHeaderView(headerView);

        Button logoutReturn = (Button) findViewById(R.id.btn_logoutReturn);
        logoutReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavUtils.navigateUpFromSameTask(ReturnGUI.this);
            }
        });

        Button showGatheredPrimer = (Button) findViewById(R.id.btn_gatheredPrimer);
        showGatheredPrimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listImpl.requestAllGatheredPrimers(uobj.getUsername(), uobj.getPassword());
            }
        });

        Button scanButton = (Button) findViewById(R.id.scanReturn);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST);
        }
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReturnGUI.this, ScanActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }

    /**
     * Called when the activity that launched exits, giving the {@code requestCode} it started with,
     * the {@code resultCode} it returned, and any additional {@code data} from it.
     *
     * @param requestCode allows to identify who this result came from. It must not be {@code null}.
     * @param resultCode  returned by the {@link PopReturn} activity through one of its setResult methods.
     * @param data        intent, which can return result data to the caller.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                final Barcode barcode = data.getParcelableExtra(getString(R.string.intentBarcode));
                System.out.println(barcode.displayValue);
                adapter.checkBarcodeWithPrimer(this, barcode, listView);

            }
        }
        if (requestCode == REQUEST_POPUP && resultCode == RESULT_OK) {
            if (data != null) {
                PrimerTube tubeToRemove = data.getParcelableExtra(getString(R.string.intentTubeRemoveAlt));
                int positionForReplacement = data.getIntExtra(getString(R.string.intentPosition), 0);
                adapter.setPrimerOnTakenIfRemovedManually(tubeToRemove, positionForReplacement);
            }
        }
    }

    /**
     * Calls one of two methods that either receives a list with gathered primers or returns the primer to the storage,
     * depending on the {@link ResponseCode} of the REST request.
     *
     * @param o    the response body for the corresponding REST request. It must not be {@code null}.
     * @param code it must not be {@code null}.
     */
    @Override
    public void onResponseSuccess(Object o, ResponseCode code) {
        switch (code) {
            case COMPLETEGATHEREDLIST:
                receiveGatheredPrimerList(o);
                break;
            case RETURNPRIMER:
                returnPrimer();
                break;
        }
    }

    /**
     * Notifies the user that the primer has been returned.
     */
    private void returnPrimer() {
        Toast.makeText(this, R.string.toastReturnPrimer, Toast.LENGTH_SHORT).show();

    }

    /**
     * Sets the adapter for the listview and fills it with gathered primers that were received by the REST request.
     *
     * @param o the list of primertubes from the response body. It might be empty.
     */
    private void receiveGatheredPrimerList(Object o) {
        //  Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
        final List<PrimerTube> tubes = (List<PrimerTube>) o;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (id != -1) {
                    PrimerTube actualTube = tubes.get(position - 1);
                    Intent intentPopUp = new Intent(ReturnGUI.this, PopReturn.class);
                    intentPopUp.putExtra(getString(R.string.intentTube), (Parcelable) actualTube);
                    intentPopUp.putExtra(getString(R.string.intentPosition), position);
                    intentPopUp.putExtra(getString(R.string.intentUser), uobj);
                    startActivityForResult(intentPopUp, REQUEST_POPUP);
                }

            }
        });


        adapter = new ListAdapterGatheredPrimer(this, R.layout.rowlayout_gathered_primer, R.id.txtPrimerLastGathered, tubes, uobj, listImpl, primerImpl);
        listView.setAdapter(adapter);
    }

    /**
     * Notifies the {@link User} when something went wrong with the request. If the error occurred for the
     * {@link ResponseCode#RETURNPRIMER} code, the taken status for the {@link PrimerTube} will be reset to false.
     *
     * @param o    the content of the response body for the corresponding REST request.
     * @param code it must not be {@code null}.
     */
    @Override
    public void onResponseError(Object o, ResponseCode code) {
        Toast.makeText(this, R.string.restError, Toast.LENGTH_SHORT).show();
        switch (code) {
            case RETURNPRIMER:
                int position = (int) o;
                adapter.changeReturnStatus(position, false);
                break;
        }
    }

    /**
     * Notifies the {@link User} when something went wrong with the request.
     *
     * @param code it must not be {@code null}.
     */
    @Override
    public void onResponseFailure(ResponseCode code) {
        Toast.makeText(this, "Failure", Toast.LENGTH_SHORT).show();
    }
}
