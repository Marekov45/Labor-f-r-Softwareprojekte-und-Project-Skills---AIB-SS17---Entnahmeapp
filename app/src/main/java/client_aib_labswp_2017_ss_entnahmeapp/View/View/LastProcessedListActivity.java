package client_aib_labswp_2017_ss_entnahmeapp.View.View;

import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioGroup;
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

public class LastProcessedListActivity extends AppCompatActivity implements CustomObserver {

    private Button logoutButton;
    private Button showList;
    private User uobj;
    private ListView listView;
    private ListImpl listImpl;
    private RadioGroup listGroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_processed_list);

        uobj = getIntent().getParcelableExtra("USER");
        listImpl = new ListImpl();
        listImpl.setCObserver(this);

        listView = (ListView) findViewById(R.id.listvLastSanger);
        ViewGroup headerView = (ViewGroup) getLayoutInflater().inflate(R.layout.header_last_sanger, listView, false);
        listView.addHeaderView(headerView);

        listGroup = (RadioGroup) findViewById(R.id.listGroup);
        logoutButton = (Button) findViewById(R.id.btnLogout);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavUtils.navigateUpFromSameTask(LastProcessedListActivity.this);
            }
        });
        showList = (Button) findViewById(R.id.btnShowList);

        showList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                listImpl.requestLastSangerList(uobj.getUsername(), uobj.getPassword());
            }
        });

    }

    @Override
    public void onResponseSuccess(Object o, ResponseCode code) {
        switch (code) {
            case LASTSANGER:
                receiveLastSangerList(o);
                break;
        }
    }

    private void receiveLastSangerList(Object o) {
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
        List<PickList> pickLists = (List<PickList>) o;

        List<PrimerTube> tubes = new ArrayList<>();
        for (PickList pickList : pickLists) {
            tubes.addAll(pickList.getPickList());
        }

        ListAdapter adapter = new ListAdapter(this, R.layout.rowlayout_last_sanger, R.id.txtPos, tubes, pickLists, uobj, listImpl);
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
}
