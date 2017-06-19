package client_aib_labswp_2017_ss_entnahmeapp.View.Model.model_List;

import android.os.Parcel;
import android.os.Parcelable;
import client_aib_labswp_2017_ss_entnahmeapp.View.Model.test.DemoResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;


/**
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PrimerTube implements Parcelable{

//    private LocalDateTime takeOutDate;
//    private LocalDateTime putBackDate;
    private DemoResponse takeOutDate;
    private DemoResponse putBackDate;

    // PrimerTubeID  , PrimerUID from Labor, PrimerTube Name, LotNR from Storage
    private String primerTubeID;
    private String primerUID;
    private String name;
    private String lotNr;
    private StorageLocation storageLocation;
    private static boolean returnToStorage;
    private String manufacturer;
    private long objectID;

    private String currentLocation;
    private boolean taken;

    public PrimerTube(long objectID, String primerTubeID, String primerUID, String name, String lotNr, StorageLocation storageLocation, boolean returnToStorage, String manufacturer, String currentLocation) {
        this.objectID=objectID;
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

    public static final Creator<PrimerTube> CREATOR = new Creator<PrimerTube>() {
        @Override
        public PrimerTube createFromParcel(Parcel in) {
            return new PrimerTube(in);
        }

        @Override
        public PrimerTube[] newArray(int size) {
            return new PrimerTube[size];
        }
    };

    public String getCurrentLocation() {
        return currentLocation;
    }

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

    public static boolean isReturnToStorage() {
        return returnToStorage;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public long getObjectID() {
        return objectID;
    }

    public void setPrimerTubeID(String primerTubeID) {
        this.primerTubeID = primerTubeID;
    }

    public void setPrimerUID(String primerUID) {
        this.primerUID = primerUID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLotNr(String lotNr) {
        this.lotNr = lotNr;
    }

    public void setStorageLocation(StorageLocation storageLocation) {
        this.storageLocation = storageLocation;
    }

    public void setReturnToStorage(boolean returnToStorage) {
        this.returnToStorage = returnToStorage;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public void setObjectID(long objectID) {
        this.objectID = objectID;
    }

    public DemoResponse getTakeOutDate() {
        return takeOutDate;
    }

    public void setTakeOutDate(DemoResponse takeOutDate) {
        this.takeOutDate = takeOutDate;
    }

    public DemoResponse getPutBackDate() {
        return putBackDate;
    }

    public void setPutBackDate(DemoResponse putBackDate) {
        this.putBackDate = putBackDate;
    }

    public boolean isTaken() {
        return taken;
    }

    public void setTaken(boolean taken) {
        this.taken = taken;
    }



    @Override
    public int describeContents() {
        return 0;
    }

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
