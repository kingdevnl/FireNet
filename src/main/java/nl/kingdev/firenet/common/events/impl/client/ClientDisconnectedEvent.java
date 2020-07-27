package nl.kingdev.firenet.common.events.impl.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.kingdev.firenet.common.events.Event;
import nl.kingdev.firenet.server.client.ClientContext;

@AllArgsConstructor
public class ClientDisconnectedEvent extends Event {
    @Getter
    private ClientContext clientContext;

    @Override
    public String toString() {
        return "ClientDisconnectedEvent{" +
                "clientContext=" + clientContext +
                '}';
    }
}
