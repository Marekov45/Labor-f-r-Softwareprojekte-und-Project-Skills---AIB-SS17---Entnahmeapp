package client_aib_labswp_2017_ss_entnahmeapp.View.view.guis;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Parcelable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.os.Bundle;
import client.aib_labswp_2017_ss_entnahmeapp.R;
import client_aib_labswp_2017_ss_entnahmeapp.View.controller.serverAPI.CustomObserver;
import client_aib_labswp_2017_ss_entnahmeapp.View.controller.serverAPI.ListImpl;
import client_aib_labswp_2017_ss_entnahmeapp.View.controller.serverAPI.PrimerImpl;
import client_aib_labswp_2017_ss_entnahmeapp.View.controller.enumResponseCode.ResponseCode;
import client_aib_labswp_2017_ss_entnahmeapp.View.model.User;
import client_aib_labswp_2017_ss_entnahmeapp.View.model.model_List.PickList;
import client_aib_labswp_2017_ss_entnahmeapp.View.model.model_List.PrimerTube;
import client_aib_labswp_2017_ss_entnahmeapp.View.view.popup.PopPicklist;
import client_aib_labswp_2017_ss_entnahmeapp.View.view.scan.ScanActivity;
import client_aib_labswp_2017_ss_entnahmeapp.View.view.adapter.ListAdapterPicklist;
import com.google.android.gms.vision.barcode.Barcode;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link PickListActivity} displays the GUI for the withdrawal of primers. It supports all of the three procedure types
 * (Sanger, Manual, Extra).
 */
public class PickListActivity extends AppCompatActivity implements CustomObserver {


    public static final int REQUEST_CODE = 100;
    public static final int PERMISSION_REQUEST = 200;
    public static final int REQUEST_POPUP = 300;
    private ListImpl listImpl;
    private PrimerImpl primerImpl;
    private RadioGroup listGroup;
    private User uobj;
    private ListView listView;
    private ListAdapterPicklist adapter;

    /**
     * Initializes the activity.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously
     *                           being shut down then this Bundle contains the data it most recently supplied
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picklist);
        uobj = getIntent().getParcelableExtra(getString(R.string.intentUser));

        listView = (ListView) findViewById(R.id.listv);
        ViewGroup headerView = (ViewGroup) getLayoutInflater().inflate(R.layout.header, listView, false);
        listView.addHeaderView(headerView);

        listImpl = new ListImpl();
        listImpl.setCObserver(this);
        primerImpl = new PrimerImpl();
        primerImpl.setCObserver(this);

        Button scanButton = (Button) findViewById(R.id.scan);
        Button bListeAnzeigen = (Button) findViewById(R.id.bListeAnzeigen);

        listGroup = (RadioGroup) findViewById(R.id.listGroup);
        Button logoutButton = (Button) findViewById(R.id.btnLogout);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavUtils.navigateUpFromSameTask(PickListActivity.this);
            }
        });

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST);
        }
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PickListActivity.this, ScanActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });


        bListeAnzeigen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (chooseList().equals(getString(R.string.all))) {
                    listImpl.requestAllLists(uobj.getUsername(), uobj.getPassword());
                } else {
                    listImpl.requestList(uobj.getUsername(), uobj.getPassword(), chooseList());

                }
            }
        });
    } 


    /**
     * Returns a list type based on the radiobutton that has been checked.
     *
     * @return the type of procedure
     */
    private String chooseList() {

        int selectedID = listGroup.getCheckedRadioButtonId();

        switch (selectedID) {
            case R.id.radioRoboter:
                return getString(R.string.sanger);
            case R.id.radioManuell:
                return getString(R.string.manual);
            case R.id.radioExtra:
                return getString(R.string.extra);
            case R.id.radioAll:
                return getString(R.string.all);
        }
        return null;
    }

    /**
     * Called when the activity that launched exits, giving the {@code requestCode} it started with,
     * the {@code resultCode} it returned, and any additional {@code data} from it.
     *
     * @param requestCode allows to identify who this result came from. It must not be {@code null}.
     * @param resultCode  returned by the {@link PopPicklist} activity through one of its setResult methods.
     * @param data        intent, which can return result data to the caller.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                final Barcode barcode = data.getParcelableExtra(getString(R.string.intentBarcode));
                System.out.println(barcode.displayValue);
                adapter.checkBarcodeWithPrimer(barcode);
            }
        }
        if (requestCode == REQUEST_POPUP) {
            if (resultCode == Activity.RESULT_OK) {
                PrimerTube tubeNew = data.getParcelableExtra(getString(R.string.intentNewTube));
                int positionForReplacement = data.getIntExtra(getString(R.string.intentPosition), 0);
                adapter.changeRow(tubeNew, positionForReplacement);
            }
        }
    }

    /**
     * Calls one of two methods that either receives a list with primers or takes the primer out of the storage,
     * depending on the {@link ResponseCode} of the REST request.
     *
     * @param o    the response body for the corresponding REST request. It must not be {@code null}.
     * @param code it must not be {@code null}.
     */
    @Override
    public void onResponseSuccess(Object o, ResponseCode code) {
        switch (code) {
            case LIST:
            case COMPLETELIST:
                receivePrimerList(o);
                break;
            case TAKEPRIMER:
                takePrimer();
                break;
        }
    }

    /**
     * Notifies the user that a primer has been taken from the storage.
     */
    private void takePrimer() {
        Toast.makeText(this, R.string.takenMessage, Toast.LENGTH_SHORT).show();

    }

    /**
     * Sets the adapter for the listview and fills it with primers that were received by the REST request.
     *
     * @param o the list of picklists from the response body. It might be empty.
     */
    private void receivePrimerList(Object o) {
       // Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
        final List<PickList> pickLists = (List<PickList>) o;

        final List<PrimerTube> tubes = new ArrayList<>();
        for (PickList pickList : pickLists) {
            tubes.addAll(pickList.getPickList());
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (id != -1) {
                    PrimerTube actualTube = tubes.get(position - 1);
                    Intent intentPopUp = new Intent(PickListActivity.this, PopPicklist.class);
                    intentPopUp.putExtra(getString(R.string.intentTube), (Parcelable) actualTube);
                    intentPopUp.putExtra(getString(R.string.intentPosition), position);
                    intentPopUp.putExtra(getString(R.string.intentUser), uobj);
                    startActivityForResult(intentPopUp, REQUEST_POPUP);

                    // Toast.makeText(PickListActivity.this, "List Item was clicked at " + position, Toast.LENGTH_SHORT).show();
                }

            }
        });

        adapter = new ListAdapterPicklist(this, R.layout.rowlayout_picklist, R.id.txtPos, tubes, pickLists, uobj, listImpl, primerImpl);
        listView.setAdapter(adapter);

    }

    /**
     * Notifies the {@link User} when something went wrong with the request. If the error occurred for the
     * {@link ResponseCode#TAKEPRIMER} code, the taken status for the {@link PrimerTube} will be reset to false.
     *
     * @param o    the content of the response body for the corresponding REST request.
     * @param code it must not be {@code null}.
     */
    @Override
    public void onResponseError(Object o, ResponseCode code) {
        Toast.makeText(this, R.string.restError, Toast.LENGTH_SHORT).show();
        switch (code) {
            case TAKEPRIMER:
                int position = (int) o;
                adapter.updateTakenStatus(position, false);
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
        Toast.makeText(this, R.string.restFailure, Toast.LENGTH_SHORT).show();
    }

}
