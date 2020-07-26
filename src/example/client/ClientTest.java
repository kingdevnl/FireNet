package client;

import nl.kingdev.firenet.client.FireNetClient;
import packets.Test2Packet;
import packets.TestPacket;

public class ClientTest {
    public static void main(String[] args) {
        FireNetClient client = new FireNetClient();
        client.setup();
        client.connect("localhost", 1337, c -> {
            try {
                client.getPacketRegistry().register(0x08, TestPacket.class);
                client.getPacketRegistry().register(0x09, Test2Packet.class);

                c.writeAndFlush(new TestPacket("1337 pro hacker")).sync();
                c.writeAndFlush(new Test2Packet(new String[]{"hello", "world", "from java"})).sync();


            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        });
    }
}
