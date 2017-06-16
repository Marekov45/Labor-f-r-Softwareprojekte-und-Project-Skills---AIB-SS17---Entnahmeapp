package client_aib_labswp_2017_ss_entnahmeapp.View.Model;

import android.os.Parcel;
import android.os.Parcelable;
import client_aib_labswp_2017_ss_entnahmeapp.View.Model.model_List.PrimerTube;

import java.util.ArrayList;

/**
 * Created by User on 15.06.2017.
 */
public class TubesArray implements Parcelable {

    private ArrayList<PrimerTube> primertubes;

    public TubesArray(ArrayList<PrimerTube> tubes){
        this.primertubes=tubes;
    }

    public ArrayList<PrimerTube> getPrimertubes() {
        return primertubes;
    }

    public void setPrimertubes(ArrayList<PrimerTube> primertubes) {
        this.primertubes = primertubes;
    }

    protected TubesArray(Parcel in) {
    }

    public static final Creator<TubesArray> CREATOR = new Creator<TubesArray>() {
        @Override
        public TubesArray createFromParcel(Parcel in) {
            return new TubesArray(in);
        }

        @Override
        public TubesArray[] newArray(int size) {
            return new TubesArray[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
