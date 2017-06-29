package client_aib_labswp_2017_ss_entnahmeapp.View.view.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
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
import client_aib_labswp_2017_ss_entnahmeapp.View.view.popup.PopReturn;
import com.google.android.gms.vision.barcode.Barcode;

import java.util.List;

/**
 * {@link ListAdapterGatheredPrimer} converts a list of {@link PrimerTube} objects into {@link View} items loaded into the {@link ListView} container.
 */
public class ListAdapterGatheredPrimer extends ArrayAdapter<PrimerTube> {

    private int vg;
    private List<PrimerTube> primerTubes;
    Context context;
    ListImpl listImpl;
    PrimerImpl primerImpl;
    User user;


    /**
     * @param context    context of the current state of the application. It must not be {@code null}.
     * @param vg         row layout for a {@link View} item. it must not be {@code null}.
     * @param id         position of the view item
     * @param tubes      the list of {@link PrimerTube} objects loaded into the {@link ListView}.It might be empty.
     * @param user       user that logged on to the application. It must not be {@code null}.
     * @param listImpl   implementation of all rest requests regarding lists. It must not be {@code null}.
     * @param primerImpl implementation of all rest requests regarding primers. It must not be {@code null}.
     */
    public ListAdapterGatheredPrimer(Context context, int vg, int id, List<PrimerTube> tubes, User user, ListImpl listImpl, PrimerImpl primerImpl) {
        super(context, vg, id, tubes);

        this.context = context;
        this.vg = vg;
        this.user = user;
        this.listImpl = listImpl;
        this.primerTubes = tubes;
        this.primerImpl = primerImpl;
    }

    public void setPrimerOnTakenIfRemovedManually(PrimerTube tubeToRemove, int positionForReplacement) {
        getItem(positionForReplacement - 1).setReturned(true);
        notifyDataSetChanged();
    }

    static class ViewHolder {
        public TextView txtReturn_Primer;
        public TextView txtReturn_StorageLocation;
        public CheckBox checkIfReturn;
        public Button returnPrimer;
    }

    /**
     * Checks for every {@link PrimerTube} object in the {@link ListView} if the barcode matches
     * with a {@link PrimerTube} ID. If the values are the same, the fitting {@link PrimerTube} is returned
     * to the storage.
     *
     * @param barcode  the barcode that was scanned by the camera of the device
     * @param listView
     */
    public void checkBarcodeWithPrimer(Barcode barcode, ListView listView) {

        for (PrimerTube primertube : primerTubes) {
            if (barcode.displayValue.equals(primertube.getPrimerTubeID())) {
                if (!primertube.isReturnToStorage()) {
                    Intent intentPopup = new Intent(context, PopReturn.class);
                    intentPopup.putExtra("USERREMOVE", user);
                    intentPopup.putExtra("PRIMERTUBETOREMOVE", primertube);
                    context.startActivity(intentPopup);
                    primerTubes.remove(primertube);
                    notifyDataSetChanged();
                } else {
                    primerImpl.returnPrimer(primertube.getObjectID(), getPosition(primertube), user.getUsername(), user.getPassword());
                    primertube.setReturned(true);

                    // setup the alert builder
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Die Lagerkoordinate für den Primer " + primertube.getName() + " lautet:");
                    builder.setMessage(primertube.getStorageLocation() + "");
                    // add a button
                    builder.setPositiveButton("OK", null);
                    // create and show the alert dialog
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    notifyDataSetChanged();
                    listView.setSelection(getPosition(primertube));
                }

            }
        }

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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewholder;
        if (convertView == null) {
            viewholder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(vg, parent, false);

            viewholder.txtReturn_Primer = (TextView) convertView.findViewById(R.id.txtPrimerLastGathered);
            viewholder.txtReturn_StorageLocation = (TextView) convertView.findViewById(R.id.txtStorageLocationLastGathered);
            viewholder.checkIfReturn = (CheckBox) convertView.findViewById(R.id.checkZurueck);
            viewholder.returnPrimer = (Button) convertView.findViewById(R.id.btnReturnPrimer);
            convertView.setTag(viewholder);

        }

        final PrimerTube primerTube = primerTubes.get(position);
        final ViewHolder holder = (ViewHolder) convertView.getTag();

        holder.returnPrimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!primerTube.isReturnToStorage()) {
                    Intent intentPopup = new Intent(context, PopReturn.class);
                    intentPopup.putExtra("USERREMOVE", user);
                    intentPopup.putExtra("PRIMERTUBETOREMOVE", primerTube);
                    context.startActivity(intentPopup);
                    primerTubes.remove(primerTube);
                    notifyDataSetChanged();
                } else {
                    primerImpl.returnPrimer(primerTube.getObjectID(), position, user.getUsername(), user.getPassword());
                    primerTube.setReturned(true);
                    notifyDataSetChanged();
                }
            }
        });


        holder.txtReturn_Primer.setText(primerTube.getName());
        holder.txtReturn_StorageLocation.setText(primerTube.getStorageLocation().toString());
        if (getItem(position).isReturned()) {
            holder.checkIfReturn.setChecked(true);
            holder.returnPrimer.setEnabled(false);

        } else {
            holder.checkIfReturn.setChecked(false);
            holder.returnPrimer.setEnabled(true);

        }
        return convertView;
    }

    public void changeReturnStatus(int position, boolean status) {
        getItem(position).setReturned(status);
        notifyDataSetChanged();
    }
}

