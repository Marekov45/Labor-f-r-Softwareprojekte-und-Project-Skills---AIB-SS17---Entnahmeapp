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

/**
 * Created by User on 11.06.2017.
 */
public class ListAdapter extends ArrayAdapter<String> {
    private int vg;
    String[] items_list;
    Context context;

    public ListAdapter(Context context, int vg, int id, String[] items_list) {
        super(context, vg, id, items_list);
        this.context = context;
        this.items_list = items_list;
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

    public View getView(final int postition, View convertView, ViewGroup parent) {
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
                    Toast.makeText(getContext(), "Button was clicked for item "+postition, Toast.LENGTH_SHORT).show();

                }
            });
            rowView.setTag(holder);

        }

        String[] items = items_list[postition].split("_");
        ViewHolder holder = (ViewHolder) rowView.getTag();
        holder.txtModel.setText(items[0]);
        holder.txtPrice.setText(items[1]);
        holder.txt2.setText(items[2]);
        holder.txt3.setText(items[3]);
        return rowView;
    }
}
