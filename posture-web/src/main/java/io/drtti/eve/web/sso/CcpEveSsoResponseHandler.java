package io.drtti.eve.web.sso;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.drtti.eve.dom.esi.EsiCharacterLocation;
import io.drtti.eve.dom.esi.EsiSolarSystem;
import io.drtti.eve.dom.sso.CcpEveSsoAuthenticatedPilot;
import io.drtti.eve.dom.sso.CcpEveSsoConfig;
import io.drtti.eve.dom.sso.CcpEveSsoCredential;
import io.drtti.eve.ejb.esi.CcpEveEsiBean;
import io.drtti.eve.ejb.sso.CcpEveSsoBean;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.json.*;
import javax.json.stream.JsonGenerator;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author cwinebrenner
 */
@WebServlet(urlPatterns = {"/ccp/eve/sso/", "/ccp/eve/sso"})
public class CcpEveSsoResponseHandler extends HttpServlet {

    private final Logger log = Logger.getLogger(this.getClass());

    @EJB
    CcpEveSsoBean ssoBean;

    @EJB
    CcpEveEsiBean esiBean;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        CcpEveSsoCredential credential = (CcpEveSsoCredential) request.getSession().getAttribute(CcpEveSsoConfig.DRTTI_EVE_SSO_CREDENTIAL_KEY);

        if (credential == null) {
            log.info("No credential found in Session");
            if (ssoBean.containsAuthCallback(request)) {
                try {
                    log.info("Found CCP EVE SSO Auth Callback in request; processing...");

                    credential = ssoBean.processAuthCallback(request);
                    log.info("Got credential from CCP EVE SSO endpoint");

                    request.getSession().setAttribute(CcpEveSsoConfig.DRTTI_EVE_SSO_CREDENTIAL_KEY, credential);
                    log.info("Saved credential in Session");

                    String authenticatedPilotJson = ssoBean.getAuthenticatedPilot(credential);
                    log.info("Got authenticated pilot from CCP EVE SSO verify endpoint");

                    request.getSession().setAttribute(CcpEveSsoConfig.DRTTI_EVE_AUTHENTICATED_PILOT_KEY, jsonPrettyPrint(authenticatedPilotJson));
                    log.info("Saved authenticated pilot JSON in Session");

                    ObjectMapper om = new ObjectMapper();
                    CcpEveSsoAuthenticatedPilot authenticatedPilot = om.readValue(authenticatedPilotJson, CcpEveSsoAuthenticatedPilot.class);
                    log.info("ObjectMapper: mapped CcpEveSsoAuthenticatedPilot: " + authenticatedPilot.getCharacterId() + "," + authenticatedPilot.getCharacterName());

                    request.getSession().setAttribute(CcpEveSsoConfig.DRTTI_EVE_PILOT_NAME_KEY, authenticatedPilot.getCharacterName());
                    request.getSession().setAttribute(CcpEveSsoConfig.DRTTI_EVE_PILOT_CHARACTER_ID_KEY, authenticatedPilot.getCharacterId());
                    log.info("Saved authenticated pilot data in Session");

                    // TODO: move this somewhere else
                    EsiCharacterLocation esiCharacterLocation = esiBean.getCharacterLocation(credential, authenticatedPilot.getCharacterId());
                    EsiSolarSystem esiSolarSystem = esiBean.getSolarSystem(esiCharacterLocation.getSolarSystemId());
                    request.getSession().setAttribute(CcpEveSsoConfig.DRTTI_EVE_PILOT_LOCATION_KEY, esiSolarSystem.getSolarSystemName());
                    log.info("Saved authenticated pilot location solar system name in Session as " + CcpEveSsoConfig.DRTTI_EVE_PILOT_LOCATION_KEY);

                    log.info("CCP EVE SSO Auth Callback processed; redirecting to home...");
                    response.sendRedirect(request.getContextPath() + "/home.jsp");
                } catch (IOException ioe) {
                    log.error(ioe);
                }
            } else {
                log.info("No CCP EVE SSO Auth callback found in request; redirecting to SSO endpoint...");
                response.sendRedirect(ssoBean.getAuthRequestURI(request.getSession()));
            }
        } else {
            log.info("Direct access to CCP EVE SSO Callback endpoint while already logged in; redirecting to home...");
            response.sendRedirect(request.getContextPath() + "/home.jsp");
        }
    }

    // TODO: move to a utility class
    private String jsonPrettyPrint(String json) {
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
