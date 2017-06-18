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
    private List<PickList> pickLists;
    Context context;
    ListImpl listImpl;
    User user;

    public ListAdapterGatheredPrimer(Context context, int vg, int id, User user, ListImpl listImpl) {
        super(context, vg, id);

        this.context = context;
        this.vg = vg;
        this.user = user;
        this.listImpl = listImpl;
    }
    static class ViewHolder{
        public TextView txtReturn_Primer;
        public TextView txtReturn_StorageLocation;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(vg, parent, false);
            ListAdapterGatheredPrimer.ViewHolder viewholder = new ListAdapterGatheredPrimer.ViewHolder();
            viewholder.txtReturn_Primer = (TextView) view.findViewById(R.id.txtReturn_Primer);
            viewholder.txtReturn_StorageLocation = (TextView) view.findViewById(R.id.txtReturn_StorageLocation);
            view.setTag(viewholder);

        }
        return view;
    }
}

