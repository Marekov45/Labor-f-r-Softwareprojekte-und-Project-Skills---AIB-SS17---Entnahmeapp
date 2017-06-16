package client_aib_labswp_2017_ss_entnahmeapp.View.View;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.widget.Button;
import android.widget.TextView;
import client.aib_labswp_2017_ss_entnahmeapp.R;
import client_aib_labswp_2017_ss_entnahmeapp.View.Model.User;
import client_aib_labswp_2017_ss_entnahmeapp.View.Model.model_List.PrimerTube;
import org.w3c.dom.Text;

/**
 * Created by User on 11.06.2017.
 */
public class Pop extends AppCompatActivity{

    private TextView primerName;
    private TextView shownName;
    private TextView primerID;
    private TextView shownID;
    private TextView primerLOT;
    private TextView shownLOT;
    private TextView primerManufacturer;
    private TextView shownManufacturer;
    private TextView primerNote;
    private TextView shownNote;

    private Button submit;

    private User uobj;
    private int positionGiven;
    private PrimerTube tube;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popupwindow);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width*.8), (int) (height*.6));



        primerName=(TextView) findViewById(R.id.tvPrimerName);
        shownName=(TextView) findViewById(R.id.tvNameActTube);
        primerID=(TextView) findViewById(R.id.tvPrimerTubeID);
        shownID = (TextView) findViewById(R.id.tvIDActTube);
        primerLOT=(TextView) findViewById(R.id.tvLOT);
        shownLOT=(TextView) findViewById(R.id.tvLOTActTube);
        primerManufacturer=(TextView) findViewById(R.id.tvManufacturerActTube);
        shownManufacturer=(TextView) findViewById(R.id.tvManufacturerActTube);
        primerNote=(TextView) findViewById(R.id.tvNote);
        shownNote=(TextView) findViewById(R.id.tvNoteActTube);
        submit=(Button) findViewById(R.id.btnSubmit);

        uobj = getIntent().getParcelableExtra("USER");
        tube = getIntent().getParcelableExtra("TUBE");
        positionGiven = getIntent().getIntExtra("POSITION", 0);
        int position = positionGiven -1;


        shownName.setText(tube.getName());
        shownID.setText(tube.getPrimerTubeID());
        shownLOT.setText(tube.getLotNr());
        shownManufacturer.setText(tube.getManufacturer());




    }
}
