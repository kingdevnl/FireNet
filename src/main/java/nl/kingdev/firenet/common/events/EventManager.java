package nl.kingdev.firenet.common.events;

import lombok.AllArgsConstructor;
import nl.kingdev.firenet.common.events.impl.packet.PacketReceivedEvent;
import nl.kingdev.firenet.common.events.impl.packet.PacketSendEvent;

import java.util.ArrayList;
import java.util.List;


public class EventManager {

    @AllArgsConstructor
    private class EventData {
        protected Class<? extends Event> eventClass;
        protected EventHandler<Event> handler;
    }

    private List<EventData> handlers = new ArrayList<>();


    public void on(Class<? extends Event> clzz, EventHandler<? extends Event> eventHandler) {

        handlers.add(new EventData(clzz, (EventHandler<Event>) eventHandler));
    }

    public boolean call(Event event) {
        for(EventData eventData : handlers) {
            if(event.getClass().equals(eventData.eventClass)) {
                eventData.handler.fire(event);
                if(event.isCanceled()) {
                    return true;
                }
            }
        }

        return false;
    }

    public static void main(String[] args) {
        EventManager eventManager = new EventManager();


        eventManager.on(PacketSendEvent.class, (EventHandler<PacketSendEvent>) event -> {

            System.out.println("OnPacketSend!"+event.toString());
        });

        eventManager.on(PacketReceivedEvent.class, (EventHandler<PacketReceivedEvent>) event -> {
            event.setCanceled(true);


            System.out.println("OnPacketReceived!"+event.toString());
        });

        eventManager.on(PacketReceivedEvent.class, (EventHandler<PacketReceivedEvent>) event -> {

            System.out.println("OnPacketReceived2!"+event.toString());
        });

        eventManager.call(new PacketReceivedEvent(null));
        eventManager.call(new PacketSendEvent(null));

    }

}
