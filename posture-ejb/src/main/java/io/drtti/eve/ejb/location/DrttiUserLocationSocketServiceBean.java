package io.drtti.eve.ejb.location;

import javax.ejb.Singleton;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;

/**
 * Adapted from Java EE 7 Essentials Ch. 16, O'Reilly
 * @author cwinebrenner
 */
@ServerEndpoint("/websocket/dulss")
@Singleton
public class DrttiUserLocationSocketServiceBean {

    private static final Set<Session> USER_SESSIONS = Collections.synchronizedSet(new HashSet<>());

    @OnOpen
    public void onOpen(Session client) {
        USER_SESSIONS.add(client);
    }

    @OnClose
    public void onClose(Session client) {
        USER_SESSIONS.remove(client);
    }

    @OnMessage
    public void message(String message) throws IOException, EncodeException {
        for (Session session : USER_SESSIONS) {
            session.getBasicRemote().sendObject(message);
        }
    }

}
