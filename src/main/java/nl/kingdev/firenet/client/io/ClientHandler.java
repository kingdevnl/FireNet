package nl.kingdev.firenet.client.io;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import nl.kingdev.firenet.client.FireNetClient;
import nl.kingdev.firenet.common.events.impl.packet.PacketReceivedEvent;
import nl.kingdev.firenet.common.packet.Packet;


public class ClientHandler extends SimpleChannelInboundHandler<Packet> {

    private final FireNetClient client;

    public ClientHandler(FireNetClient fireNetClient) {
        this.client = fireNetClient;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        System.out.println("ClientClientHandler.channelActive");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet packet) throws Exception {
        client.getEventManager().call(new PacketReceivedEvent(packet));

    }

}
