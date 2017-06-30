package client_aib_labswp_2017_ss_entnahmeapp.View.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Defines a {@link User} with all of his information regarding the authentication.
 */
public class User implements Parcelable {
    private String username;
    private String password;

    /**
     * Initializes the {@link User}.
     *
     * @param name     is not allowed to be {@code null} and must not be an empty
     *                 String (<code>""</code>).
     * @param password is not allowed to be {@code null} and must not be an empty
     *                 String (<code>""</code>).
     */
    public User(String name, String password) {
        this.username = name;
        this.password = password;
    }

    /**
     * @param username is not allowed to be {@code null} and must not be an empty
     *                 String (<code>""</code>).
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @param password is not allowed to be {@code null} and must not be an empty
     *                 String (<code>""</code>).
     */
    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {

        return username;
    }

    public String getPassword() {
        return password;
    }

    /**
     * Describe the kinds of special objects contained in this Parcelable instance's marshaled representation.
     *
     * @return a bitmask indicating the set of special object types marshaled by this Parcelable object instance
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flattens the object in to a Parcel.
     *
     * @param dest  the Parcel in which the object should be written.
     * @param flags additional flags about how the object should be written.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.username);
        dest.writeString(this.password);
    }

    /**
     * Initializes the Parcel.
     *
     * @param in container for a message.
     */
    protected User(Parcel in) {
        this.username = in.readString();
        this.password = in.readString();
    }


    //Generates instances of this Parcelable class from a Parcel.
    public static final Creator<User> CREATOR = new Creator<User>() {
        /**
         * Create a new instance of the Parcelable class, instantiating it from the given Parcel whose data had
         * previously been written by
         *
         * @param source the Parcel to read the object's data from.
         * @return A new instance of the Parcelable class
         */
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        /**
         * Creates a new array of the Parcelable class.
         *
         * @param size size of the array.
         * @return An array of the Parcelable class, with every entry initialized to null
         */
        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
