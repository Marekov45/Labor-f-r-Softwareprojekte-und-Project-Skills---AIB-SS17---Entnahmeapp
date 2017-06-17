package client_aib_labswp_2017_ss_entnahmeapp.View.View;

import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;
import client.aib_labswp_2017_ss_entnahmeapp.R;
import client_aib_labswp_2017_ss_entnahmeapp.View.Controller.ServerAPI.CustomObserver;
import client_aib_labswp_2017_ss_entnahmeapp.View.Controller.ServerAPI.ListImpl;
import client_aib_labswp_2017_ss_entnahmeapp.View.Controller.enumResponseCode.ResponseCode;
import client_aib_labswp_2017_ss_entnahmeapp.View.Model.User;
import client_aib_labswp_2017_ss_entnahmeapp.View.Model.model_List.PickList;
import client_aib_labswp_2017_ss_entnahmeapp.View.Model.model_List.PrimerTube;

import java.util.ArrayList;
import java.util.List;

public class LaborGui extends AppCompatActivity implements CustomObserver, SearchView.OnQueryTextListener {


    private Button logoutButton;
    private SearchView view;
    private ListImpl listImpl;
    private User uobj;
    private ListView listView;


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
        listView.setTextFilterEnabled(true);
        setupSearchView();
    }

    private void setupSearchView() {
        view.setIconifiedByDefault(false);
        view.setOnQueryTextListener(this);
        view.setSubmitButtonEnabled(true);
        view.setQueryHint("Primername");
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
        List<PrimerTube> tubes = (List<PrimerTube>) o;
        ListAdapterLabor adapter = new ListAdapterLabor(this, R.layout.rowlayout_tracking, R.id.txtPos, tubes, uobj, listImpl);
        listView.setAdapter(adapter);
    }

    private void receiveGatheredList(Object o) {
        System.out.println(o.toString());

        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();

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
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (TextUtils.isEmpty(newText)) {
            listView.clearTextFilter();
        } else {
            listView.setFilterText(newText);
        }
        return true;
    }
}
