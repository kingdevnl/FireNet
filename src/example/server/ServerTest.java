package server;

import nl.kingdev.firenet.server.FireNetServer;
import packets.TestPacket;

public class ServerTest {

    public static void main(String[] args) {
        FireNetServer server = new FireNetServer();
        //Setup netty

        server.setup();

        try {
            //Bind the server to the port
            server.bind("localhost", 1337, () -> {

                //Register a packet with the id 0x08, with the type TestPacket
                server.getPacketRegistry().register(0x08, TestPacket.class);

                System.out.println("Ready! "+server.getChannel());

            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
