package io.drtti.eve.web.sso;

import io.drtti.eve.ejb.sso.CcpEveSsoOAuth2SessionBean;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author cwinebrenner
 */
@WebServlet(urlPatterns = {"/ccp/eve/sso/", "/ccp/eve/sso"})
public class CcpEveSsoResponseHandler extends HttpServlet {

    private final Logger log = Logger.getLogger(this.getClass());

    @EJB
    CcpEveSsoOAuth2SessionBean ccpSession;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ccpSession.bindSession(request.getSession());

        if (!ccpSession.isAuthenticated()) {
            response.sendRedirect(ccpSession.getAuthRequestURI());
        }

        if (ccpSession.containsAuthCallback(request)) {
            ccpSession.processAuthCallback(request);
        } else {

            try (PrintWriter out = response.getWriter()) {
                out.write("<!doctype html>\n" +
                        "\n" +
                        "<html lang=\"en\">\n" +
                        "<head>\n" +
                        "    <meta charset=\"utf-8\" />\n" +
                        "    <title>drtti.io</title>\n" +
                        "    <link rel=\"stylesheet\" href=\"https://fonts.googleapis.com/css?family=GFS+Didot|Tinos|Tangerine\" />\n" +
                        "    <link rel=\"stylesheet\" href=\"/css/main.css\" />\n" +
                        "</head>\n" +
                        "\n" +
                        "<body>\n" +
                        "<p class=\"f-corner a\">&alpha;</p>\n" +
                        "<p class=\"f-corner o\">&Omega;</p>\n" +
                        "<div class=\"f-center\">\n" +
                        "    <p class=\"h-main\"><strong>d</strong>istributed <strong>r</strong>eal<strong>t</strong>ime <strong>t</strong>arget <strong>i</strong>ntelligence</p>\n" +
                        "</div>\n" +
                        "<div class=\"f-center\">\n" +
                        "    <p class=\"t-center\">");
                out.write(ccpSession.crestGetAuthenticatedPilot());
                out.write("    </p>\n" +
                        "</div>\n" +
                        "</body>\n" +
                        "</html>");
            }

        }
    }

}
