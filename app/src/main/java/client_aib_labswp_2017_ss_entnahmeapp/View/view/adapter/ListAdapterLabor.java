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
 * {@link ListAdapterLabor} converts a list of {@link PrimerTube} objects into {@link View} items loaded into the {@link ListView} container.
 */
public class ListAdapterLabor extends ArrayAdapter<PrimerTube> {
    private int vg;
    private List<PrimerTube> primerTubes;
    private Context context;
    private ListImpl listImpl;
    private User user;

    /**
     * @param context     context of the current state of the application. It must not be {@code null}.
     * @param vg          row layout for a {@link View} item. it must not be {@code null}.
     * @param id          position of the view item
     * @param primerTubes the list of {@link PrimerTube} objects loaded into the {@link ListView}.It might be empty.
     * @param user        user that logged on to the application. It must not be {@code null}.
     * @param listImpl    implementation of all rest requests regarding lists. It must not be {@code null}.
     */
    public ListAdapterLabor(Context context, int vg, int id, List<PrimerTube> primerTubes, User user, ListImpl listImpl) {
        super(context, vg, id, primerTubes);

        this.context = context;
        this.primerTubes = primerTubes;
        this.vg = vg;
        this.user = user;
        this.listImpl = listImpl;
    }


    /**
     * Stores each of the component views inside the tag field of the layout, so they can be immediately accessed
     * without the need to look them up repeatedly. Improves the scrolling performance of the {@link ListView}.
     */
    static class ViewHolder {
        public TextView txtPrimer;
        public TextView txtPrimerTube;
        public TextView txtLOT;
        public TextView txtLocation;

    }

    /**
     * Populates the {@link ViewHolder} and stores it inside the layout.
     *
     * @param position    the position of the item within the adapter's data set of the item whose view we want
     * @param convertView the old {@link View} to reuse, if possible
     * @param parent      the parent that this view will eventually be attached to
     * @return a {@link View} corresponding to the data at the specified position
     */
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

        } else {
            viewholder = (ViewHolder) convertView.getTag();
        }
        viewholder.txtPrimer.setText(primerTubes.get(position).getName());
        viewholder.txtPrimerTube.setText(primerTubes.get(position).getPrimerTubeID());
        viewholder.txtLOT.setText(primerTubes.get(position).getLotNr());

        viewholder.txtLocation.setText(primerTubes.get(position).getCurrentLocation());


        return convertView;
    }

    /**
     * Gets the data item associated with the specified position in the data set.
     * The data of the item is changed, after a new {@link PrimerTube} has been requested.
     *
     * @param newTube                the {@link PrimerTube} that replaces the old one. The replacement can be {@code null},
     *                               if there is no {@link PrimerTube} left.
     * @param positionForReplacement position of the {@link PrimerTube} in the {@link ListView} that is replaced.
     * @param newLocation            the new location of the {@link PrimerTube}. Only relevant, if a new location
     *                               has been requested.
     */
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

    /**
     * Gets the data item associated with the specified position in the data set.
     * The data of the item is changed, after a new location for the {@link PrimerTube} has been requested.
     *
     * @param tube                   the {@link PrimerTube} whose location is about to be changed. It must not be an empty location.
     * @param positionForNewLocation position of the {@link PrimerTube} in the {@link ListView} that has
     *                               his location changed.
     * @param newlocation            the new location of the {@link PrimerTube}.
     */
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
