package nl.kingdev.firenet.server;

import io.netty.channel.Channel;
import nl.kingdev.firenet.common.interfaces.ICallback;

public interface IServer {

    void setup();
    void initChannel(Channel channel);

    void bind(String host, int port, ICallback<Channel> callback) throws InterruptedException;
    void close();




}
