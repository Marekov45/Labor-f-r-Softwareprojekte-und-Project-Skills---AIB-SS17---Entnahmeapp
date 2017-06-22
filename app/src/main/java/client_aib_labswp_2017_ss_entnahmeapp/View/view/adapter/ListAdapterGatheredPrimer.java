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
import client_aib_labswp_2017_ss_entnahmeapp.View.model.model_List.PrimerTube;
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
        public Button returnPrimer;
    }
    public void checkBarcodeWithPrimer(Barcode barcode) {

        for (PrimerTube primertube : primerTubes) {
            if (barcode.displayValue.equals(primertube.getPrimerTubeID())) {
                if (!primertube.isReturnToStorage()){
                    Toast.makeText(context, "NICHT ZURÜCKLEGEN",Toast.LENGTH_SHORT).show();
                }else{
                    primerImpl.returnPrimer(primertube.getObjectID(), user.getUsername(), user.getPassword());
                    primertube.setTaken(true);
                    notifyDataSetChanged();
                }

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
            viewholder.returnPrimer = (Button) convertView.findViewById(R.id.btnReturnPrimer);
            convertView.setTag(viewholder);

        }

        final PrimerTube primerTube = primerTubes.get(position);
        final ViewHolder holder = (ViewHolder) convertView.getTag();

        holder.returnPrimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!primerTube.isReturnToStorage()){
                    Toast.makeText(context, "NICHT ZURÜCKLEGEN",Toast.LENGTH_SHORT).show();
                }else {
                    primerImpl.returnPrimer(primerTube.getObjectID(), user.getUsername(), user.getPassword());
                    primerTube.setTaken(true);
//                System.out.println("Primer zurückgelegt"+primerTube.getName());
                    notifyDataSetChanged();
                }
            }
        });


        holder.txtReturn_Primer.setText(primerTube.getName());
        holder.txtReturn_StorageLocation.setText(primerTube.getStorageLocation().toString());
        if (getItem(position).isTaken()) {
            holder.checkZurueckgegeben.setChecked(true);
            holder.returnPrimer.setEnabled(false);

        } else {
            holder.checkZurueckgegeben.setChecked(false);
            holder.returnPrimer.setEnabled(true);

        }
        return convertView;
    }


}

