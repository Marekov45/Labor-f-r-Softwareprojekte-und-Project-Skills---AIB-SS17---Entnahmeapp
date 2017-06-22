package client_aib_labswp_2017_ss_entnahmeapp.View.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by User on 08.06.2017.
 */
public class User implements Parcelable{
    private String username;
    private String password;

    public User(String name, String password){
        this.username = name;
        this.password=password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {

        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.username);
        dest.writeString(this.password);
    }

    protected User(Parcel in) {
        this.username = in.readString();
        this.password = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
