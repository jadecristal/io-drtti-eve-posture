package io.drtti.eve.ejb.util;

import io.drtti.eve.dom.location.ReportedPilotLocation;
import org.apache.log4j.Logger;

import javax.json.*;
import javax.json.stream.JsonGenerator;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author cwinebrenner
 */
public class DrttiJson {

    private final static Logger log = Logger.getLogger(DrttiJson.class);

    public static String DEBUG_pilotSystemJson(Set<ReportedPilotLocation> rpl) {

        JsonBuilderFactory jf = Json.createBuilderFactory(null);
        JsonObjectBuilder job = Json.createObjectBuilder();

        rpl.stream().forEach(e -> job.add(String.valueOf(e.getPilot().getCharacterName()), jf.createObjectBuilder()
                .add("solar_system_name", String.valueOf(e.getSolarSystem().getSolarSystemName()))
                .add("reported_time", e.getReportedTimeStamp().toString()))
        );
        return jsonFormat(job.build().toString());

    }

    public static String jsonFormat(String json) {
        StringWriter sw = new StringWriter();

        try (JsonReader jr = Json.createReader(new StringReader(json))) {
            JsonObject jo = jr.readObject();

            Map<String, Object> jsonWriterProperties = new HashMap<>(1);
            jsonWriterProperties.put(JsonGenerator.PRETTY_PRINTING, true);

            JsonWriterFactory jwf = Json.createWriterFactory(jsonWriterProperties);
            JsonWriter jw = jwf.createWriter(sw);

            jw.writeObject(jo);
            jw.close();
        } catch (Exception e) {
            log.error(e);
            return "javax.json PRETTY_PRINTING broke";
        }

        return sw.toString();
    }

}
