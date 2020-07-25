package packets;

import nl.kingdev.firenet.server.io.NetInput;
import nl.kingdev.firenet.server.io.NetOutput;
import nl.kingdev.firenet.server.packet.Packet;

import java.io.IOException;

public class TestPacket extends Packet  {

    private String msg;

    public TestPacket() {
    }

    public TestPacket(String msg) {
        this.msg = msg;
    }

    @Override
    public void read(NetInput buff) {

        try {
            this.msg = buff.readString();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void write(NetOutput buff) {
        try {
            buff.writeString(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "TestPacket{" +
                "msg='" + msg + '\'' +
                '}';
    }
}