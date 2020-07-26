package client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import nl.kingdev.firenet.common.io.TcpPacketCodec;
import nl.kingdev.firenet.common.packet.PacketRegistry;
import packets.Test2Packet;
import packets.TestPacket;


public class ClientTest {

    private static Channel channel;

    private  static boolean isConnected() {
        return channel != null && channel.isOpen();

    }
    public static void main(String[] args) {
        NioEventLoopGroup group = new NioEventLoopGroup();


        PacketRegistry packetRegistry = new PacketRegistry();


        Bootstrap bootstrap = new Bootstrap();
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.handler(new ChannelInitializer<Channel>() {
            @Override
            protected void initChannel(Channel c) throws Exception {
                packetRegistry.register(0x08, TestPacket.class);
                packetRegistry.register(0x09, Test2Packet.class);

                ChannelPipeline pipeline = c.pipeline();
                pipeline.addLast("codec", new TcpPacketCodec(packetRegistry));
                channel = c;

            }

        }).group(group);

        Runnable connectTask = new Runnable() {
            @Override
            public void run() {
                try {
                    bootstrap.remoteAddress("localhost", 1337);

                    ChannelFuture future = bootstrap.connect().sync();
                    if(future.isSuccess()) {
                        System.out.println("Waiting for connection...");

                        while(!isConnected()) {
                            try {
                                Thread.sleep(5);
                            } catch(InterruptedException e) {
                            }
                        }
                        System.out.println("Connected");

                        channel.writeAndFlush(new TestPacket("1337 pro hacker")).sync();
                        channel.writeAndFlush(new Test2Packet(new String[]{"hello", "world", "from java"})).sync();

                        channel.close().syncUninterruptibly();
                        group.shutdownGracefully();

                    }
                } catch(Throwable t) {
                    t.printStackTrace();
                }
            }


        };
        new Thread(connectTask).start();


    }
}
