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

    private int listIndex = 0;
    private int primerTubeIndex = 0;

    public ListAdapter(Context context, int vg, int id, List<PrimerTube> primerTubes, List<PickList> pickLists) {
        super(context, vg, id, primerTubes);
        this.context = context;
        this.primerTubes = primerTubes;
        this.pickLists = pickLists;
        this.vg = vg;
    }

    //Hold views of the listView to improve its scrolling performance
    static class ViewHolder {
        public TextView txtModel;
        public TextView txtPrice;
        public TextView txt2;
        public TextView txt3;
        public Button manualScan;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(vg, parent, false);
            ViewHolder holder = new ViewHolder();
            holder.txtModel = (TextView) rowView.findViewById(R.id.txtmodel);
            holder.txtPrice = (TextView) rowView.findViewById(R.id.txtprice);
            holder.txt2 = (TextView) rowView.findViewById(R.id.txt2);
            holder.txt3 = (TextView) rowView.findViewById(R.id.txt3);

            holder.manualScan = (Button) rowView.findViewById(R.id.btn);
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
        holder.txtModel.setText(String.valueOf(position));
        holder.txtPrice.setText(primerTube.getName());
        holder.txt2.setText(primerTube.getStorageLocation().toString());

        PickList pickListFinal = null;
        int postitioncounter = position;
        for(PickList pickList:pickLists){
            postitioncounter = postitioncounter-pickList.getPickList().size();
            if(postitioncounter<0){
                pickListFinal = pickList;
                break;
            }
        }

        holder.txt3.setText(pickListFinal.getDestination().getLocationName());

//        primerTubeIndex++;
//        if(primerTubeIndex>= pickLists.get(listIndex).getPickList().size()){
//            primerTubeIndex = 0;
//            listIndex++;
//        }


        return rowView;
    }
}
