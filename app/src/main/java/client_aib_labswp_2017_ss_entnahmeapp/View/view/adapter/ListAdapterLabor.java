package client_aib_labswp_2017_ss_entnahmeapp.View.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import client.aib_labswp_2017_ss_entnahmeapp.R;
import client_aib_labswp_2017_ss_entnahmeapp.View.controller.serverAPI.ListImpl;
import client_aib_labswp_2017_ss_entnahmeapp.View.model.NewLocation;
import client_aib_labswp_2017_ss_entnahmeapp.View.model.User;
import client_aib_labswp_2017_ss_entnahmeapp.View.model.model_List.PrimerTube;

import java.util.List;

/**
 * Created by Marvin on 17.06.2017.
 */
public class ListAdapterLabor extends ArrayAdapter<PrimerTube> implements Filterable {
    private int vg;
    private List<PrimerTube> primerTubes;
//    private List<PrimerTube> filteredTubes;
    Context context;
    ListImpl listImpl;
    User user;


    public ListAdapterLabor(Context context, int vg, int id, List<PrimerTube> primerTubes, User user, ListImpl listImpl) {
        super(context, vg, id, primerTubes);

        this.context = context;
        this.primerTubes = primerTubes;
        this.vg = vg;
        this.user = user;
        this.listImpl = listImpl;
    }


    static class ViewHolder {
        public TextView txtPrimer;
        public TextView txtPrimerTube;
        public TextView txtLOT;
        public TextView txtLocation;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewholder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.rowlayout_tracking, parent, false);
            viewholder = new ViewHolder();
            viewholder.txtPrimer = (TextView) convertView.findViewById(R.id.primerTxtView);
            viewholder.txtPrimerTube = (TextView) convertView.findViewById(R.id.primerTubeTxtView);
            viewholder.txtLOT = (TextView) convertView.findViewById(R.id.lotTxtView);
            viewholder.txtLocation = (TextView) convertView.findViewById(R.id.locationTxtView);
            convertView.setTag(viewholder);

        }

        //  final PrimerTube primerTube = primerTubes.get(position);
        else {
            viewholder = (ViewHolder) convertView.getTag();
        }
        viewholder.txtPrimer.setText(primerTubes.get(position).getName());
        viewholder.txtPrimerTube.setText(primerTubes.get(position).getPrimerTubeID());
        viewholder.txtLOT.setText(primerTubes.get(position).getLotNr());

        viewholder.txtLocation.setText(primerTubes.get(position).getCurrentLocation());


        return convertView;
    }

    public void changeRow(PrimerTube newTube, int positionForReplacement, NewLocation newLocation) {
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

        notifyDataSetChanged();
    }

    public void changeCurrentLocation(PrimerTube tube, int positionForNewLocation, NewLocation newlocation) {
        getItem(positionForNewLocation - 1).setObjectID(tube.getObjectID());
        getItem(positionForNewLocation - 1).setTakeOutDate(tube.getTakeOutDate());
        getItem(positionForNewLocation - 1).setPutBackDate(tube.getPutBackDate());
        getItem(positionForNewLocation - 1).setPrimerTubeID(tube.getPrimerTubeID());
        getItem(positionForNewLocation - 1).setPrimerUID(tube.getPrimerUID());
        getItem(positionForNewLocation - 1).setName(tube.getName());
        getItem(positionForNewLocation - 1).setLotNr(tube.getLotNr());
        getItem(positionForNewLocation - 1).setManufacturer(tube.getManufacturer());
        getItem(positionForNewLocation - 1).setCurrentLocation(newlocation.getNewLocation());
        getItem(positionForNewLocation - 1).setStorageLocation(tube.getStorageLocation());
        getItem(positionForNewLocation - 1).setReturnToStorage(tube.isReturnToStorage());
        notifyDataSetChanged();
    }

}
