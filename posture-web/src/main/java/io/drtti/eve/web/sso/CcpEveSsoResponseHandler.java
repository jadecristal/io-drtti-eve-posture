package io.drtti.eve.web.sso;

import io.drtti.eve.dom.sso.CcpEveSsoOAuth2Config;
import io.drtti.eve.dom.sso.CcpEveSsoOAuth2Credential;
import io.drtti.eve.ejb.sso.CcpEveSsoOAuth2Bean;
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
    CcpEveSsoOAuth2Bean ssoBean;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        CcpEveSsoOAuth2Credential credential = (CcpEveSsoOAuth2Credential) request.getSession().getAttribute(CcpEveSsoOAuth2Config.DRTTI_EVE_CREDENTIAL_COOKIE_KEY);

        if (credential == null) {
            log.info("No credential found in Session");
            if (ssoBean.containsAuthCallback(request)) {
                log.info("Found CCP EVE SSO Auth Callback in request; processing...");
                credential = ssoBean.processAuthCallback(request);
                log.info("Got credential from CCP EVE SSO endpoint");
                request.getSession().setAttribute(CcpEveSsoOAuth2Config.DRTTI_EVE_CREDENTIAL_COOKIE_KEY, credential);
                log.info("Saved credential in Session");
                request.getSession().setAttribute(CcpEveSsoOAuth2Config.DRTTI_EVE_PILOT_NAME_COOKIE_KEY, jsonPrettyPrint(ssoBean.crestGetAuthenticatedPilot(credential)));
                log.info("Saved authenticated pilot name in Session");
                log.info("CCP EVE SSO Auth Callback processed; redirecting to home...");
                response.sendRedirect(request.getContextPath() + "/home.jsp");
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
        }
        catch (Exception e) {
            log.error(e);
            return "javax.json PRETTY_PRINTING broke";
        }

        return sw.toString();
    }

}
