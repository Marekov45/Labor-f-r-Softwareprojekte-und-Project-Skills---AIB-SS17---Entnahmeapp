package client_aib_labswp_2017_ss_entnahmeapp.View.Model.model_List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.*;

/**
 * Created by SimonHauck-GamingPC on 23.04.2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PrimerTube {

//    private LocalDateTime takeOutDate;
//    private LocalDateTime putBackDate;

    // PrimerTubeID  , PrimerUID from Labor, PrimerTube Name, LotNR from Storage
    private String primerTubeID;
    private String primerUID;
    private String name;
    private String lotNr;
    private StorageLocation storageLocation;
    private boolean returnToStorage;
    private String manufacturer;
    private long obejctID;

    public PrimerTube(long objectID, String primerTubeID, String primerUID, String name, String lotNr, StorageLocation storageLocation, boolean returnToStorage, String manufacturer) {
        this.obejctID=objectID;
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

    public long getObejctID() {
        return obejctID;
    }
}
