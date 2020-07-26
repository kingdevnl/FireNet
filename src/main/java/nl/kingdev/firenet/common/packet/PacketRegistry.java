package nl.kingdev.firenet.common.packet;


import nl.kingdev.firenet.common.interfaces.IRegistry;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class PacketRegistry implements IRegistry<Integer, Class<? extends Packet>> {

    private Map<Integer, Class<? extends Packet>> INCOMING;
    private Map<Class<? extends Packet>, Integer> OUTGOING;

    public PacketRegistry() {
        this.INCOMING = new HashMap<>();
        this.OUTGOING = new HashMap<>();
    }

    public Class<? extends Packet> findPacketById(int id) {
        return INCOMING.get(id);
    }

    @Override
    public void register(Integer key, Class<? extends Packet> value) {
        INCOMING.put(key, value);
        OUTGOING.put(value, key);
        System.out.println("Registered packet key = " + key + ", value = " + value);
    }

    public Packet createPacket(int id) {


        Class<? extends Packet> packetClzz = findPacketById(id);
        if(packetClzz != null) {
            try {
                Constructor<? extends Packet> declaredConstructor = packetClzz.getDeclaredConstructor();
                declaredConstructor.setAccessible(true);
                return declaredConstructor.newInstance();
            } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public int getPacketID(Class<? extends Packet> aClass) {
        return OUTGOING.get(aClass);
    }
}
