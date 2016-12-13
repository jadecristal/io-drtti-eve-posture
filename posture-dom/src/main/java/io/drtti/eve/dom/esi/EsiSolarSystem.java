package io.drtti.eve.dom.esi;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * CCP EVE ESI Solar System
 * @author cwinebrenner
 */
public class EsiSolarSystem {

    @JsonProperty("solar_system_name")
    private String solarSystemName;

    public EsiSolarSystem() {
    }

    public EsiSolarSystem(String solarSystemName) {
        this.solarSystemName = solarSystemName;
    }

    public String getSolarSystemName() {
        return solarSystemName;
    }

    public void setSolarSystemName(String solarSystemName) {
        this.solarSystemName = solarSystemName;
    }

}
