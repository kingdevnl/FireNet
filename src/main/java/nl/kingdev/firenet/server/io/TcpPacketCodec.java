package nl.kingdev.firenet.server.io;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import nl.kingdev.firenet.server.packet.Packet;
import nl.kingdev.firenet.server.packet.PacketRegistry;

import java.util.List;

public class TcpPacketCodec extends ByteToMessageCodec<Packet> {

    private PacketRegistry packetRegistry;

    public TcpPacketCodec(PacketRegistry packetRegistry) {
        this.packetRegistry = packetRegistry;
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Packet packet, ByteBuf byteBuf) throws Exception {

        NetOutput output = new NetOutput(byteBuf);

        output.writeVarInt(packetRegistry.getPacketID(packet.getClass()));

        System.out.println("channelHandlerContext = " + channelHandlerContext + ", packet = " + packet + ", byteBuf = " + byteBuf + " > " + packetRegistry.getPacketID(packet.getClass()));

        packet.write(output);
    }


    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        NetInput input = new NetInput(in);

        int packetID = input.readVarInt();
        if (packetID != -1) {
            Packet packet = packetRegistry.createPacket(packetID);
            packet.read(input);
            System.out.println(packet.toString());
        }

    }
}