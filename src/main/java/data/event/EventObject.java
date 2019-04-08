package data.event;

public interface EventObject extends EventSource {

    void fireEvent(Event event);
}
