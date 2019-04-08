package data.event;

public interface EventSource {

    void addEventListener(EventListener listener);

    void removeEventListener(EventListener listener);
}
