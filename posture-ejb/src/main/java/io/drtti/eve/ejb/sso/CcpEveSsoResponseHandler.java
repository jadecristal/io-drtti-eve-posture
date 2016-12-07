package io.drtti.eve.ejb.sso;

import org.apache.log4j.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * @author cwinebrenner
 */
@WebServlet(urlPatterns = "/ccp/eve/sso")
public class CcpEveSsoResponseHandler extends HttpServlet {

    private final Logger log = Logger.getLogger(this.getClass());

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

        try (PrintWriter out = response.getWriter()) {
            out.println("<html>\n<head>\n<title>CCP EVE SSO Response Handler</title>\n</head>\n<body>\n");

            Map<String, String[]> requestParameters = request.getParameterMap();

            for (String key : requestParameters.keySet()) {
                out.println("<p>" + key + " : " + requestParameters.get(key).toString() + "</p>\n");
            }
            out.println("</body></html>");
        }
        catch (IOException ioe) {
            log.debug(ioe);
        }

    }

}
