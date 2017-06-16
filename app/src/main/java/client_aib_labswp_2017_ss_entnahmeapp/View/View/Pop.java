package client_aib_labswp_2017_ss_entnahmeapp.View.View;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.widget.TextView;
import client.aib_labswp_2017_ss_entnahmeapp.R;
import client_aib_labswp_2017_ss_entnahmeapp.View.Model.model_List.PrimerTube;

/**
 * Created by User on 11.06.2017.
 */
public class Pop extends AppCompatActivity{

    private TextView primerName;
    private TextView shownName;
    private int positionGiven;
    private PrimerTube tube;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popupwindow);

        tube = getIntent().getParcelableExtra("TUBE");
        positionGiven = getIntent().getIntExtra("POSITION", 0);
        int position = positionGiven -1;
        System.out.println(position);


        primerName=(TextView) findViewById(R.id.textView4);
        shownName=(TextView) findViewById(R.id.textView5);
        shownName.setText(tube.getName());

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width*.8), (int) (height*.6));



    }
}
