package client_aib_labswp_2017_ss_entnahmeapp.View.View;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;
import android.os.Bundle;
import client.aib_labswp_2017_ss_entnahmeapp.R;
import client_aib_labswp_2017_ss_entnahmeapp.View.Controller.ServerAPI.CustomObserver;
import client_aib_labswp_2017_ss_entnahmeapp.View.Controller.ServerAPI.ListImpl;
import client_aib_labswp_2017_ss_entnahmeapp.View.Controller.enumResponseCode.ResponseCode;
import client_aib_labswp_2017_ss_entnahmeapp.View.Model.User;
import com.google.android.gms.vision.barcode.Barcode;

/**
 * Created by Marvin on 30.04.2017.
 */
public class PrimerList extends AppCompatActivity implements CustomObserver {

    private Spinner spinner;
    private TextView txtResult;
    private Button scanButton;
    private Button bListeAnzeigen;
    private String[] items = {"Entnommen", "Nicht Entnommen"};
    public static final int REQUEST_CODE = 100;
    public static final int PERMISSION_REQUEST = 200;
    private ListImpl listImpl;
    private RadioGroup listGroup;
    private RadioButton radioRoboter;
    private RadioButton radioManual;
    private RadioButton radioExtra;
    private RadioButton radioAllLists;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.primerlist);
        final User uobj = getIntent().getParcelableExtra("USER");

        listImpl = new ListImpl();
        listImpl.setCObserver(this);

        scanButton = (Button) findViewById(R.id.scan);
        bListeAnzeigen = (Button) findViewById(R.id.bListeAnzeigen);
        txtResult = (TextView) findViewById(R.id.txtResult);
        listGroup = (RadioGroup) findViewById(R.id.listGroup);

        radioRoboter = (RadioButton) findViewById(R.id.radioRoboter);
        radioManual = (RadioButton) findViewById(R.id.radioManuell);
        radioExtra = (RadioButton) findViewById(R.id.radioExtra);
        radioAllLists = (RadioButton) findViewById(R.id.radioAll);


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST);
        }
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PrimerList.this, ScanActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        bListeAnzeigen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                listImpl.requestList(uobj.getUsername(), uobj.getPassword(), chooseList());

            }
        });

        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(PrimerList.this,
                android.R.layout.simple_spinner_item, items);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private String chooseList() {

        int selectedID = listGroup.getCheckedRadioButtonId();

        switch (selectedID) {
            case R.id.radioRoboter:
                    return "S";
            case R.id.radioManuell:
                    return "M";
            case R.id.radioExtra:
                    return "E";
            case R.id.radioAll:
                return "A";
        }
        return null;
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
    public void onResponseSuccess(Object o, ResponseCode code) {
        switch (code) {
            case LIST:
                receivePrimerList();
                break;
        }
    }

    private void receivePrimerList() {
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






