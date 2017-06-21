package client_aib_labswp_2017_ss_entnahmeapp.View.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import client.aib_labswp_2017_ss_entnahmeapp.R;
import client_aib_labswp_2017_ss_entnahmeapp.View.Controller.ServerAPI.ListImpl;
import client_aib_labswp_2017_ss_entnahmeapp.View.Controller.ServerAPI.PrimerImpl;
import client_aib_labswp_2017_ss_entnahmeapp.View.Model.User;
import client_aib_labswp_2017_ss_entnahmeapp.View.Model.model_List.PickList;
import client_aib_labswp_2017_ss_entnahmeapp.View.Model.model_List.PrimerTube;
import com.google.android.gms.vision.barcode.Barcode;

import java.util.List;
/**
 * Created by neuma on 17.06.2017.
 */
public class ListAdapterGatheredPrimer extends ArrayAdapter<PrimerTube>{

    private int vg;
    private List<PrimerTube> primerTubes;
    Context context;
    ListImpl listImpl;
    PrimerImpl primerImpl;
    User user;

    public ListAdapterGatheredPrimer(Context context, int vg, int id, List<PrimerTube> tubes, User user, ListImpl listImpl, PrimerImpl primerImpl) {
        super(context, vg, id, tubes);

        this.context = context;
        this.vg = vg;
        this.user = user;
        this.listImpl = listImpl;
        this.primerTubes=tubes;
        this.primerImpl = primerImpl;
    }
    static class ViewHolder{
        public TextView txtReturn_Primer;
        public TextView txtReturn_StorageLocation;
        public CheckBox checkZurueckgegeben;
    }
    public void checkBarcodeWithPrimer(Barcode barcode) {

        for (PrimerTube primertube : primerTubes) {
            if (barcode.displayValue.equals(primertube.getPrimerTubeID())) {
                primerImpl.returnPrimer(primertube.getObjectID(), user.getUsername(), user.getPassword());
                primertube.setTaken(true);
                notifyDataSetChanged();
            }
        }

    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewholder;
        if (convertView == null) {
            viewholder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(vg, parent, false);

            viewholder.txtReturn_Primer= (TextView) convertView.findViewById(R.id.txtPrimerLastGathered);
            viewholder.txtReturn_StorageLocation = (TextView) convertView.findViewById(R.id.txtStorageLocationLastGathered);
            viewholder.checkZurueckgegeben = (CheckBox) convertView.findViewById(R.id.checkZurueck);
            convertView.setTag(viewholder);

        }

        final PrimerTube primerTube = primerTubes.get(position);
        final ViewHolder holder = (ViewHolder) convertView.getTag();


        holder.txtReturn_Primer.setText(primerTube.getName());
        holder.txtReturn_StorageLocation.setText(primerTube.getStorageLocation().toString());
        if (getItem(position).isTaken()) {
            holder.checkZurueckgegeben.setChecked(true);

        } else {
            holder.checkZurueckgegeben.setChecked(false);

        }
        return convertView;
    }


}

