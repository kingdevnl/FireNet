package packets;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nl.kingdev.firenet.common.interfaces.NetInput;
import nl.kingdev.firenet.common.interfaces.NetOutput;
import nl.kingdev.firenet.common.packet.Packet;

import java.io.IOException;



@AllArgsConstructor
@NoArgsConstructor
public class TestPacket extends Packet  {

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
        return "TestPacket{" +
                "msg='" + msg + '\'' +
                '}';
    }
}
