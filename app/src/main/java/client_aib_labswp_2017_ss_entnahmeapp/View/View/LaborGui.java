package client_aib_labswp_2017_ss_entnahmeapp.View.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import client.aib_labswp_2017_ss_entnahmeapp.R;

public class LaborGui extends AppCompatActivity {


    private Button logoutButton;
    private SearchView view;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_labor_gui);

        view = (SearchView) findViewById(R.id.search);
        view.setQueryHint("Primername/Primerart");
        logoutButton = (Button) findViewById(R.id.logout);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
