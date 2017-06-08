package client_aib_labswp_2017_ss_entnahmeapp.View.Model.model_List;

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

    public PrimerTube(long objectID, LocalDateTime takeOutDate, LocalDateTime putBackDate, String primerTubeID, String primerUID, String name, String lotNr, StorageLocation storageLocation, boolean returnToStorage, String manufacturer) {
        super(objectID);
        this.takeOutDate = takeOutDate;
        this.putBackDate = putBackDate;
        this.primerTubeID = primerTubeID;
        this.primerUID = primerUID;
        this.name = name;
        this.lotNr = lotNr;
        this.storageLocation = storageLocation;
        this.returnToStorage = returnToStorage;
        this.manufacturer = manufacturer;
    }
}
