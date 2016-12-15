package io.drtti.eve.dom.sso;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author cwinebrenner
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CcpEveSsoAuthenticatedPilot {

    @JsonProperty("CharacterID")
    private Long characterId;

    @JsonProperty("CharacterName")
    private String characterName;

    @JsonProperty("CharacterOwnerHash")
    private String characterOwnerHash;

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

    public String getCharacterOwnerHash() {
        return characterOwnerHash;
    }

    public void setCharacterOwnerHash(String characterOwnerHash) {
        this.characterOwnerHash = characterOwnerHash;
    }

}
