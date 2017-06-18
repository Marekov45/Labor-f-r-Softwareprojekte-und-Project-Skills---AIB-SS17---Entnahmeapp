package client_aib_labswp_2017_ss_entnahmeapp.View.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by User on 18.06.2017.
 */
public class NewLocation implements Parcelable{
    private String newLocation;
    public NewLocation(String location){
        this.newLocation=location;
    }

    protected NewLocation(Parcel in) {
        newLocation = in.readString();
    }

    public static final Creator<NewLocation> CREATOR = new Creator<NewLocation>() {
        @Override
        public NewLocation createFromParcel(Parcel in) {
            return new NewLocation(in);
        }

        @Override
        public NewLocation[] newArray(int size) {
            return new NewLocation[size];
        }
    };

    public String getNewLocation() {
        return newLocation;
    }

    public void setNewLocation(String newLocation) {
        this.newLocation = newLocation;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(newLocation);
    }
}
