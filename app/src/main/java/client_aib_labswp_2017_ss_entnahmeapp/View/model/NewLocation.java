package client_aib_labswp_2017_ss_entnahmeapp.View.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Defines a {@link NewLocation} for a primer. The purpose of this class is that the location of a primer is
 * immediately updated before loading the list another time.
 */
public class NewLocation implements Parcelable {
    private String newLocation;

    /**
     * Initializes the {@link NewLocation}.
     *
     * @param location is not allowed to be {@code null} and must not be an empty
     *                 String (<code>""</code>).
     */
    public NewLocation(String location) {
        this.newLocation = location;
    }

    /**
     * Initializes the Parcel.
     *
     * @param in container for a message.
     */
    protected NewLocation(Parcel in) {
        newLocation = in.readString();
    }

    //Generates instances of this Parcelable class from a Parcel.
    public static final Creator<NewLocation> CREATOR = new Creator<NewLocation>() {
        /**
         * Create a new instance of the Parcelable class, instantiating it from the given Parcel whose data had
         * previously been written by
         *
         * @param in the Parcel to read the object's data from.
         * @return A new instance of the Parcelable class
         */
        @Override
        public NewLocation createFromParcel(Parcel in) {
            return new NewLocation(in);
        }

        /**
         * Creates a new array of the Parcelable class.
         *
         * @param size size of the array.
         * @return An array of the Parcelable class, with every entry initialized to null
         */
        @Override
        public NewLocation[] newArray(int size) {
            return new NewLocation[size];
        }
    };

    public String getNewLocation() {
        return newLocation;
    }

    /**
     * @param newLocation is not allowed to be {@code null} and must not be an empty
     *                    String (<code>""</code>).
     */
    public void setNewLocation(String newLocation) {
        this.newLocation = newLocation;
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
        dest.writeString(newLocation);
    }
}
