package nl.kingdev.firenet.server;

import io.netty.channel.Channel;
import io.netty.channel.nio.NioEventLoopGroup;
import nl.kingdev.firenet.server.interfaces.ICallback;
import nl.kingdev.firenet.server.packet.PacketRegistry;

public interface IServer {

    void setup();
    void initChannel(Channel channel);

    void bind(String host, int port, ICallback<Channel> callback) throws InterruptedException;
    void close();




}
