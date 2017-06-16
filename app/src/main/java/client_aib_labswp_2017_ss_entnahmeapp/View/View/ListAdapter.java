package client_aib_labswp_2017_ss_entnahmeapp.View.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import client.aib_labswp_2017_ss_entnahmeapp.R;
import client_aib_labswp_2017_ss_entnahmeapp.View.Controller.ServerAPI.ListImpl;
import client_aib_labswp_2017_ss_entnahmeapp.View.Model.User;
import client_aib_labswp_2017_ss_entnahmeapp.View.Model.model_List.PickList;
import client_aib_labswp_2017_ss_entnahmeapp.View.Model.model_List.PrimerTube;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by User on 11.06.2017.
 */
public class ListAdapter extends ArrayAdapter<PrimerTube> {
    private int vg;
    private List<PrimerTube> primerTubes;
    private List<PickList> pickLists;
    Context context;
    ListImpl listImpl;
    User user;
    static final int MAXPOSITIONINCARRIER=32;
//    View rowView;

    public ListAdapter(Context context, int vg, int id, List<PrimerTube> primerTubes, List<PickList> pickLists, User user, ListImpl listImpl) {
        super(context, vg, id, primerTubes);
        this.context = context;
        this.primerTubes = primerTubes;
        this.pickLists = pickLists;
        this.vg = vg;
        this.user = user;
        this.listImpl = listImpl;
    }

    //Hold views of the listView to improve its scrolling performance
    static class ViewHolder {
        public TextView txtPos;
        public TextView txtPrimer;
        public TextView txtStorageLocation;
        public TextView txtDestination;
        public Button manualScan;
    }

    public View getView(final int position, final View convertView, ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(vg, parent, false);
            ViewHolder viewholder = new ViewHolder();
            viewholder.txtPos = (TextView) view.findViewById(R.id.txtPos);
            viewholder.txtPrimer = (TextView) view.findViewById(R.id.txtPrimer);
            viewholder.txtStorageLocation = (TextView) view.findViewById(R.id.txtStorageLocation);
            viewholder.txtDestination = (TextView) view.findViewById(R.id.txtDestination);
            view.setTag(viewholder);

        }

//        System.out.println(position);

        final PrimerTube primerTube = primerTubes.get(position);
        final ViewHolder holder = (ViewHolder) view.getTag();


        holder.txtPos.setText(String.valueOf((position%32)+1));

        holder.txtPrimer.setText(primerTube.getName());
        holder.txtStorageLocation.setText(primerTube.getStorageLocation().toString());

        holder.manualScan = (Button) view.findViewById(R.id.btnTakePrimer);

        holder.manualScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(position);
                listImpl.takePrimer(primerTubes.get(position).getObjectID(), user.getUsername(), user.getPassword());
//                remove(getItem(position));
//
//                notifyDataSetChanged();
            }
        });

        if(primerTube.isTaken()){
            holder.manualScan.setEnabled(false);
        }

        PickList pickListFinal = null;
        int postitioncounter = position;
        for (PickList pickList : pickLists) {
            postitioncounter = postitioncounter - pickList.getPickList().size();
            if (postitioncounter < 0) {
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

        return view;
    }

}
