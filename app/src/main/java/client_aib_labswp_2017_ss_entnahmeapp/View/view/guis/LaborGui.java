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
import client_aib_labswp_2017_ss_entnahmeapp.View.controller.enumResponseCode.ResponseCode;
import client_aib_labswp_2017_ss_entnahmeapp.View.model.NewLocation;
import client_aib_labswp_2017_ss_entnahmeapp.View.model.User;
import client_aib_labswp_2017_ss_entnahmeapp.View.model.model_List.PrimerTube;
import client_aib_labswp_2017_ss_entnahmeapp.View.view.popup.PopTracking;
import client_aib_labswp_2017_ss_entnahmeapp.View.view.adapter.ListAdapterLabor;

import java.util.List;

/**
 * {@link LaborGui} displays the GUI for the tracking of primers. It also supports the specific search of primers based on their names.
 */
public class LaborGui extends AppCompatActivity implements CustomObserver, SearchView.OnQueryTextListener {


    private Button logoutButton;
    private Button listButton;
    private SearchView view;
    private ListImpl listImpl;
    private User uobj;
    private CheckBox searchBox;
    private boolean wildcardSearch;
    private ListView listView;
    public static final int REQUEST_POPUP = 300;
    public static final int REQUEST_POPUP_SEARCH = 400;
    private ListAdapterLabor adapter;

    /**
     * Initializes the activity.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously
     *                           being shut down then this Bundle contains the data it most recently supplied
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_labor_gui);
        uobj = getIntent().getParcelableExtra(getString(R.string.intentUser));
        listImpl = new ListImpl();
        listImpl = new ListImpl();
        listImpl.setCObserver(this);
        listView = (ListView) findViewById(R.id.list);
        wildcardSearch = false;
        ViewGroup headerView = (ViewGroup) getLayoutInflater().inflate(R.layout.header_tracking, listView, false);
        listView.addHeaderView(headerView);
        view = (SearchView) findViewById(R.id.search);
        logoutButton = (Button) findViewById(R.id.logout);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavUtils.navigateUpFromSameTask(LaborGui.this);
                Toast.makeText(LaborGui.this, R.string.logoutMessage, Toast.LENGTH_SHORT).show();
            }

        });
        listButton = (Button) findViewById(R.id.btnShowListAll);
        listButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listImpl.requestAllGatheredPrimers(uobj.getUsername(), uobj.getPassword());
            }
        });
        searchBox = (CheckBox) findViewById(R.id.boxAdvancedSearch);
        searchBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    wildcardSearch = true;
                    Toast.makeText(LaborGui.this, R.string.AdvSearchMessage, Toast.LENGTH_SHORT).show();
                } else {
                    wildcardSearch = false;
                }
            }
        });
        setupSearchView();
    }

    /**
     * Sets up the search bar for the primers.
     */
    private void setupSearchView() {
        view.setOnQueryTextListener(this);
        view.setSubmitButtonEnabled(true);
        view.setQueryHint(getString(R.string.hintSearchPrimer));
    }

