package nl.kingdev.firenet.server.client;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.kingdev.firenet.common.packet.Packet;

@AllArgsConstructor
public class ClientContext {

    @Getter
    private ChannelHandlerContext context;




    public void sendPacket(Packet packet) throws InterruptedException {
        context.channel().writeAndFlush(packet).sync();
    }

}
