package io.drtti.eve.dom.core;

import javax.persistence.*;

/**
 * @author cwinebrenner
 */
@Entity
@Table(name = "SolarSystem")
public class SolarSystem {

    @Id
    @GeneratedValue
    @Column(name = "ID", columnDefinition = "BIGINT")
    private Long id;

    @Column(name = "SolarSystemID", columnDefinition = "BIGINT")
    private Long solarSystemId;

    @Column(name = "SolarSystemName")
    private String solarSystemName;

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
