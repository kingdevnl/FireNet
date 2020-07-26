package nl.kingdev.firenet.client.interfaces;

import io.netty.channel.Channel;
import nl.kingdev.firenet.common.interfaces.ICallback;

public interface IClient {

    void setup();

    void initChannel(Channel channel);

    void connect(String host, int port, ICallback<Channel> callback);

    void close();

}
