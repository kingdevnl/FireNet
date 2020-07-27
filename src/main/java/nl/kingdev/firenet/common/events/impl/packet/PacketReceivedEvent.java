package nl.kingdev.firenet.common.events.impl.packet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.kingdev.firenet.common.events.Event;
import nl.kingdev.firenet.common.packet.Packet;

@AllArgsConstructor
public class PacketReceivedEvent extends Event {
    @Getter
    private Packet packet;
}
