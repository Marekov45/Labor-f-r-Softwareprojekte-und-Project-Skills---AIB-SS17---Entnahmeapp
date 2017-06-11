package client_aib_labswp_2017_ss_entnahmeapp.View.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import client.aib_labswp_2017_ss_entnahmeapp.R;
import client_aib_labswp_2017_ss_entnahmeapp.View.Model.model_List.PickList;
import client_aib_labswp_2017_ss_entnahmeapp.View.Model.model_List.PrimerTube;

import java.util.List;

/**
 * Created by User on 11.06.2017.
 */
public class ListAdapter extends ArrayAdapter<PrimerTube> {
    private int vg;
    private List<PrimerTube> primerTubes;
    private List<PickList> pickLists;
    Context context;

    public ListAdapter(Context context, int vg, int id, List<PrimerTube> primerTubes, List<PickList> pickLists) {
        super(context, vg, id, primerTubes);
        this.context = context;
        this.primerTubes = primerTubes;
        this.pickLists = pickLists;
        this.vg = vg;
    }

    //Hold views of the listView to improve its scrolling performance
    static class ViewHolder {
        public TextView txtPos;
        public TextView txtPrimer;
        public TextView txtStorageLocation;
        public TextView txtDestination;
        public Button manualScan;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(vg, parent, false);
            ViewHolder holder = new ViewHolder();
            holder.txtPos = (TextView) rowView.findViewById(R.id.txtPos);
            holder.txtPrimer = (TextView) rowView.findViewById(R.id.txtPrimer);
            holder.txtStorageLocation = (TextView) rowView.findViewById(R.id.txtStorageLocation);
            holder.txtDestination = (TextView) rowView.findViewById(R.id.txtDestination);

            holder.manualScan = (Button) rowView.findViewById(R.id.btnTakePrimer);
            holder.manualScan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "Button was clicked for item " + position, Toast.LENGTH_SHORT).show();

                }
            });
            rowView.setTag(holder);

        }


        PrimerTube primerTube = primerTubes.get(position);
        ViewHolder holder = (ViewHolder) rowView.getTag();
        holder.txtPos.setText(String.valueOf(position));
        holder.txtPrimer.setText(primerTube.getName());
        holder.txtStorageLocation.setText(primerTube.getStorageLocation().toString());

        PickList pickListFinal = null;
        int postitioncounter = position;
        for(PickList pickList:pickLists){
            postitioncounter = postitioncounter-pickList.getPickList().size();
            if(postitioncounter<0){
                pickListFinal = pickList;
                break;
            }
        }
        holder.txtDestination.setText(pickListFinal.getDestination().getLocationName());

//        primerTubeIndex++;
//        if(primerTubeIndex>= pickLists.get(listIndex).getPickList().size()){
//            primerTubeIndex = 0;
//            listIndex++;
//        }
        return rowView;
    }
}
