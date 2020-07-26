package nl.kingdev.firenet.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.Getter;
import nl.kingdev.firenet.client.interfaces.IClient;
import nl.kingdev.firenet.client.io.ClientClientHandler;
import nl.kingdev.firenet.common.interfaces.ICallback;
import nl.kingdev.firenet.common.io.TcpPacketCodec;
import nl.kingdev.firenet.common.packet.PacketRegistry;
import nl.kingdev.firenet.common.packets.HelloPacket;
import packets.Test2Packet;
import packets.TestPacket;

import java.net.SocketAddress;

public class FireNetClient implements IClient {

    @Getter
    private Channel channel;


    private NioEventLoopGroup group = new NioEventLoopGroup();
    private Bootstrap bootstrap;

    @Getter
    private PacketRegistry packetRegistry = new PacketRegistry();

    @Override
    public void setup() {
        bootstrap = new Bootstrap();

        bootstrap.group(group).channel(NioSocketChannel.class).handler(new ChannelInitializer<Channel>() {
            @Override
            protected void initChannel(Channel ch) throws Exception {
                FireNetClient.this.channel = ch;
                FireNetClient.this.initChannel(ch);
            }
        });

    }


    @Override
    public void initChannel(Channel channel) {
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast("codec", new TcpPacketCodec(packetRegistry));
        pipeline.addLast("handler", new ClientClientHandler(this));
    }

    @Override
    public void connect(String host, int port, ICallback<Channel> callback) {
        try {
            bootstrap.remoteAddress(host, port);
            ChannelFuture future = bootstrap.connect().sync();
            System.out.println("here");
            if(future.isSuccess()) {
                while (!isConnected()) {
                    System.out.println("waiting for connection...");
                    Thread.sleep(5);
                }
                System.out.println("Connected to "+host + ":"+port);

                packetRegistry.register(0x1337, HelloPacket.class);
                callback.run(channel);
            }
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


}
