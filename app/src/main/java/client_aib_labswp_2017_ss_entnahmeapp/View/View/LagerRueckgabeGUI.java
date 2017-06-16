package client_aib_labswp_2017_ss_entnahmeapp.View.View;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
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

public class LagerRueckgabeGUI extends AppCompatActivity {

    private TextView txtResult;
    private Button scanButton;
    private Button logoutButton;
    private Button entnommene_anzeigen;
    public static final int REQUEST_CODE = 100;
    public static final int PERMISSION_REQUEST = 200;
    private ListImpl listImpl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lager_rueckgabe_gui);
        final User uobj = getIntent().getParcelableExtra("USER");
        logoutButton = (Button) findViewById(R.id.logout);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(R.layout.activity_login);
                Toast.makeText(LagerRueckgabeGUI.this, "Erfolgreich ausgeloggt", Toast.LENGTH_SHORT).show();
            }
        });


        scanButton = (Button) findViewById(R.id.scan);
        entnommene_anzeigen = (Button) findViewById(R.id.entnommene_anzeigen);

/**
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

        entnommene_anzeigen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listImpl.requestAllGatheredPrimers(uobj.getUsername(), uobj.getPassword());
            }
        });**/


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

}
/**
    @Override
    public void onResponseSuccess(Object o, ResponseCode code) {
        switch (code) {
            case LIST:
            case COMPLETELIST:
                receivePrimerList(o);
                break;
            case TAKEPRIMER:
                takePrimer(o);
                break;
        }
    }

    private void takePrimer(Object o) {
    }

    private void receivePrimerList(Object o) {
        System.out.println(o.toString());
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
        List<PickList> pickLists = (List<PickList>) o;

        List<PrimerTube> tubes = new ArrayList<>();
        for(PickList pickList: pickLists){
            tubes.addAll(pickList.getPickList());
        }

        ListAdapter adapter = new ListAdapter(this, R.layout.rowlayout, R.id.txtPos, tubes, pickLists);
        listView.setAdapter(adapter);

    }

    @Override
    public void onResponseError() {
        Toast.makeText(this, "ResponseError", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponseFailure() {
        Toast.makeText(this, "Failure", Toast.LENGTH_SHORT).show();**/