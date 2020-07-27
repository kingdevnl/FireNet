package nl.kingdev.firenet.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.Getter;
import nl.kingdev.firenet.common.events.EventManager;
import nl.kingdev.firenet.common.interfaces.ICallback;
import nl.kingdev.firenet.common.packets.HelloPacket;
import nl.kingdev.firenet.server.client.ClientContext;
import nl.kingdev.firenet.server.io.ServerClientHandler;
import nl.kingdev.firenet.common.io.TcpPacketCodec;
import nl.kingdev.firenet.common.packet.PacketRegistry;

import java.util.HashMap;
import java.util.Map;

public class FireNetServer implements IServer {

    @Getter
    private NioEventLoopGroup group = new NioEventLoopGroup();

    @Getter
    private Channel channel;

    private final ServerBootstrap serverBootstrap = new ServerBootstrap();

    @Getter
    private PacketRegistry packetRegistry = new PacketRegistry();

    @Getter
    private EventManager eventManager = new EventManager();


    @Getter
    private Map<String, ClientContext> clients = new HashMap<>();

    @Override
    public void setup() {

        serverBootstrap.group(group).channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) {
                        FireNetServer.this.initChannel(ch);
                    }
                });
    }

    //Initialize a channel with the client
    @Override
    public void initChannel(Channel channel) {
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast("codec", new TcpPacketCodec(getPacketRegistry()));
        pipeline.addLast("handler", new ServerClientHandler(this));
    }


    @Override
    public void bind(String host, int port, ICallback<Channel> callback) throws InterruptedException {

        new Thread(() -> {

            ChannelFuture channelFuture = serverBootstrap.localAddress(host, port).bind();
            channel = channelFuture.channel();

            if (callback != null) {
                packetRegistry.register(0x1337, HelloPacket.class);
                callback.run(channel);
            }
        }).start();

    }

    @Override
    public void close() {
        channel.close().awaitUninterruptibly();
        group.shutdownGracefully();


    }


}
