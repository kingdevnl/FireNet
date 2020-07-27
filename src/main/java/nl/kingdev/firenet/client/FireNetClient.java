package nl.kingdev.firenet.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.Getter;
import nl.kingdev.firenet.client.interfaces.IClient;
import nl.kingdev.firenet.client.io.ClientHandler;
import nl.kingdev.firenet.common.events.EventManager;
import nl.kingdev.firenet.common.events.impl.packet.PacketSendEvent;
import nl.kingdev.firenet.common.interfaces.ICallback;
import nl.kingdev.firenet.common.io.TcpPacketCodec;
import nl.kingdev.firenet.common.packet.Packet;
import nl.kingdev.firenet.common.packet.PacketRegistry;
import nl.kingdev.firenet.common.packets.HelloPacket;
import nl.kingdev.firenet.server.FireNetServer;
import packets.TestPacket;

public class FireNetClient implements IClient {




    @Getter
    private Channel channel;


    private NioEventLoopGroup group = new NioEventLoopGroup();
    private Bootstrap bootstrap;

    @Getter
    private PacketRegistry packetRegistry = new PacketRegistry();
    @Getter
    private EventManager eventManager = new EventManager();

    @Override
    public void setup() {
        bootstrap = new Bootstrap();

        bootstrap.group(group).channel(NioSocketChannel.class).handler(new ChannelInitializer<Channel>() {
            @Override
            protected void initChannel(Channel ch) throws Exception {
                FireNetClient.this.initChannel(ch);
            }
        });

    }


    @Override
    public void initChannel(Channel channel) {
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast("codec", new TcpPacketCodec(packetRegistry));
        pipeline.addLast("handler", new ClientHandler(this));
    }

    @Override
    public void connect(String host, int port, ICallback<Channel> callback) {
        try {
            bootstrap.remoteAddress(host, port);
            this.channel = bootstrap.connect().sync().channel();
            packetRegistry.register(0x1337, HelloPacket.class);
            callback.run(channel);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private boolean isConnected() {
        return channel != null && channel.isOpen();
    }

    @Override
    public void close() {

        channel.close().syncUninterruptibly();
        group.shutdownGracefully();
    }


    public void sendPacket(Packet packet) throws InterruptedException {
        if(!eventManager.call(new PacketSendEvent(packet))) {
            channel.writeAndFlush(packet).sync();
        }
    }
}
