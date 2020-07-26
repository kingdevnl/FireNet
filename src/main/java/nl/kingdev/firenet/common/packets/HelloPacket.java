package nl.kingdev.firenet.common.packets;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nl.kingdev.firenet.common.io.NetInput;
import nl.kingdev.firenet.common.io.NetOutput;
import nl.kingdev.firenet.common.packet.Packet;

import java.io.IOException;

@NoArgsConstructor
@AllArgsConstructor
public class HelloPacket extends Packet {
    private String msg;



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
        return "HelloPacket{" +
                "msg='" + msg + '\'' +
                '}';
    }
}
