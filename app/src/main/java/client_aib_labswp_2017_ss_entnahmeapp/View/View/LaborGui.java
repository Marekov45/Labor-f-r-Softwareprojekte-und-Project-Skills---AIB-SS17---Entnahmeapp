package client_aib_labswp_2017_ss_entnahmeapp.View.View;

import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;
import client.aib_labswp_2017_ss_entnahmeapp.R;
import client_aib_labswp_2017_ss_entnahmeapp.View.Controller.ServerAPI.CustomObserver;
import client_aib_labswp_2017_ss_entnahmeapp.View.Controller.ServerAPI.ListImpl;
import client_aib_labswp_2017_ss_entnahmeapp.View.Controller.enumResponseCode.ResponseCode;
import client_aib_labswp_2017_ss_entnahmeapp.View.Model.User;

public class LaborGui extends AppCompatActivity implements CustomObserver {


    private Button logoutButton;
    private SearchView view;
    private ListImpl listImpl;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_labor_gui);
        final User uobj = getIntent().getParcelableExtra("USER");
        listImpl = new ListImpl();
        listImpl.setCObserver(this);
        view = (SearchView) findViewById(R.id.search);
        view.setQueryHint("Primername/Primerart");
        logoutButton = (Button) findViewById(R.id.logout);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavUtils.navigateUpFromSameTask(LaborGui.this);            }

        });


       // listImpl.requestAllGatheredPrimers(uobj.getUsername(),uobj.getPassword());
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
        System.out.println(o.toString());

        Toast.makeText(this, "SuccessALLGATHEREDPRIMERS", Toast.LENGTH_SHORT).show();
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
}
