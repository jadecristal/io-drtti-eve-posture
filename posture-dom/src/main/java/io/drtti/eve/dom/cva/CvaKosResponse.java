package io.drtti.eve.dom.cva;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * @author cwinebrenner
 */
public class CvaKosResponse {

    @JsonProperty("total")
    private int total;

    @JsonProperty("code")
    private int code;

    @JsonProperty("message")
    private String message;

    @JsonProperty("results")
    private List<CvaKosResultItem> results;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<CvaKosResultItem> getResults() {
        return results;
    }

    public void setResults(List<CvaKosResultItem> results) {
        this.results = results;
    }

}