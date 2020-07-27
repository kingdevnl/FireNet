package nl.kingdev.firenet.server.io;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import nl.kingdev.firenet.server.events.client.ClientConnectEvent;
import nl.kingdev.firenet.server.events.client.ClientDisconnectedEvent;
import nl.kingdev.firenet.common.events.impl.packet.PacketReceivedEvent;
import nl.kingdev.firenet.common.packets.HelloPacket;
import nl.kingdev.firenet.server.FireNetServer;
import nl.kingdev.firenet.common.packet.Packet;
import nl.kingdev.firenet.server.client.ClientContext;


public class ServerClientHandler extends SimpleChannelInboundHandler<Packet> {


    private final FireNetServer server;


    public ServerClientHandler(FireNetServer server) {
        this.server = server;
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);

        ClientContext clientContext = new ClientContext(ctx, server);
        server.getClients().put(ctx.channel().id().asShortText(), clientContext);

        server.getEventManager().call(new ClientConnectEvent(clientContext));

        clientContext.sendPacket(new HelloPacket("Hello client " + ctx.channel().id().asShortText()));
    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        ClientContext clientContext = server.getClients().get(ctx.channel().id().asShortText());
        server.getEventManager().call(new ClientDisconnectedEvent(clientContext));
        server.getClients().remove(ctx.channel().id().asShortText());

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet packet) throws Exception {
        server.getEventManager().call(new PacketReceivedEvent(packet));

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
