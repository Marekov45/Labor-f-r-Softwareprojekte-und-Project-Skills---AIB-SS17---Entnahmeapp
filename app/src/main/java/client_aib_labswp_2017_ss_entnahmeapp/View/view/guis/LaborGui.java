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

public class LaborGui extends AppCompatActivity implements CustomObserver, SearchView.OnQueryTextListener {


    private Button logoutButton;
    private Button listButton;
    private CheckBox searchBox;
    private SearchView view;
    private boolean wildcardSearch;
    private ListImpl listImpl;
    private User uobj;
    private ListView listView;
    public static final int REQUEST_CODE = 100;
    public static final int PERMISSION_REQUEST = 200;
    public static final int REQUEST_POPUP = 300;
    public static final int REQUEST_POPUP_SEARCH = 400;
    private ListAdapterLabor adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_labor_gui);
        uobj = getIntent().getParcelableExtra("USER");
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
                Toast.makeText(LaborGui.this, "Erfolgreich ausgeloggt", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(LaborGui.this, "Suche mit Platzhalter aktiviert", Toast.LENGTH_SHORT).show();
                } else {
                    wildcardSearch = false;
                }
            }
        });

        //  listImpl.requestAllGatheredPrimers(uobj.getUsername(), uobj.getPassword());
        setupSearchView();
    }

    private void setupSearchView() {
        view.setOnQueryTextListener(this);
        view.setSubmitButtonEnabled(true);
        view.setQueryHint(getString(R.string.hintSearchPrimer));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_POPUP) {
            if (resultCode == Activity.RESULT_OK) {
                PrimerTube tubeNew = data.getParcelableExtra("NEWTUBE");
                PrimerTube actualtube = data.getParcelableExtra("ACTUALTUBE");
                int positionForReplacement = data.getIntExtra("POSITION", 0);
                NewLocation newLocation = data.getParcelableExtra("NEWLOCATION");
                if (tubeNew != null && newLocation == null) {
                    adapter.changeRow(tubeNew, positionForReplacement);
                }
                if (newLocation != null && tubeNew == null) {
                    adapter.changeCurrentLocation(actualtube, positionForReplacement, newLocation);
                }
            } else if (resultCode == REQUEST_POPUP_SEARCH) {
                if (resultCode == Activity.RESULT_OK) {
                    PrimerTube tubeNew = data.getParcelableExtra("NEWTUBE");
                    PrimerTube tubeactualSearch = data.getParcelableExtra("ACTUALTUBE");
                    int positionForReplacementSearch = data.getIntExtra("POSITION", 0);
                    NewLocation newLocationSearch = data.getParcelableExtra("NEWLOCATION");
                    if (tubeNew != null && newLocationSearch == null) {
                        adapter.changeRow(tubeNew, positionForReplacementSearch);
                    }
                    if (newLocationSearch != null && tubeNew == null) {
                        adapter.changeCurrentLocation(tubeactualSearch, positionForReplacementSearch, newLocationSearch);
                    }
                }

            }
        }
    }

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

    private void receiveAllGatheredList(Object o) {
        //  System.out.println(o.toString());
        Toast.makeText(this, "SuccessALLGATHEREDPRIMERS", Toast.LENGTH_SHORT).show();
        final List<PrimerTube> tubes = (List<PrimerTube>) o;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (id != -1) {
                    PrimerTube actualTube = tubes.get(position - 1);
                    Intent intentPopUp = new Intent(LaborGui.this, PopTracking.class);
                    intentPopUp.putExtra("TUBE", (Parcelable) actualTube);
                    intentPopUp.putExtra("POSITION", position);
                    intentPopUp.putExtra("USER", uobj);
                    startActivityForResult(intentPopUp, REQUEST_POPUP_SEARCH);

//                    Toast.makeText(LaborGui.this, "List Item was clicked at " + position, Toast.LENGTH_SHORT).show();
                }

            }
        });
        adapter = new ListAdapterLabor(this, R.layout.rowlayout_tracking, R.id.txtPos, tubes, uobj, listImpl);
        listView.setAdapter(adapter);
    }

    private void receiveGatheredList(Object o) {
        // System.out.println(o.toString());
        Toast.makeText(this, "Entnommene Primer empfangen", Toast.LENGTH_SHORT).show();
        final List<PrimerTube> tubesSearch = (List<PrimerTube>) o;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (id != -1) {
                    PrimerTube actualTubeSearch = tubesSearch.get(position - 1);
                    Intent intentpopUpSearch = new Intent(LaborGui.this, PopTracking.class);
                    intentpopUpSearch.putExtra("TUBE", (Parcelable) actualTubeSearch);
                    intentpopUpSearch.putExtra("POSITION", position);
                    intentpopUpSearch.putExtra("USER", uobj);
                    startActivityForResult(intentpopUpSearch, REQUEST_POPUP);
                }
            }
        });
        adapter = new ListAdapterLabor(this, R.layout.rowlayout_tracking, R.id.txtPos, tubesSearch, uobj, listImpl);
        listView.setAdapter(adapter);
    }

    @Override
    public void onResponseError() {
        Toast.makeText(this, "ResponseError", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponseFailure() {
        Toast.makeText(this, "Failure", Toast.LENGTH_SHORT).show();
    }

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

