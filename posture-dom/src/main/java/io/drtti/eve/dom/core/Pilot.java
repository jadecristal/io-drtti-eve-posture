package io.drtti.eve.dom.core;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author cwinebrenner
 */
@Entity
@Table(name = "Pilot")
public class Pilot implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "ID", columnDefinition = "BIGINT")
    private Long id;

    @Column(name = "CharacterID", columnDefinition = "BIGINT")
    private Long characterId;

    @Column(name = "CharacterName")
    private String characterName;

    public Pilot() {
    }

    public Pilot(Long characterId, String characterName) {
        this.characterId = characterId;
        this.characterName = characterName;
    }

    public Pilot(String characterName) {
        this.characterName = characterName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCharacterId() {
        return characterId;
    }

    public void setCharacterId(Long characterId) {
        this.characterId = characterId;
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

}
