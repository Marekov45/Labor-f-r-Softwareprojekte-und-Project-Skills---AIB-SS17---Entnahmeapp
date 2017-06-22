package client_aib_labswp_2017_ss_entnahmeapp.View.view.guis;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Parcelable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.os.Bundle;
import client.aib_labswp_2017_ss_entnahmeapp.R;
import client_aib_labswp_2017_ss_entnahmeapp.View.controller.serverAPI.CustomObserver;
import client_aib_labswp_2017_ss_entnahmeapp.View.controller.serverAPI.ListImpl;
import client_aib_labswp_2017_ss_entnahmeapp.View.controller.serverAPI.PrimerImpl;
import client_aib_labswp_2017_ss_entnahmeapp.View.controller.enumResponseCode.ResponseCode;
import client_aib_labswp_2017_ss_entnahmeapp.View.model.User;
import client_aib_labswp_2017_ss_entnahmeapp.View.model.model_List.PickList;
import client_aib_labswp_2017_ss_entnahmeapp.View.model.model_List.PrimerTube;
import client_aib_labswp_2017_ss_entnahmeapp.View.view.popup.PopPicklist;
import client_aib_labswp_2017_ss_entnahmeapp.View.view.scan.ScanActivity;
import client_aib_labswp_2017_ss_entnahmeapp.View.view.adapter.ListAdapterPicklist;
import com.google.android.gms.vision.barcode.Barcode;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class PickListActivity extends AppCompatActivity implements CustomObserver {


    private TextView txtResult;
    private Button scanButton;
    private Button logoutButton;
    private Button bListeAnzeigen;
    public static final int REQUEST_CODE = 100;
    public static final int PERMISSION_REQUEST = 200;
    public static final int REQUEST_POPUP=300;
    private ListImpl listImpl;
    private PrimerImpl primerImpl;
    private RadioGroup listGroup;
    private User uobj;
    private ListView listView;
    ListAdapterPicklist adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picklist);
        uobj = getIntent().getParcelableExtra("USER");

        listView = (ListView) findViewById(R.id.listv);
        ViewGroup headerView = (ViewGroup) getLayoutInflater().inflate(R.layout.header, listView, false);
        listView.addHeaderView(headerView);

        listImpl = new ListImpl();
        listImpl.setCObserver(this);
        primerImpl = new PrimerImpl();
        primerImpl.setCObserver(this);

        scanButton = (Button) findViewById(R.id.scan);
        bListeAnzeigen = (Button) findViewById(R.id.bListeAnzeigen);

        listGroup = (RadioGroup) findViewById(R.id.listGroup);
        logoutButton = (Button) findViewById(R.id.btnLogout);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavUtils.navigateUpFromSameTask(PickListActivity.this);
            }
        });

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST);
        }
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PickListActivity.this, ScanActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });


        bListeAnzeigen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (chooseList().equals("A")) {
                    listImpl.requestAllLists(uobj.getUsername(), uobj.getPassword());
                } else {
                    listImpl.requestList(uobj.getUsername(), uobj.getPassword(), chooseList());

                }
            }
        });
    };

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
                System.out.println(barcode.displayValue);
                adapter.checkBarcodeWithPrimer(barcode);
//                txtResult.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        txtResult.setText(barcode.displayValue);
//                    }
//                });
            }
        }
        if(requestCode==REQUEST_POPUP){
            if(resultCode== Activity.RESULT_OK){
                PrimerTube tubeNew= data.getParcelableExtra("NEWTUBE");
                int positionForReplacement = data.getIntExtra("POSITION",0);
                adapter.changeRow(tubeNew, positionForReplacement);
//                listView.getChildAt(positionForReplacement).setBackgroundColor(Color.RED);
//                System.out.println("good");
            }else {
//                System.out.println("tube ist null");
            }
        }
    }

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
        Toast.makeText(this, "Primer has been taken", Toast.LENGTH_SHORT).show();

    }

    private void receivePrimerList(Object o) {
//        System.out.println(o.toString());
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
        final List<PickList> pickLists = (List<PickList>) o;

        final List<PrimerTube> tubes = new ArrayList<>();
        for (PickList pickList : pickLists) {
            tubes.addAll(pickList.getPickList());
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (id != -1) {
                    PrimerTube actualTube = tubes.get(position-1);
                    Intent intentPopUp = new Intent(PickListActivity.this, PopPicklist.class);
                    intentPopUp.putExtra("TUBE", (Parcelable) actualTube);
                    intentPopUp.putExtra("POSITION",position);
                    intentPopUp.putExtra("USER",uobj);
                    startActivityForResult(intentPopUp, REQUEST_POPUP);

                    Toast.makeText(PickListActivity.this, "List Item was clicked at " + position, Toast.LENGTH_SHORT).show();
                }

            }
        });

        adapter = new ListAdapterPicklist(this, R.layout.rowlayout_picklist, R.id.txtPos, tubes, pickLists, uobj, listImpl, primerImpl);
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
