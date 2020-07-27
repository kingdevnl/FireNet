package nl.kingdev.firenet.common.events;

public abstract class Event {

    private boolean isCanceled;


    public void setCanceled(boolean canceled) {
        isCanceled = canceled;
    }

    public boolean isCanceled() {
        return isCanceled;
    }

    @Override
    public String toString() {
        return "Event{" +
                "isCanceled=" + isCanceled +
                '}';
    }
}
