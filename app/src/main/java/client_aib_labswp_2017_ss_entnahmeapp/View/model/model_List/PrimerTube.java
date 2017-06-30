package client_aib_labswp_2017_ss_entnahmeapp.View.model.model_List;

import android.os.Parcel;
import android.os.Parcelable;
import client_aib_labswp_2017_ss_entnahmeapp.View.model.parsedate.ParseDateResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 * A {@link PrimerTube} contains the primers that serve as a starting point for replication.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PrimerTube implements Parcelable {

    private ParseDateResponse takeOutDate;
    private ParseDateResponse putBackDate;
    private String primerTubeID;
    private String primerUID;
    private String name;
    private String lotNr;
    private StorageLocation storageLocation;
    private boolean returnToStorage;
    private String manufacturer;
    private long objectID;
    private String currentLocation;
    private boolean taken;
    private boolean returned;

    /**
     * Initializes the {@link PrimerTube}.
     *
     * @param objectID        unique id. It must not be {@code null}.
     * @param primerTubeID    is not allowed to be {@code null} and must not be an empty
     *                        String (<code>""</code>).
     * @param primerUID       is not allowed to be {@code null} and must not be an empty
     *                        String (<code>""</code>).
     * @param name            is not allowed to be {@code null} and must not be an empty
     *                        String (<code>""</code>).
     * @param lotNr           is not allowed to be {@code null} and must not be an empty
     *                        String (<code>""</code>).
     * @param storageLocation must not be {@code null}.
     * @param returnToStorage value that tells if the {@link PrimerTube} has to be returned to the storage.
     * @param manufacturer    is not allowed to be {@code null} and must not be an empty
     *                        String (<code>""</code>).
     * @param currentLocation is not allowed to be {@code null} and must not be an empty
     *                        String (<code>""</code>).
     */
    public PrimerTube(long objectID, String primerTubeID, String primerUID, String name, String lotNr, StorageLocation storageLocation, boolean returnToStorage, String manufacturer, String currentLocation) {
        this.objectID = objectID;
        this.primerTubeID = primerTubeID;
        this.primerUID = primerUID;
        this.name = name;
        this.lotNr = lotNr;
        this.storageLocation = storageLocation;
        this.returnToStorage = returnToStorage;
        this.manufacturer = manufacturer;
        this.currentLocation = currentLocation;
        taken = false;
    }

    /**
     * Initializes the Parcel.
     *
     * @param in container for a message.
     */
    protected PrimerTube(Parcel in) {
        primerTubeID = in.readString();
        primerUID = in.readString();
        name = in.readString();
        lotNr = in.readString();
        storageLocation = in.readParcelable(StorageLocation.class.getClassLoader());
        returnToStorage = in.readByte() != 0;
        manufacturer = in.readString();
        objectID = in.readLong();
        currentLocation = in.readString();
        taken = in.readByte() != 0;
    }

    //Generates instances of this Parcelable class from a Parcel.
    public static final Creator<PrimerTube> CREATOR = new Creator<PrimerTube>() {
        /**
         * Create a new instance of the Parcelable class, instantiating it from the given Parcel whose data had
         * previously been written by
         *
         * @param in the Parcel to read the object's data from.
         * @return A new instance of the Parcelable class
         */
        @Override
        public PrimerTube createFromParcel(Parcel in) {
            return new PrimerTube(in);
        }

        /**
         * Creates a new array of the Parcelable class.
         *
         * @param size size of the array.
         * @return An array of the Parcelable class, with every entry initialized to null
         */
        @Override
        public PrimerTube[] newArray(int size) {
            return new PrimerTube[size];
        }
    };

    public String getCurrentLocation() {
        return currentLocation;
    }

    /**
     * @param currentLocation is not allowed to be {@code null} and must not be an empty
     *                        String (<code>""</code>).
     */
    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    public PrimerTube() {
        taken = false;
    }

    public String getPrimerTubeID() {
        return primerTubeID;
    }

    public String getPrimerUID() {
        return primerUID;
    }

    public String getName() {
        return name;
    }

    public String getLotNr() {
        return lotNr;
    }

    public StorageLocation getStorageLocation() {
        return storageLocation;
    }

    public boolean isReturnToStorage() {
        return returnToStorage;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public long getObjectID() {
        return objectID;
    }

    /**
     * @param primerTubeID is not allowed to be {@code null} and must not be an empty
     *                     String (<code>""</code>).
     */
    public void setPrimerTubeID(String primerTubeID) {
        this.primerTubeID = primerTubeID;
    }

    /**
     * @param primerUID is not allowed to be {@code null} and must not be an empty
     *                  String (<code>""</code>).
     */
    public void setPrimerUID(String primerUID) {
        this.primerUID = primerUID;
    }

    /**
     * @param name is not allowed to be {@code null} and must not be an empty
     *             String (<code>""</code>).
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param lotNr is not allowed to be {@code null} and must not be an empty
     *              String (<code>""</code>).
     */
    public void setLotNr(String lotNr) {
        this.lotNr = lotNr;
    }

    /**
     * @param storageLocation must not be {@code null}.
     */
    public void setStorageLocation(StorageLocation storageLocation) {
        this.storageLocation = storageLocation;
    }

    /**
     * @param returnToStorage value that tells if the {@link PrimerTube} has to be returned to the storage.
     */
    public void setReturnToStorage(boolean returnToStorage) {
        this.returnToStorage = returnToStorage;
    }

    /**
     * @param manufacturer is not allowed to be {@code null} and must not be an empty
     *                     String (<code>""</code>).
     */
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    /**
     * @param objectID must not be {@code null}.
     */
    public void setObjectID(long objectID) {
        this.objectID = objectID;
    }

    public ParseDateResponse getTakeOutDate() {
        return takeOutDate;
    }

    /**
     * @param takeOutDate must be before the {@link PrimerTube#setPutBackDate(ParseDateResponse)}.
     */
    public void setTakeOutDate(ParseDateResponse takeOutDate) {
        this.takeOutDate = takeOutDate;
    }

    public ParseDateResponse getPutBackDate() {
        return putBackDate;
    }

    /**
     * @param putBackDate must be after the {@link PrimerTube#setTakeOutDate(ParseDateResponse)}.
     */
    public void setPutBackDate(ParseDateResponse putBackDate) {
        this.putBackDate = putBackDate;
    }

    public boolean isTaken() {
        return taken;
    }

    /**
     * @param taken true if the {@link PrimerTube} has been taken from the storage, false
     *              if the current location of the {@link PrimerTube} is the storage
     */
    public void setTaken(boolean taken) {
        this.taken = taken;
    }

    public boolean isReturned() {
        return returned;
    }

    /**
     * @param returned false if the {@link PrimerTube} has been taken from the storage, true
     *                 if the current location of the {@link PrimerTube} is the storage
     */
    public void setReturned(boolean returned) {
        this.returned = returned;
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
        dest.writeString(primerTubeID);
        dest.writeString(primerUID);
        dest.writeString(name);
        dest.writeString(lotNr);
        dest.writeParcelable(storageLocation, flags);
        dest.writeByte((byte) (returnToStorage ? 1 : 0));
        dest.writeString(manufacturer);
        dest.writeLong(objectID);
        dest.writeString(currentLocation);
        dest.writeByte((byte) (taken ? 1 : 0));
    }
}
