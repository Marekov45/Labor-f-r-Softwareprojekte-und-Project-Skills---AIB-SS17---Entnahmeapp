package client_aib_labswp_2017_ss_entnahmeapp.View.view.guis;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.app.NavUtils;
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
import client_aib_labswp_2017_ss_entnahmeapp.View.model.NewLocation;
import client_aib_labswp_2017_ss_entnahmeapp.View.model.User;
import client_aib_labswp_2017_ss_entnahmeapp.View.model.model_List.PickList;
import client_aib_labswp_2017_ss_entnahmeapp.View.model.model_List.PrimerTube;
import client_aib_labswp_2017_ss_entnahmeapp.View.view.popup.PopSanger;
import client_aib_labswp_2017_ss_entnahmeapp.View.view.adapter.ListAdapterLastSanger;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link LastSangerListActivity} displays the GUI for the most recently processed list.
 */
public class LastSangerListActivity extends AppCompatActivity implements CustomObserver {

    private User uobj;
    private ListView listView;
    private ListImpl listImpl;
    public static final int REQUEST_POPUP = 300;
    private ListAdapterLastSanger adapter;
    private PrimerImpl primerImpl;

    /**
     * Initializes the activity.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously
     *                           being shut down then this Bundle contains the data it most recently supplied
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_processed_list);

        uobj = getIntent().getParcelableExtra("USER");
        listImpl = new ListImpl();
        listImpl.setCObserver(this);

        primerImpl = new PrimerImpl();
        primerImpl.setCObserver(this);

        listView = (ListView) findViewById(R.id.listvLastSanger);
        ViewGroup headerView = (ViewGroup) getLayoutInflater().inflate(R.layout.header_last_sanger, listView, false);
        listView.addHeaderView(headerView);

        RadioGroup listGroup = (RadioGroup) findViewById(R.id.listGroup);
        Button logoutButtonLastSanger = (Button) findViewById(R.id.btnLogoutLastSanger);
        logoutButtonLastSanger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavUtils.navigateUpFromSameTask(LastSangerListActivity.this);
            }
        });
        Button showListLastSanger = (Button) findViewById(R.id.btnShowListLastSanger);
        showListLastSanger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listImpl.requestLastSangerList(uobj.getUsername(), uobj.getPassword());
            }
        });
    }

    /**
     * Calls method that receives last processed picklists for locations that can process "Sanger".
     *
     * @param o    the response body for the corresponding REST request.
     * @param code it must not be {@code null}.
     */
    @Override
    public void onResponseSuccess(Object o, ResponseCode code) {
        switch (code) {
            case LASTSANGER:
                receiveLastSangerList(o);
                break;

        }
    }

    /**
     * Called when the activity that launched exits, giving the {@code requestCode} it started with,
     * the {@code resultCode} it returned, and any additional {@code data} from it.
     *
     * @param requestCode allows to identify who this result came from. It must not be {@code null}.
     * @param resultCode  returned by the {@link PopSanger} activity through one of its setResult methods.
     * @param data        intent, which can return result data to the caller.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_POPUP) {
            if (resultCode == Activity.RESULT_OK) {
                PrimerTube tubeNew = data.getParcelableExtra(getString(R.string.intentNewTube));
                PrimerTube actualtube = data.getParcelableExtra(getString(R.string.intentActualTube));
                int positionForReplacement = data.getIntExtra(getString(R.string.intentPosition), 0);
                NewLocation newLocation = data.getParcelableExtra(getString(R.string.intentNewLocation));
                if (tubeNew != null && newLocation == null) {
                    adapter.changeRow(tubeNew, positionForReplacement);
                }
                if (newLocation != null && tubeNew == null) {
                    adapter.changeCurrentLocation(actualtube, positionForReplacement, newLocation);
                }
                if (tubeNew == null && newLocation == null) {

                }
            }
        }

    }

    /**
     * Sets the adapter for the listview and fills it with primers that were received by the REST request.
     *
     * @param o the list of picklists from the response body. It might be empty.
     */
    private void receiveLastSangerList(Object o) {
        // Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
        List<PickList> pickLists = (List<PickList>) o;

        final List<PrimerTube> tubes = new ArrayList<>();
        for (PickList pickList : pickLists) {
            tubes.addAll(pickList.getPickList());
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (id != -1) {
                    PrimerTube actualTube = tubes.get(position - 1);
                    Intent intentPopUp = new Intent(LastSangerListActivity.this, PopSanger.class);
                    intentPopUp.putExtra(getString(R.string.intentTube), (Parcelable) actualTube);
                    intentPopUp.putExtra(getString(R.string.intentPosition), position);
                    intentPopUp.putExtra(getString(R.string.intentUser), uobj);
                    startActivityForResult(intentPopUp, REQUEST_POPUP);

                    // Toast.makeText(LastSangerListActivity.this, "List Item was clicked at " + position, Toast.LENGTH_SHORT).show();
                }

            }
        });
        adapter = new ListAdapterLastSanger(this, R.layout.rowlayout_last_sanger, R.id.txtPos, tubes, pickLists, uobj, listImpl, primerImpl);
        listView.setAdapter(adapter);
    }

    /**
     * Notifies the {@link User} when something went wrong with the request.
     *
     * @param o    the content of the response body for the corresponding REST request. It must not be {@code null}.
     * @param code it must not be {@code null}.
     */
    @Override
    public void onResponseError(Object o, ResponseCode code) {
        Toast.makeText(this, R.string.restError, Toast.LENGTH_SHORT).show();

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
