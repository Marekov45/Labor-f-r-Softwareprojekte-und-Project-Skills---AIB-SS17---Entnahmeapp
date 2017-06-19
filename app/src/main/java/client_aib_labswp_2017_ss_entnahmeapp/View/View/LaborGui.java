package client_aib_labswp_2017_ss_entnahmeapp.View.View;
import android.app.Activity;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import client.aib_labswp_2017_ss_entnahmeapp.R;
import client_aib_labswp_2017_ss_entnahmeapp.View.Controller.ServerAPI.CustomObserver;
import client_aib_labswp_2017_ss_entnahmeapp.View.Controller.ServerAPI.ListImpl;
import client_aib_labswp_2017_ss_entnahmeapp.View.Controller.enumResponseCode.ResponseCode;
import client_aib_labswp_2017_ss_entnahmeapp.View.Model.NewLocation;
import client_aib_labswp_2017_ss_entnahmeapp.View.Model.User;
import client_aib_labswp_2017_ss_entnahmeapp.View.Model.model_List.PickList;
import client_aib_labswp_2017_ss_entnahmeapp.View.Model.model_List.PrimerTube;
import com.google.android.gms.vision.barcode.Barcode;

import java.util.ArrayList;
import java.util.List;

public class LaborGui extends AppCompatActivity implements CustomObserver, SearchView.OnQueryTextListener {


    private Button logoutButton;
    private SearchView view;
    private ListImpl listImpl;
    private User uobj;
    private ListView listView;
    public static final int REQUEST_CODE = 100;
    public static final int PERMISSION_REQUEST = 200;
    public static final int REQUEST_POPUP=300;
    ListAdapterLabor adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_labor_gui);
        uobj = getIntent().getParcelableExtra("USER");
        listImpl = new ListImpl();
        listImpl.setCObserver(this);
        listView = (ListView) findViewById(R.id.list);
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


        listImpl.requestAllGatheredPrimers(uobj.getUsername(), uobj.getPassword());

        setupSearchView();
    }

    private void setupSearchView() {
        // view.setIconifiedByDefault(false);
        view.setOnQueryTextListener(this);
        view.setSubmitButtonEnabled(true);
        view.setQueryHint("Primername");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==REQUEST_POPUP){
            if(resultCode== Activity.RESULT_OK){
                PrimerTube tubeNew= data.getParcelableExtra("NEWTUBE");
                PrimerTube actualtube = data.getParcelableExtra("ACTUALTUBE");
                int positionForReplacement = data.getIntExtra("POSITION",0);
                NewLocation newLocation = data.getParcelableExtra("NEWLOCATION");
                if(tubeNew!=null&&newLocation==null){
                    adapter.changeRow(tubeNew, positionForReplacement);
                }
                if(newLocation!=null&&tubeNew==null){
                    adapter.changeCurrentLocation(actualtube, positionForReplacement, newLocation);
                    System.out.println("eine neue position"+newLocation.getNewLocation().toString());
                }
                System.out.println("good");
            }else {

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
                    PrimerTube actualTube = tubes.get(position-1);
                    Intent intentPopUp = new Intent(LaborGui.this, PopTracking.class);
                    intentPopUp.putExtra("TUBE", (Parcelable) actualTube);
                    intentPopUp.putExtra("POSITION",position);
                    intentPopUp.putExtra("USER",uobj);
                    startActivityForResult(intentPopUp, REQUEST_POPUP);

                    Toast.makeText(LaborGui.this, "List Item was clicked at " + position, Toast.LENGTH_SHORT).show();
                }

            }
        });
         adapter = new ListAdapterLabor(this, R.layout.rowlayout_tracking, R.id.txtPos, tubes, uobj, listImpl);
        listView.setAdapter(adapter);
    }

    private void receiveGatheredList(Object o) {
        // System.out.println(o.toString());
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
        final List<PrimerTube> tubes = (List<PrimerTube>) o;

         adapter = new ListAdapterLabor(this, R.layout.rowlayout_tracking, R.id.txtPos, tubes, uobj, listImpl);
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
        listImpl.requestGatheredPrimers(query,uobj.getUsername(),uobj.getPassword());
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;

    }
}
