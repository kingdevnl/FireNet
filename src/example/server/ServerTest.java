package server;

import nl.kingdev.firenet.common.events.EventHandler;
import nl.kingdev.firenet.server.events.client.ClientConnectEvent;
import nl.kingdev.firenet.common.events.impl.packet.PacketReceivedEvent;
import nl.kingdev.firenet.common.events.impl.packet.PacketSendEvent;
import nl.kingdev.firenet.server.FireNetServer;
import packets.Test2Packet;
import packets.TestPacket;

public class ServerTest {

    public static void main(String[] args) {
        FireNetServer server = new FireNetServer();
        //Setup netty

        server.setup();

        server.getEventManager().on(PacketReceivedEvent.class, (EventHandler<PacketReceivedEvent>) event -> {
            System.out.println("onPacketReceived: "+event.getPacket());

        });
        server.getEventManager().on(PacketSendEvent.class, (EventHandler<PacketSendEvent>) event -> {
            System.out.println("OnPacketSend: "+event.getPacket());
        });

        server.getEventManager().on(ClientConnectEvent.class, (EventHandler<ClientConnectEvent>) e -> {
            System.out.println("A new client connected: "+e.toString());
        });

        try {
            //Bind the server to the port
            server.bind("localhost", 1337, (channel) -> {

                //Register a packet with the id 0x08, with the type TestPacket
                server.getPacketRegistry().register(0x08, TestPacket.class);
                server.getPacketRegistry().register(0x09, Test2Packet.class);
                System.out.println("Ready! "+channel);


            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
