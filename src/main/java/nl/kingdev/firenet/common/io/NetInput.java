package nl.kingdev.firenet.common.io;

import io.netty.buffer.ByteBuf;

import java.io.IOException;
import java.util.UUID;

public class NetInput {

    private ByteBuf buf;

    public NetInput(ByteBuf buf) {
        this.buf = buf;
    }

    
    public boolean readBoolean() throws IOException {
        return this.buf.readBoolean();
    }

    
    public byte readByte() throws IOException {
        return this.buf.readByte();
    }

    
    public int readUnsignedByte() throws IOException {
        return this.buf.readUnsignedByte();
    }

    
    public short readShort() throws IOException {
        return this.buf.readShort();
    }

    
    public int readUnsignedShort() throws IOException {
        return this.buf.readUnsignedShort();
    }

    
    public char readChar() throws IOException {
        return this.buf.readChar();
    }

    
    public int readInt() throws IOException {
        return this.buf.readInt();
    }

    
    public int readVarInt() throws IOException {
        int value = 0;
        int size = 0;
        int b;
        while(((b = this.readByte()) & 0x80) == 0x80) {
            value |= (b & 0x7F) << (size++ * 7);
            if(size > 5) {
                throw new IOException("VarInt too long (length must be <= 5)");
            }
        }

        return value | ((b & 0x7F) << (size * 7));
    }

    
    public long readLong() throws IOException {
        return this.buf.readLong();
    }

    
    public long readVarLong() throws IOException {
        int value = 0;
        int size = 0;
        int b;
        while(((b = this.readByte()) & 0x80) == 0x80) {
            value |= (b & 0x7F) << (size++ * 7);
            if(size > 10) {
                throw new IOException("VarLong too long (length must be <= 10)");
            }
        }

        return value | ((b & 0x7F) << (size * 7));
    }

    
    public float readFloat() throws IOException {
        return this.buf.readFloat();
    }

    
    public double readDouble() throws IOException {
        return this.buf.readDouble();
    }

    
    public byte[] readBytes(int length) throws IOException {
        if(length < 0) {
            throw new IllegalArgumentException("Array cannot have length less than 0.");
        }

        byte b[] = new byte[length];
        this.buf.readBytes(b);
        return b;
    }

    
    public int readBytes(byte[] b) throws IOException {
        return this.readBytes(b, 0, b.length);
    }

    
    public int readBytes(byte[] b, int offset, int length) throws IOException {
        int readable = this.buf.readableBytes();
        if(readable <= 0) {
            return -1;
        }

        if(readable < length) {
            length = readable;
        }

        this.buf.readBytes(b, offset, length);
        return length;
    }

    
    public short[] readShorts(int length) throws IOException {
        if(length < 0) {
            throw new IllegalArgumentException("Array cannot have length less than 0.");
        }

        short s[] = new short[length];
        for(int index = 0; index < length; index++) {
            s[index] = this.readShort();
        }

        return s;
    }

    
    public int readShorts(short[] s) throws IOException {
        return this.readShorts(s, 0, s.length);
    }

    
    public int readShorts(short[] s, int offset, int length) throws IOException {
        int readable = this.buf.readableBytes();
        if(readable <= 0) {
            return -1;
        }

        if(readable < length * 2) {
            length = readable / 2;
        }

        for(int index = offset; index < offset + length; index++) {
            s[index] = this.readShort();
        }

        return length;
    }

    
    public int[] readInts(int length) throws IOException {
        if(length < 0) {
            throw new IllegalArgumentException("Array cannot have length less than 0.");
        }

        int i[] = new int[length];
        for(int index = 0; index < length; index++) {
            i[index] = this.readInt();
        }

        return i;
    }

    
    public int readInts(int[] i) throws IOException {
        return this.readInts(i, 0, i.length);
    }

    
    public int readInts(int[] i, int offset, int length) throws IOException {
        int readable = this.buf.readableBytes();
        if(readable <= 0) {
            return -1;
        }

        if(readable < length * 4) {
            length = readable / 4;
        }

        for(int index = offset; index < offset + length; index++) {
            i[index] = this.readInt();
        }

        return length;
    }

    
    public long[] readLongs(int length) throws IOException {
        if(length < 0) {
            throw new IllegalArgumentException("Array cannot have length less than 0.");
        }

        long l[] = new long[length];
        for(int index = 0; index < length; index++) {
            l[index] = this.readLong();
        }

        return l;
    }

    
    public int readLongs(long[] l) throws IOException {
        return this.readLongs(l, 0, l.length);
    }

    
    public int readLongs(long[] l, int offset, int length) throws IOException {
        int readable = this.buf.readableBytes();
        if(readable <= 0) {
            return -1;
        }

        if(readable < length * 2) {
            length = readable / 2;
        }

        for(int index = offset; index < offset + length; index++) {
            l[index] = this.readLong();
        }

        return length;
    }

    
    public String readString() throws IOException {
        int length = this.readVarInt();
        byte bytes[] = this.readBytes(length);
        return new String(bytes, "UTF-8");
    }

    
    public UUID readUUID() throws IOException {
        return new UUID(this.readLong(), this.readLong());
    }

    
    public int available() throws IOException {
        return this.buf.readableBytes();
    }
}
