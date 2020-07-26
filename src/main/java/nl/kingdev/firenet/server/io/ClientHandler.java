package nl.kingdev.firenet.server.io;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import nl.kingdev.firenet.server.FireNetServer;
import nl.kingdev.firenet.common.packet.Packet;
import nl.kingdev.firenet.server.client.ClientContext;


public class ClientHandler extends SimpleChannelInboundHandler<Packet> {


    private final FireNetServer server;




    public ClientHandler(FireNetServer server) {
        this.server = server;
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        ClientContext clientContext = new ClientContext(ctx);
        server.getClients().put(ctx.name(), clientContext);
    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        server.getClients().remove(ctx.name());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet msg) throws Exception {
        System.out.println("channelRead0 ctx = " + ctx + ", msg = " + msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
