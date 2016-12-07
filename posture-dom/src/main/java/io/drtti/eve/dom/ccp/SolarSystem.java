package io.drtti.eve.dom.ccp;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author cwinebrenner
 */
@Entity
@Table(name = "SolarSystem")
public class SolarSystem implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "ID", columnDefinition = "BIGINT")
    private Long id;

    @Column(name = "SolarSystemID", columnDefinition = "BIGINT")
    private Long solarSystemId;

    @Column(name = "SolarSystemName")
    private String solarSystemName;

    public SolarSystem() {
    }

    public SolarSystem(Long solarSystemId, String solarSystemName) {
        this.solarSystemId = solarSystemId;
        this.solarSystemName = solarSystemName;
    }

    public SolarSystem(String solarSystemName) {
        this.solarSystemName = solarSystemName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSolarSystemId() {
        return solarSystemId;
    }

    public void setSolarSystemId(Long solarSystemId) {
        this.solarSystemId = solarSystemId;
    }

    public String getSolarSystemName() {
        return solarSystemName;
    }

    public void setSolarSystemName(String solarSystemName) {
        this.solarSystemName = solarSystemName;
    }

}
