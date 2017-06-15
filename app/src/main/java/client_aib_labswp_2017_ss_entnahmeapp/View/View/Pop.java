package client_aib_labswp_2017_ss_entnahmeapp.View.View;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.widget.TextView;
import client.aib_labswp_2017_ss_entnahmeapp.R;
import client_aib_labswp_2017_ss_entnahmeapp.View.Model.TubesArray;
import client_aib_labswp_2017_ss_entnahmeapp.View.Model.model_List.PrimerTube;

/**
 * Created by User on 11.06.2017.
 */
public class Pop extends AppCompatActivity{

    private TextView primerName;
    private TextView shownName;
    private TubesArray tubesArray;
    private long position;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popupwindow);

//        tubesArray = getIntent().getParcelableExtra("TUBES");
        position = getIntent().getParcelableExtra("POSITION");


        primerName=(TextView) findViewById(R.id.textView4);
        shownName=(TextView) findViewById(R.id.textView5);
//        shownName.setText(tubesArray.getPrimertubes().get(position).getName());
System.out.println(position);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width*.8), (int) (height*.6));



    }
}
