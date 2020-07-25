package nl.kingdev.firenet.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.Getter;
import nl.kingdev.firenet.server.io.ClientHandler;
import nl.kingdev.firenet.server.io.TcpPacketCodec;
import nl.kingdev.firenet.server.packet.PacketRegistry;

public class FireNetServer implements IServer {

    @Getter
    private NioEventLoopGroup group = new NioEventLoopGroup();

    @Getter
    private Channel channel;

    private final ServerBootstrap serverBootstrap = new ServerBootstrap();

    @Getter
    private PacketRegistry packetRegistry = new PacketRegistry();


    @Override
    public void setup() {

        serverBootstrap.group(group).channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) {
                        channel = ch;
                        FireNetServer.this.initChannel(channel);
                    }
                });

    }

    private static final StringEncoder encoder = new StringEncoder();
    private static final StringDecoder decoder = new StringDecoder();

    @Override
    public void initChannel(Channel channel) {
        ChannelPipeline pipeline = channel.pipeline();

        pipeline.addLast("codec", new TcpPacketCodec(getPacketRegistry()));
        pipeline.addLast("handler", new ClientHandler(this));

    }


    @Override
    public void bind(String host, int port, Runnable callback) throws InterruptedException {

        new Thread(() -> {

            ChannelFuture channelFuture = serverBootstrap.localAddress(host, port).bind();
            channel = channelFuture.channel();
            if (callback != null) {
                callback.run();
            }
        }).start();

    }

    @Override
    public void close() {
        channel.close().awaitUninterruptibly();
        group.shutdownGracefully();


    }


}
