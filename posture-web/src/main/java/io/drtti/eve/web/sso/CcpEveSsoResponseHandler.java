package io.drtti.eve.web.sso;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.drtti.eve.dom.ccp.Pilot;
import io.drtti.eve.dom.core.DrttiUser;
import io.drtti.eve.dom.esi.EsiCharacterLocation;
import io.drtti.eve.dom.esi.EsiSolarSystem;
import io.drtti.eve.dom.sso.CcpEveSsoAuthenticatedPilot;
import io.drtti.eve.dom.sso.CcpEveSsoConfig;
import io.drtti.eve.dom.sso.CcpEveSsoCredential;
import io.drtti.eve.ejb.client.DrttiUserRegistrationServiceBean;
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
    DrttiUserRegistrationServiceBean dursBean;

    @EJB
    CcpEveEsiBean esiBean;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        DrttiUser user = (DrttiUser) request.getSession().getAttribute(CcpEveSsoConfig.DRTTI_USER_KEY);

        if (user == null) {
            log.debug("No credential found in Session");

            if (ssoBean.containsAuthCallback(request)) {
                log.debug("Found CCP EVE SSO Auth Callback in request; processing...");

                try {

                    CcpEveSsoCredential credential = ssoBean.processAuthCallback(request);
                    log.debug("Got credential from CCP EVE SSO endpoint");

                    String authenticatedPilotJson = ssoBean.getAuthenticatedPilot(credential);
                    log.debug("Got authenticated pilot from CCP EVE SSO verify endpoint");

                    CcpEveSsoAuthenticatedPilot authenticatedPilot = new ObjectMapper().readValue(authenticatedPilotJson, CcpEveSsoAuthenticatedPilot.class);
                    log.debug("ObjectMapper: mapped CcpEveSsoAuthenticatedPilot: " + authenticatedPilot.getCharacterId() + "," + authenticatedPilot.getCharacterName());

                    user = new DrttiUser();
                    user.setCredential(credential);
                    user.setPilot(new Pilot(authenticatedPilot.getCharacterId(), authenticatedPilot.getCharacterName()));

                    dursBean.register(user);
                    log.info("Logged in user registered with ");

                    request.getSession().setAttribute(CcpEveSsoConfig.DRTTI_USER_KEY, user);
                    log.debug("Logged in DrttiUser saved to session as " + CcpEveSsoConfig.DRTTI_USER_KEY);

                    // AFTER HERE, USE DRTTI_USER FOR MAKING AUTH CALLS!
                    EsiCharacterLocation esiCharacterLocation = esiBean.getEsiCharacterLocation(user.getCredential(), user.getPilot().getCharacterId());
                    EsiSolarSystem esiSolarSystem = esiBean.getEsiSolarSystem(esiCharacterLocation.getSolarSystemId());
                    request.getSession().setAttribute(CcpEveSsoConfig.DRTTI_EVE_PILOT_LOCATION_KEY, esiSolarSystem.getSolarSystemName());
                    log.debug("Saved user pilot location solar system name in Session as " + CcpEveSsoConfig.DRTTI_EVE_PILOT_LOCATION_KEY);

                    // TODO: Remove after other stuff is on the front page
                    request.getSession().setAttribute(CcpEveSsoConfig.DRTTI_EVE_AUTHENTICATED_PILOT_KEY, jsonPrettyPrint(authenticatedPilotJson));
                    log.debug("Saved authenticated pilot JSON in Session");

                    log.debug("CCP EVE SSO Auth Callback processed; redirecting to home...");
                    response.sendRedirect(request.getContextPath() + "/home.jsp");

                } catch (Exception e) {
                    log.error("DRTTI: SSO: FAILURE WHILE PROCSESING OAUTH2 LOGIN!", e);
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
