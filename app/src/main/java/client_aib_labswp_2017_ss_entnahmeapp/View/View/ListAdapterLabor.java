package client_aib_labswp_2017_ss_entnahmeapp.View.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import client.aib_labswp_2017_ss_entnahmeapp.R;
import client_aib_labswp_2017_ss_entnahmeapp.View.Controller.ServerAPI.ListImpl;
import client_aib_labswp_2017_ss_entnahmeapp.View.Model.User;
import client_aib_labswp_2017_ss_entnahmeapp.View.Model.model_List.PrimerTube;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marvin on 17.06.2017.
 */
public class ListAdapterLabor extends ArrayAdapter<PrimerTube> implements Filterable {
    private int vg;
    private List<PrimerTube> primerTubes;
    private List<PrimerTube> filteredTubes;
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

    public Filter getFilter() {
        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final ArrayList<PrimerTube> results = new ArrayList<PrimerTube>();
                if (filteredTubes == null)
                    filteredTubes = primerTubes;
                if (constraint != null) {
                    if (filteredTubes != null && filteredTubes.size() > 0) {
                        for (final PrimerTube tube : filteredTubes) {
                            if (tube.getName().toLowerCase()
                                    .contains(constraint.toString()))
                                results.add(tube);
                        }
                    }
                    oReturn.values = results;
                }
                return oReturn;
            }


            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                primerTubes = (ArrayList<PrimerTube>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return primerTubes.size();
    }


    public PrimerTube getItem(int position) {
        return primerTubes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewholder;

        if (convertView == null) {
            convertView=LayoutInflater.from(context).inflate(R.layout.rowlayout_tracking, parent, false);
            viewholder = new  ViewHolder();
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
       // holder.txtPos.setText(String.valueOf((position%32)+1));
        viewholder.txtPrimer.setText(primerTubes.get(position).getName());
        viewholder.txtPrimerTube.setText(primerTubes.get(position).getPrimerTubeID());
        viewholder.txtLOT.setText(primerTubes.get(position).getLotNr());

        //PickList pickListFinal = null;
       // int postitioncounter = position;
        //for (PickList pickList : pickLists) {
        //    postitioncounter = postitioncounter - pickList.getPickList().size();
        //    if (postitioncounter < 0) {
        //        pickListFinal = pickList;
        //        break;
        //    }
       // }
        viewholder.txtLocation.setText(primerTubes.get(position).getCurrentLocation());
        viewholder.txtBemerkung = (Spinner) convertView.findViewById(R.id.bemerkungSpinner);
          ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.context,android.R.layout.simple_spinner_dropdown_item,items);
         adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
          viewholder.txtBemerkung.setAdapter(adapter);

        return convertView;
    }
}
