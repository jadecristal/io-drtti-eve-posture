package io.drtti.eve.ejb.util;

import io.drtti.eve.dom.ccp.Pilot;
import io.drtti.eve.dom.ccp.SolarSystem;
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

    public static String DEBUG_pilotSystemJson(Map<Pilot, SolarSystem> pilotSystem) {

        JsonObjectBuilder job = Json.createObjectBuilder();
        Set<Map.Entry<Pilot,SolarSystem>> ps = pilotSystem.entrySet();
        ps.stream().forEach(e -> job.add(String.valueOf(e.getKey().getCharacterName()), String.valueOf(e.getValue().getSolarSystemName())));
        return jsonFormat(job.build().toString());

//        StringBuilder sb = new StringBuilder().append("{\n");
//
//        for (Pilot p : pilotSystem.keySet()) {
//            sb.append("{")
//                    .append("\"pilot_name\":\"").append(p.getCharacterName()).append("\",")
//                    .append("\"solar_system_name\":\"").append(pilotSystem.get(p).getSolarSystemName()).append("\"}\n");
//        }

//        for (Map.Entry<Pilot, SolarSystem> psEntry : pilotSystem.entrySet()) {
//            job.add("pilotLocation", jbf.createObjectBuilder()
//                    .add("pilot", psEntry.getKey().getCharacterName())
//                    .add("solar_system", psEntry.getValue().getSolarSystemName()));
//        }
//
//        return jsonFormat(job.build().toString());
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
