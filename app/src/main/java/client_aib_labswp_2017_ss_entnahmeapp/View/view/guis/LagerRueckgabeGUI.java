package client_aib_labswp_2017_ss_entnahmeapp.View.view.guis;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Parcelable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import client.aib_labswp_2017_ss_entnahmeapp.R;
import client_aib_labswp_2017_ss_entnahmeapp.View.controller.serverAPI.CustomObserver;
import client_aib_labswp_2017_ss_entnahmeapp.View.controller.serverAPI.ListImpl;
import client_aib_labswp_2017_ss_entnahmeapp.View.controller.serverAPI.PrimerImpl;
import client_aib_labswp_2017_ss_entnahmeapp.View.controller.enumResponseCode.ResponseCode;
import client_aib_labswp_2017_ss_entnahmeapp.View.model.User;
import client_aib_labswp_2017_ss_entnahmeapp.View.model.model_List.PrimerTube;
import client_aib_labswp_2017_ss_entnahmeapp.View.view.popup.PopReturn;
import client_aib_labswp_2017_ss_entnahmeapp.View.view.scan.ScanActivity;
import client_aib_labswp_2017_ss_entnahmeapp.View.view.adapter.ListAdapterGatheredPrimer;
import com.google.android.gms.vision.barcode.Barcode;

import java.util.List;

public class LagerRueckgabeGUI extends AppCompatActivity implements CustomObserver {

    private TextView txtResult;
    private Button scanButton;
    private Button logoutReturn;
    private Button showGatheredPrimer;
    private ListView listView;
    private ListImpl listImpl;
    private PrimerImpl primerImpl;
    public static final int REQUEST_CODE = 100;
    public static final int PERMISSION_REQUEST = 200;
    public static final int REQUEST_POPUP = 300;
    private User uobj;
    private ListAdapterGatheredPrimer adapter;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lager_rueckgabe_gui);

        context = this;
        uobj = getIntent().getParcelableExtra("USER");
        listImpl = new ListImpl();
        listImpl.setCObserver(this);
        primerImpl = new PrimerImpl();
        primerImpl.setCObserver(this);

        listView = (ListView) findViewById(R.id.listvGatheredPrimer);
        ViewGroup headerView = (ViewGroup) getLayoutInflater().inflate(R.layout.header_gathered_primer, listView, false);
        listView.addHeaderView(headerView);

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                final Barcode barcode = data.getParcelableExtra("barcode");
                System.out.println(barcode.displayValue);
                adapter.checkBarcodeWithPrimer(barcode, listView);

            }
        }
        if (requestCode==REQUEST_POPUP&&resultCode==RESULT_OK){
            if(data!=null){
                PrimerTube tubeToRemove= data.getParcelableExtra("TUBEREMOVED");
                int positionForReplacement = data.getIntExtra("POSITION",0);
                adapter.setPrimerOnTakenIfRemovedManually(tubeToRemove, positionForReplacement);
            }
        }
    }

    @Override
    public void onResponseSuccess(Object o, ResponseCode code) {
        switch (code) {
            case COMPLETEGATHEREDLIST:
                receiveGatheredPrimerList(o);
                break;
            case RETURNPRIMER:
                returnPrimer(o);
                break;
        }
    }

    private void returnPrimer(Object o) {
        Toast.makeText(this, "Primer zurückgelegt", Toast.LENGTH_SHORT).show();

    }

    private void receiveGatheredPrimerList(Object o) {
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
        final List<PrimerTube> tubes = (List<PrimerTube>) o;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (id != -1) {
                    PrimerTube actualTube = tubes.get(position - 1);
                    Intent intentPopUp = new Intent(LagerRueckgabeGUI.this, PopReturn.class);
                    intentPopUp.putExtra("TUBE", (Parcelable) actualTube);
                    intentPopUp.putExtra("POSITION", position);
                    intentPopUp.putExtra("USER", uobj);
                    startActivityForResult(intentPopUp, REQUEST_POPUP);
                }

            }
        });


        adapter = new ListAdapterGatheredPrimer(this, R.layout.rowlayout_gathered_primer, R.id.txtPrimerLastGathered, tubes, uobj, listImpl, primerImpl);
        listView.setAdapter(adapter);
    }


    @Override
    public void onResponseError(Object o, ResponseCode code) {
        Toast.makeText(this, "ResponseError", Toast.LENGTH_SHORT).show();
        switch (code){
            case RETURNPRIMER:
                int position = (int) o;
                adapter.changeReturnStatus(position, false);
                break;
        }
    }

    @Override
    public void onResponseFailure() {
        Toast.makeText(this, "Failure", Toast.LENGTH_SHORT).show();
    }
}
