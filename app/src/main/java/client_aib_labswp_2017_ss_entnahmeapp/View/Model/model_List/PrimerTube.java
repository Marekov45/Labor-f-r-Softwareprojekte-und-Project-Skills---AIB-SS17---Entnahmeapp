package client_aib_labswp_2017_ss_entnahmeapp.View.Model.model_List;

import client_aib_labswp_2017_ss_entnahmeapp.View.Model.test.DemoResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;


/**
 * Created by SimonHauck-GamingPC on 23.04.2017.
 */
//@JsonIgnoreProperties(ignoreUnknown = true)
public class PrimerTube {

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
    private boolean returnToStorage;
    private String manufacturer;
    private long objectID;

    public PrimerTube(long objectID, String primerTubeID, String primerUID, String name, String lotNr, StorageLocation storageLocation, boolean returnToStorage, String manufacturer) {
        this.objectID=objectID;
        this.primerTubeID = primerTubeID;
        this.primerUID = primerUID;
        this.name = name;
        this.lotNr = lotNr;
        this.storageLocation = storageLocation;
        this.returnToStorage = returnToStorage;
        this.manufacturer = manufacturer;
    }

    public PrimerTube() {
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
}
