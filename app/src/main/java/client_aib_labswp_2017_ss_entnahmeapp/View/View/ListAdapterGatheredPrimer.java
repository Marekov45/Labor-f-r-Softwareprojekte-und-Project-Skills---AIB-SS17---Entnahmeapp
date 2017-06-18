package client_aib_labswp_2017_ss_entnahmeapp.View.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import client.aib_labswp_2017_ss_entnahmeapp.R;
import client_aib_labswp_2017_ss_entnahmeapp.View.Controller.ServerAPI.ListImpl;
import client_aib_labswp_2017_ss_entnahmeapp.View.Model.User;
import client_aib_labswp_2017_ss_entnahmeapp.View.Model.model_List.PickList;
import client_aib_labswp_2017_ss_entnahmeapp.View.Model.model_List.PrimerTube;

import java.util.List;
/**
 * Created by neuma on 17.06.2017.
 */
public class ListAdapterGatheredPrimer extends ArrayAdapter<PrimerTube>{

    private int vg;
    private List<PrimerTube> primerTubes;
    Context context;
    ListImpl listImpl;
    User user;

    public ListAdapterGatheredPrimer(Context context, int vg, int id, List<PrimerTube> tubes, User user, ListImpl listImpl) {
        super(context, vg, id, tubes);

        this.context = context;
        this.vg = vg;
        this.user = user;
        this.listImpl = listImpl;
        this.primerTubes=tubes;
    }
    static class ViewHolder{
        public TextView txtReturn_Primer;
        public TextView txtReturn_StorageLocation;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(vg, parent, false);
            ListAdapterGatheredPrimer.ViewHolder viewholder = new ListAdapterGatheredPrimer.ViewHolder();
            viewholder.txtReturn_Primer = (TextView) view.findViewById(R.id.txtReturn_Primer);
            viewholder.txtReturn_StorageLocation = (TextView) view.findViewById(R.id.txtReturn_StorageLocation);
            view.setTag(viewholder);

        }
        final PrimerTube primerTube = primerTubes.get(position);
        final ListAdapterGatheredPrimer.ViewHolder holder = (ListAdapterGatheredPrimer.ViewHolder) view.getTag();

        holder.txtReturn_Primer.setText(primerTube.getName());
        holder.txtReturn_StorageLocation.setText(primerTube.getStorageLocation().toString());
        return view;
    }


}

