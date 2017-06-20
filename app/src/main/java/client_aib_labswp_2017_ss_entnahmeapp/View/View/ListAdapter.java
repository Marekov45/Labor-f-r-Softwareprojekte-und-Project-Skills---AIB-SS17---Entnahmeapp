package client_aib_labswp_2017_ss_entnahmeapp.View.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import client.aib_labswp_2017_ss_entnahmeapp.R;
import client_aib_labswp_2017_ss_entnahmeapp.View.Controller.ServerAPI.ListImpl;
import client_aib_labswp_2017_ss_entnahmeapp.View.Controller.ServerAPI.PrimerImpl;
import client_aib_labswp_2017_ss_entnahmeapp.View.Model.User;
import client_aib_labswp_2017_ss_entnahmeapp.View.Model.model_List.PickList;
import client_aib_labswp_2017_ss_entnahmeapp.View.Model.model_List.PrimerTube;
import client_aib_labswp_2017_ss_entnahmeapp.View.Model.model_List.StorageLocation;
import client_aib_labswp_2017_ss_entnahmeapp.View.Model.test.DemoResponse;
import com.google.android.gms.vision.barcode.Barcode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by User on 11.06.2017.
 */
public class ListAdapter extends ArrayAdapter<PrimerTube> {
    private int vg;
    private List<PrimerTube> primerTubes;
    private List<PickList> pickLists;
    Context context;
    ListImpl listImpl;
    PrimerImpl primerImpl;
    User user;
    static final int MAXPOSITIONINCARRIER = 32;

    public ListAdapter(Context context, int vg, int id, List<PrimerTube> primerTubes, List<PickList> pickLists, User user, ListImpl listImpl, PrimerImpl primerImpl) {
        super(context, vg, id, primerTubes);
        this.context = context;
        this.primerTubes = primerTubes;
        this.pickLists = pickLists;
        this.vg = vg;
        this.user = user;
        this.listImpl = listImpl;
        this.primerImpl = primerImpl;
    }

    public void checkBarcodeWithPrimer(Barcode barcode) {

        for (PrimerTube primertube : primerTubes) {
            if (barcode.displayValue.equals(primertube.getPrimerTubeID())) {
                primerImpl.takePrimer(primertube.getObjectID(), user.getUsername(), user.getPassword());
                primertube.setTaken(true);
                notifyDataSetChanged();
            }
        }
        Toast.makeText(context, "Kein Primer mit dieser PrimerTubeID zu entnehmen.", Toast.LENGTH_SHORT).show();
    }

    public void changeRow(PrimerTube newTube, int positionForReplacement) {
        getItem(positionForReplacement - 1).setObjectID(newTube.getObjectID());
        getItem(positionForReplacement - 1).setTakeOutDate(newTube.getTakeOutDate());
        getItem(positionForReplacement - 1).setPutBackDate(newTube.getPutBackDate());
        getItem(positionForReplacement - 1).setPrimerTubeID(newTube.getPrimerTubeID());
        getItem(positionForReplacement - 1).setPrimerUID(newTube.getPrimerUID());
        getItem(positionForReplacement - 1).setName(newTube.getName());
        getItem(positionForReplacement - 1).setLotNr(newTube.getLotNr());
        getItem(positionForReplacement - 1).setManufacturer(newTube.getManufacturer());
        getItem(positionForReplacement - 1).setCurrentLocation(newTube.getCurrentLocation());
        getItem(positionForReplacement - 1).setStorageLocation(newTube.getStorageLocation());
        getItem(positionForReplacement - 1).setReturnToStorage(newTube.isReturnToStorage());
        newTube.setTaken(false);
        getItem(positionForReplacement-1).setTaken(false);
        notifyDataSetChanged();
    }

    //Hold views of the listView to improve its scrolling performance
    static class ViewHolder {
        public TextView txtPos;
        public TextView txtPrimer;
        public TextView txtStorageLocation;
        public TextView txtDestination;
        public Button manualScan;
        public TextView txtEntnommen;
        public CheckBox checkEntnommen;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder viewholder;
        if (convertView == null) {
            viewholder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(vg, parent, false);

            viewholder.txtPos = (TextView) convertView.findViewById(R.id.txtPos);
            viewholder.txtPrimer = (TextView) convertView.findViewById(R.id.txtPrimer);
            viewholder.txtStorageLocation = (TextView) convertView.findViewById(R.id.txtStorageLocation);
            viewholder.txtDestination = (TextView) convertView.findViewById(R.id.txtDestination);
            viewholder.checkEntnommen = (CheckBox) convertView.findViewById(R.id.checkEntnommen);

            convertView.setTag(viewholder);

        }

        final PrimerTube primerTube = primerTubes.get(position);
        final ViewHolder holder = (ViewHolder) convertView.getTag();


        holder.txtPos.setText(String.valueOf((position % 32) + 1));

        holder.txtPrimer.setText(primerTube.getName());
        holder.txtStorageLocation.setText(primerTube.getStorageLocation().toString());

        holder.manualScan = (Button) convertView.findViewById(R.id.btnTakePrimer);

        holder.manualScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(position);
                primerImpl.takePrimer(primerTubes.get(position).getObjectID(), user.getUsername(), user.getPassword());
                getItem(position).setTaken(true);
                notifyDataSetChanged();
            }
        });


        if (getItem(position).isTaken()) {
            holder.checkEntnommen.setChecked(true);
            holder.manualScan.setEnabled(false);
        } else {
            holder.checkEntnommen.setChecked(false);
            holder.manualScan.setEnabled(true);
        }

        PickList pickListFinal = null;
        int postitioncounter = position;
        for (PickList pickList : pickLists) {
            postitioncounter = postitioncounter - pickList.getPickList().size();
            if (postitioncounter < 0) {
                pickListFinal = pickList;
                break;
            }
        }
        holder.txtDestination.setText(pickListFinal.getDestination().getLocationName());

        return convertView;
    }


}
