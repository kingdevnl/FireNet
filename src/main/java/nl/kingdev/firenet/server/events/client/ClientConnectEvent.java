package nl.kingdev.firenet.server.events.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.kingdev.firenet.common.events.Event;
import nl.kingdev.firenet.server.client.ClientContext;

@AllArgsConstructor
public class ClientConnectEvent extends Event {
    @Getter
    private ClientContext clientContext;

    @Override
    public String toString() {
        return "ClientConnectEvent{" +
                "clientContext=" + clientContext +
                '}';
    }
}