    /**
     * Called when the activity that launched exits, giving the {@code requestCode} it started with,
     * the {@code resultCode} it returned, and any additional {@code data} from it.
     *
     * @param requestCode allows to identify who this result came from. It must not be {@code null}.
     * @param resultCode  returned by the {@link PopTracking} activity through one of its setResult methods.
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
                    adapter.changeRow(tubeNew, positionForReplacement, newLocation);
                }
                if (newLocation != null && tubeNew == null) {
                    adapter.changeCurrentLocation(actualtube, positionForReplacement, newLocation);
                }
            }

        }
        if (requestCode == REQUEST_POPUP_SEARCH) {
            if (resultCode == Activity.RESULT_OK) {
                PrimerTube tubeNew = data.getParcelableExtra(getString(R.string.intentNewTube));
                PrimerTube tubeactualSearch = data.getParcelableExtra(getString(R.string.intentActualTube));
                int positionForReplacementSearch = data.getIntExtra(getString(R.string.intentPosition), 0);
                NewLocation newLocationSearch = data.getParcelableExtra(getString(R.string.intentNewLocation));
                if (tubeNew != null && newLocationSearch == null) {
                    adapter.changeRow(tubeNew, positionForReplacementSearch, newLocationSearch);
                }
                if (newLocationSearch != null && tubeNew == null) {
                    adapter.changeCurrentLocation(tubeactualSearch, positionForReplacementSearch, newLocationSearch);
                }
            }

        }
    }

    /**
     * Calls one of two methods that fills the listview with gathered primers, depending on the {@link ResponseCode} of the REST request.
     *
     * @param o    the response body for the corresponding REST request. It must not be {@code null}.
     * @param code it must not be {@code null}.
     */
    @Override
    public void onResponseSuccess(Object o, ResponseCode code) {
        switch (code) {
            case COMPLETEGATHEREDLIST:
                receiveAllGatheredList(o);
                break;
            case GATHEREDLIST:
                receiveGatheredList(o);
                break;
        }
    }

    /**
     * Sets the adapter for the listview and fills it with primers that were received by the REST request.
     *
     * @param o the list of primertubes from the response body. It might be empty.
     */
    private void receiveAllGatheredList(Object o) {
        final List<PrimerTube> tubes = (List<PrimerTube>) o;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (id != -1) {
                    PrimerTube actualTube = tubes.get(position - 1);
                    Intent intentPopUp = new Intent(LaborGui.this, PopTracking.class);
                    intentPopUp.putExtra(getString(R.string.intentTube), (Parcelable) actualTube);
                    intentPopUp.putExtra(getString(R.string.intentPosition), position);
                    intentPopUp.putExtra(getString(R.string.intentUser), uobj);
                    // Launches the PopTracking class and when this activity exits, the onActivityResult method in this class is called
                    startActivityForResult(intentPopUp, REQUEST_POPUP_SEARCH);

                }

            }
        });
        adapter = new ListAdapterLabor(this, R.layout.rowlayout_tracking, R.id.txtPos, tubes, uobj, listImpl);
        listView.setAdapter(adapter);
    }

    /**
     * Sets the adapter for the listview and fills it with primers that were received by the REST request.
     *
     * @param o the list of primertubes from the response body. It might be empty.
     */
    private void receiveGatheredList(Object o) {
        final List<PrimerTube> tubesSearch = (List<PrimerTube>) o;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (id != -1) {
                    PrimerTube actualTubeSearch = tubesSearch.get(position - 1);
                    Intent intentpopUpSearch = new Intent(LaborGui.this, PopTracking.class);
                    intentpopUpSearch.putExtra(getString(R.string.intentTube), (Parcelable) actualTubeSearch);
                    intentpopUpSearch.putExtra(getString(R.string.intentPosition), position);
                    intentpopUpSearch.putExtra(getString(R.string.intentUser), uobj);
                    startActivityForResult(intentpopUpSearch, REQUEST_POPUP);
                }
            }
        });
        adapter = new ListAdapterLabor(this, R.layout.rowlayout_tracking, R.id.txtPos, tubesSearch, uobj, listImpl);
        listView.setAdapter(adapter);
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

    /**
     * Called when the user submits the query in the searchview. If the advanced search has been activated,
     * the wildcard "%" is used at the end of the query text.
     *
     * @param query the query text that is to be submitted. It is not allowed to be {@code null} and must not be an empty
     *              String (<code>""</code>).
     * @return true if the query has been handled by the listener
     */
    @Override
    public boolean onQueryTextSubmit(String query) {
        if (wildcardSearch) {
            listImpl.requestGatheredPrimers(query + "%", uobj.getUsername(), uobj.getPassword());
        } else {
            listImpl.requestGatheredPrimers(query, uobj.getUsername(), uobj.getPassword());
        }

        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
