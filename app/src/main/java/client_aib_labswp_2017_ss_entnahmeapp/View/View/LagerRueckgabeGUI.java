package client_aib_labswp_2017_ss_entnahmeapp.View.View;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import client.aib_labswp_2017_ss_entnahmeapp.R;
import client_aib_labswp_2017_ss_entnahmeapp.View.Controller.ServerAPI.CustomObserver;
import client_aib_labswp_2017_ss_entnahmeapp.View.Controller.ServerAPI.ListImpl;
import client_aib_labswp_2017_ss_entnahmeapp.View.Controller.enumResponseCode.ResponseCode;
import client_aib_labswp_2017_ss_entnahmeapp.View.Model.User;
import client_aib_labswp_2017_ss_entnahmeapp.View.Model.model_List.PickList;
import client_aib_labswp_2017_ss_entnahmeapp.View.Model.model_List.PrimerTube;
import com.google.android.gms.vision.barcode.Barcode;

import java.util.ArrayList;
import java.util.List;

public class LagerRueckgabeGUI extends AppCompatActivity implements CustomObserver {

    private TextView txtResult;
    private Button scanButton;
    private Button logoutReturn;
    private Button showGatheredPrimer;
    private ListView listView;
    private ListImpl listImpl;
    public static final int REQUEST_CODE = 100;
    public static final int PERMISSION_REQUEST = 200;
    User uobj;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lager_rueckgabe_gui);

        uobj = getIntent().getParcelableExtra("USER");
        listImpl = new ListImpl();
        listImpl.setCObserver(this);

        listView = (ListView) findViewById(R.id.listvGatheredPrimer);
        ViewGroup headerView = (ViewGroup) getLayoutInflater().inflate(R.layout.header_gathered_primer, listView, false);
        listView.addHeaderView(headerView);

        //RadioGroup listGroup = (RadioGroup) findViewById(R.id.listGroup);
        logoutReturn = (Button) findViewById(R.id.btn_logoutReturn);
        logoutReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavUtils.navigateUpFromSameTask(LagerRueckgabeGUI.this);
            }
        });

        showGatheredPrimer = (Button) findViewById(R.id.btn_gatheredPrimer);
        showGatheredPrimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listImpl.requestAllGatheredPrimers(uobj.getUsername(), uobj.getPassword());
            }
        });

        scanButton = (Button) findViewById(R.id.scanReturn);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST);
        }
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LagerRueckgabeGUI.this, ScanActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }

    @Override
    public void onResponseSuccess(Object o, ResponseCode code) {
        switch (code) {
            case COMPLETEGATHEREDLIST:
                receiveGatheredPrimerList(o);
                break;
        }
    }


    private void receiveGatheredPrimerList(Object o) {
        System.out.println(o.toString());
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();

        List<PrimerTube> tubes = (List<PrimerTube>) o;

//        final List<PrimerTube> gatheredtubes = new ArrayList<>();
//        gatheredtubes.addAll(tubes);


        ListAdapterGatheredPrimer adapter = new ListAdapterGatheredPrimer(this, R.layout.rowlayout_gathered_primer, R.id.txtPrimer, tubes, uobj, listImpl);
        listView.setAdapter(adapter);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                final Barcode barcode = data.getParcelableExtra("barcode");
                txtResult.post(new Runnable() {
                    @Override
                    public void run() {
                        txtResult.setText(barcode.displayValue);
                    }
                });
            }
        }
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
