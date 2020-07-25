package nl.kingdev.firenet.server.io;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import nl.kingdev.firenet.server.FireNetServer;
import nl.kingdev.firenet.server.packet.Packet;


public class ClientHandler extends SimpleChannelInboundHandler<Packet> {

    private FireNetServer server;

    public ClientHandler(FireNetServer server) {
        this.server = server;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        System.out.println("New client "+ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet msg) throws Exception {

    }
}