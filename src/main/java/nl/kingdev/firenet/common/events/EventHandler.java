package nl.kingdev.firenet.common.events;

public interface EventHandler<T extends Event> {

    void fire(T event);
}
