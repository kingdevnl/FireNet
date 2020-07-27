package client;

import nl.kingdev.firenet.client.FireNetClient;
import nl.kingdev.firenet.common.events.EventHandler;
import nl.kingdev.firenet.common.events.impl.packet.PacketReceivedEvent;
import nl.kingdev.firenet.common.packets.HelloPacket;
import packets.Test2Packet;
import packets.TestPacket;

public class ClientTest {
    public static void main(String[] args) {
        FireNetClient client = new FireNetClient();

        client.setup();

        client.getEventManager().on(PacketReceivedEvent.class, (EventHandler<PacketReceivedEvent>) event -> {
            System.out.println("onPacketReceived: " + event.getPacket());
        });

        client.connect("localhost", 1337, c -> {
            try {
                client.getPacketRegistry().register(0x08, TestPacket.class);
                client.getPacketRegistry().register(0x09, Test2Packet.class);


                client.sendPacket(new TestPacket("1337 pro hacker"));
                client.sendPacket(new Test2Packet(new String[]{"hello", "world", "from java"}));


            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        });
    }
}
