package client_aib_labswp_2017_ss_entnahmeapp.View.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import client.aib_labswp_2017_ss_entnahmeapp.R;
import client_aib_labswp_2017_ss_entnahmeapp.View.Controller.ServerAPI.ListImpl;
import client_aib_labswp_2017_ss_entnahmeapp.View.Model.User;
import client_aib_labswp_2017_ss_entnahmeapp.View.Model.model_List.PickList;
import client_aib_labswp_2017_ss_entnahmeapp.View.Model.model_List.PrimerTube;

import java.util.List;

/**
 * Created by Marvin on 17.06.2017.
 */
public class ListAdapterLabor extends ArrayAdapter<PrimerTube> {
    private int vg;
    private List<PrimerTube> primerTubes;
    private List<PickList> pickLists;
    Context context;
    ListImpl listImpl;
    User user;
    private String[] items = {"keine Bemerkung", "nicht auffindbar","beschädigt"};

    public ListAdapterLabor(Context context, int vg, int id, List<PrimerTube> primerTubes, User user, ListImpl listImpl) {
        super(context, vg, id, primerTubes);

        this.context = context;
        this.primerTubes = primerTubes;
       // this.pickLists = pickLists;
        this.vg = vg;
        this.user = user;
        this.listImpl = listImpl;
    }
    static class ViewHolder{
        public TextView txtPrimer;
        public TextView txtPrimerTube;
        public TextView txtLOT;
        public TextView txtLocation;
        public Spinner txtBemerkung;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(vg, parent, false);
            ListAdapterLabor.ViewHolder viewholder = new ListAdapterLabor.ViewHolder();
            viewholder.txtPrimer = (TextView) view.findViewById(R.id.primerTxtView);
            viewholder.txtPrimerTube = (TextView) view.findViewById(R.id.primerTubeTxtView);
            viewholder.txtLOT = (TextView) view.findViewById(R.id.lotTxtView);
            viewholder.txtLocation = (TextView) view.findViewById(R.id.locationTxtView);
            view.setTag(viewholder);

        }

        final PrimerTube primerTube = primerTubes.get(position);
        final ListAdapterLabor.ViewHolder holder = (ListAdapterLabor.ViewHolder) view.getTag();

       // holder.txtPos.setText(String.valueOf((position%32)+1));
        holder.txtPrimer.setText(primerTube.getName());
        holder.txtPrimerTube.setText(primerTube.getPrimerTubeID());
        holder.txtLOT.setText(primerTube.getLotNr());

        //PickList pickListFinal = null;
       // int postitioncounter = position;
        //for (PickList pickList : pickLists) {
        //    postitioncounter = postitioncounter - pickList.getPickList().size();
        //    if (postitioncounter < 0) {
        //        pickListFinal = pickList;
        //        break;
        //    }
       // }
        holder.txtLocation.setText(primerTube.getCurrentLocation());
        holder.txtBemerkung = (Spinner) view.findViewById(R.id.bemerkungSpinner);
          ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.context,android.R.layout.simple_spinner_dropdown_item,items);
         adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
          holder.txtBemerkung.setAdapter(adapter);

        return view;
    }
}
