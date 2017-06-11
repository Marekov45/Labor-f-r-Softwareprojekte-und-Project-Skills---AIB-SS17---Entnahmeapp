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

import java.util.List;

/**
 * Created by User on 11.06.2017.
 */
public class ListAdapter extends ArrayAdapter<PickList> {
    private int vg;
    private List<PickList> pickLists;
    Context context;

    public ListAdapter(Context context, int vg, int id, List<PickList> picklists) {
        super(context, vg, id, picklists);
        this.context = context;
        this.pickLists = picklists;
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
                    Toast.makeText(getContext(), "Button was clicked for item "+position, Toast.LENGTH_SHORT).show();

                }
            });
            rowView.setTag(holder);

        }

        PickList currentPickList = pickLists.get(position);
        ViewHolder holder = (ViewHolder) rowView.getTag();
        holder.txtModel.setText(currentPickList.getDestination().getLocationName());
        holder.txtPrice.setText(String.valueOf(currentPickList.getObjectID()));
        holder.txt2.setText(currentPickList.getEntryDate().toString());
        holder.txt3.setText("3");
        return rowView;
    }
}
