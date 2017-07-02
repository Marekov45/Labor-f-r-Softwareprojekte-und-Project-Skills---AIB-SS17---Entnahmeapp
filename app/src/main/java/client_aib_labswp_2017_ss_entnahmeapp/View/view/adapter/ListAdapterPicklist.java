package client_aib_labswp_2017_ss_entnahmeapp.View.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import client.aib_labswp_2017_ss_entnahmeapp.R;
import client_aib_labswp_2017_ss_entnahmeapp.View.controller.serverAPI.ListImpl;
import client_aib_labswp_2017_ss_entnahmeapp.View.controller.serverAPI.PrimerImpl;
import client_aib_labswp_2017_ss_entnahmeapp.View.model.User;
import client_aib_labswp_2017_ss_entnahmeapp.View.model.model_List.PickList;
import client_aib_labswp_2017_ss_entnahmeapp.View.model.model_List.PrimerTube;
import com.google.android.gms.vision.barcode.Barcode;

import java.util.List;

/**
 * {@link ListAdapterPicklist} converts a list of {@link PrimerTube} objects into {@link View} items loaded into the {@link ListView} container.
 */
public class ListAdapterPicklist extends ArrayAdapter<PrimerTube> {
    private int vg;
    private List<PrimerTube> primerTubes;
    private List<PickList> pickLists;
    private Context context;
    private ListImpl listImpl;
    private PrimerImpl primerImpl;
    private User user;
    static final int MAXPOSITIONINCARRIER = 32;

    /**
     * @param context     context of the current state of the application. It must not be {@code null}.
     * @param vg          row layout for a {@link View} item. it must not be {@code null}.
     * @param id          position of the view item
     * @param primerTubes the list of {@link PrimerTube} objects loaded into the {@link ListView}.It might be empty.
     * @param pickLists   the list of {@link PickList} objects.It might be empty.
     * @param user        user that logged on to the application. It must not be {@code null}.
     * @param listImpl    implementation of all rest requests regarding lists. It must not be {@code null}.
     * @param primerImpl  implementation of all rest requests regarding primers. It must not be {@code null}.
     */
    public ListAdapterPicklist(Context context, int vg, int id, List<PrimerTube> primerTubes, List<PickList> pickLists, User user, ListImpl listImpl, PrimerImpl primerImpl) {
        super(context, vg, id, primerTubes);
        this.context = context;
        this.primerTubes = primerTubes;
        this.pickLists = pickLists;
        this.vg = vg;
        this.user = user;
        this.listImpl = listImpl;
        this.primerImpl = primerImpl;
    }

    /**
     * Checks for every {@link PrimerTube} object in the {@link ListView} if the barcode matches
     * with a {@link PrimerTube} ID. If the values are the same, the fitting {@link PrimerTube} is taken
     * from the storage.
     *
     * @param barcode the barcode that was scanned by the camera of the device
     */
    public void checkBarcodeWithPrimer(Barcode barcode) {

        for (PrimerTube primertube : primerTubes) {
            if (barcode.displayValue.equals(primertube.getPrimerTubeID())) {
                primerImpl.takePrimer(primertube.getObjectID(), getPosition(primertube), user.getUsername(), user.getPassword());
                primertube.setTaken(true);
                notifyDataSetChanged();
                return;
            }
        }
        Toast.makeText(context, R.string.withdrawalMessage, Toast.LENGTH_SHORT).show();
    }

    /**
     * Gets the data item associated with the specified position in the data set.
     * The data of the item is changed, after a new {@link PrimerTube} has been requested.
     *
     * @param newTube                the {@link PrimerTube} that replaces the old one. The replacement can be {@code null},
     *                               if there is no {@link PrimerTube} left.
     * @param positionForReplacement position of the {@link PrimerTube} in the {@link ListView} that is replaced.
     */
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
        getItem(positionForReplacement - 1).setTaken(false);
        notifyDataSetChanged();
    }

    /**
     * Stores each of the component views inside the tag field of the layout, so they can be immediately accessed
     * without the need to look them up repeatedly. Improves the scrolling performance of the {@link ListView}.
     */
    static class ViewHolder {
        public TextView txtPos;
        public TextView txtPrimer;
        public TextView txtStorageLocation;
        public TextView txtDestination;
        public Button manualScan;
        public CheckBox checkEntnommen;
    }

    /**
     * Populates the {@link ViewHolder} and stores it inside the layout.
     *
     * @param position    the position of the item within the adapter's data set of the item whose view we want
     * @param convertView the old {@link View} to reuse, if possible
     * @param parent      the parent that this view will eventually be attached to
     * @return a {@link View} corresponding to the data at the specified position
     */
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


        holder.txtPos.setText(String.valueOf((position % MAXPOSITIONINCARRIER) + 1));

        holder.txtPrimer.setText(primerTube.getName());
        holder.txtStorageLocation.setText(primerTube.getStorageLocation().toString());

        holder.manualScan = (Button) convertView.findViewById(R.id.btnTakePrimer);

        //primers can be taken manually with the click of a button if the scanner does not work
        holder.manualScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(position);
                primerImpl.takePrimer(primerTubes.get(position).getObjectID(), position, user.getUsername(), user.getPassword());
                getItem(position).setTaken(true);
                notifyDataSetChanged();
            }
        });


        if (getItem(position).isTaken() || getItem(position).getTakeOutDate() != null) {
            holder.checkEntnommen.setChecked(true);
            holder.manualScan.setEnabled(false);
        } else {
            holder.checkEntnommen.setChecked(false);
            holder.manualScan.setEnabled(true);
        }

        PickList pickListFinal = null;
        int positioncounter = position;
        for (PickList pickList : pickLists) {
            positioncounter = positioncounter - pickList.getPickList().size();
            if (positioncounter < 0) {
                pickListFinal = pickList;
                break;
            }
        }
        holder.txtDestination.setText(pickListFinal.getDestination().getLocationName());

        return convertView;
    }

    /**
     * Updates the status  if an error occured during the withdrawal of a primer.
     *
     * @param position position of the primer in the listview
     * @param status   tells if a primer has been taken
     */
    public void updateTakenStatus(int position, boolean status) {
        getItem(position).setTaken(status);
        notifyDataSetChanged();
    }

}
