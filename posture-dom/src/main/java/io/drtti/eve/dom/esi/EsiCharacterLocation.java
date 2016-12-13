package io.drtti.eve.dom.esi;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * CCP EVE ESI Character Location
 * Might contain either stationId or structureId.
 * @author cwinebrenner
 */
public class EsiCharacterLocation {

    @JsonProperty("solar_system_id")
    private Long solarSystemId;

    @JsonProperty("station_id")
    private Long stationId;

    @JsonProperty("structure_id")
    private Long structureId;

    public EsiCharacterLocation() {
    }

    public Long getSolarSystemId() {
        return solarSystemId;
    }

    public void setSolarSystemId(Long solarSystemId) {
        this.solarSystemId = solarSystemId;
    }

    public Long getStationId() {
        return stationId;
    }

    public void setStationId(Long stationId) {
        this.stationId = stationId;
    }

    public Long getStructureId() {
        return structureId;
    }

    public void setStructureId(Long structureId) {
        this.structureId = structureId;
    }

}
