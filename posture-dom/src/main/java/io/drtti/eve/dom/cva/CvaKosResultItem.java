package io.drtti.eve.dom.cva;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author cwinebrenner
 */
public class CvaKosResultItem {

    @JsonProperty("type")
    private String type;

    @JsonProperty("id")
    private int id;

    @JsonProperty("label")
    private String label;

    @JsonProperty("icon")
    private String icon;

    @JsonProperty("kos")
    private boolean kos;

    @JsonProperty("eveid")
    private int eveid;

    @JsonProperty("ticker")
    @JsonInclude(Include.NON_NULL)
    private String ticker;

    @JsonProperty("npc")
    @JsonInclude(Include.NON_NULL)
    private boolean npc;

    @JsonProperty("corp")
    @JsonInclude(Include.NON_NULL)
    private CvaKosResultItem corp;

    @JsonProperty("alliance")
    @JsonInclude(Include.NON_NULL)
    private CvaKosResultItem alliance;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public boolean isKos() {
        return kos;
    }

    public void setKos(boolean kos) {
        this.kos = kos;
    }

    public int getEveid() {
        return eveid;
    }

    public void setEveid(int eveid) {
        this.eveid = eveid;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public boolean isNpc() {
        return npc;
    }

    public void setNpc(boolean npc) {
        this.npc = npc;
    }

    public CvaKosResultItem getCorp() {
        return corp;
    }

    public void setCorp(CvaKosResultItem corp) {
        this.corp = corp;
    }

    public CvaKosResultItem getAlliance() {
        return alliance;
    }

    public void setAlliance(CvaKosResultItem alliance) {
        this.alliance = alliance;
    }

}