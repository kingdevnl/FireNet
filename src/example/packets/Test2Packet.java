package packets;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nl.kingdev.firenet.common.io.NetInput;
import nl.kingdev.firenet.common.io.NetOutput;
import nl.kingdev.firenet.common.packet.Packet;

import java.io.IOException;
import java.util.Arrays;

@AllArgsConstructor
@NoArgsConstructor
public class Test2Packet extends Packet {
    private String[] messages;


    @Override
    public void read(NetInput buff) {

        try {
            int size = buff.readVarInt();
            this.messages = new String[size];
            for (int i = 0; i < size; i++) {
                messages[i] = buff.readString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void write(NetOutput buff) {
        try {
            buff.writeVarInt(messages.length);
            for (String s : messages) {
                buff.writeString(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Test2Packet{" +
                "messages=" + Arrays.toString(messages) +
                '}';
    }
}
