package nl.kingdev.firenet.server.client;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.kingdev.firenet.common.events.impl.packet.PacketSendEvent;
import nl.kingdev.firenet.common.packet.Packet;
import nl.kingdev.firenet.server.FireNetServer;

@AllArgsConstructor
public class ClientContext {

    @Getter
    private ChannelHandlerContext context;


    private FireNetServer server;


    public void sendPacket(Packet packet) throws InterruptedException {
        if(!server.getEventManager().call(new PacketSendEvent(packet))) {
            context.channel().writeAndFlush(packet).sync();
        }
    }



}
