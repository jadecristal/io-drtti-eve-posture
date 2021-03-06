package io.drtti.eve.web.sso;

import io.drtti.eve.dom.core.DrttiUser;
import io.drtti.eve.dom.sso.CcpEveSsoConfig;
import io.drtti.eve.ejb.client.DrttiUserRegistrationServiceBean;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author cwinebrenner
 */
@WebServlet(urlPatterns = {"/ccp/eve/sso/logout/", "/ccp/eve/sso/logout"})
public class CcpEveSsoLogoutHandler extends HttpServlet {

    @EJB
    private DrttiUserRegistrationServiceBean dursBean;

    private final Logger log = Logger.getLogger(this.getClass());

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        DrttiUser user = (DrttiUser) request.getSession().getAttribute(CcpEveSsoConfig.DRTTI_USER_KEY);
        if (user != null) {
            dursBean.unregister(user);
            log.info("Logged out user " + user.getPilot().getCharacterName() + " unregistered with DrttiUserRegistrationService");
        }

        request.getSession().invalidate();
        response.sendRedirect(request.getContextPath() + "/index.jsp");
    }

}
