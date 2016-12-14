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

    private static final Set<Session> userSessions = Collections.synchronizedSet(new HashSet<>());

    @OnOpen
    public void onOpen(Session client) {
        userSessions.add(client);
    }

    @OnClose
    public void onClose(Session client) {
        userSessions.remove(client);
    }

    @OnMessage
    public void message(String message) throws IOException, EncodeException {
        for (Session session : userSessions) {
            session.getBasicRemote().sendObject(message);
        }
    }

}
