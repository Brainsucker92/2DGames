package data.grid.event;

public interface EventObject {

    void addListener(EventListener listener);

    void removeListener(EventListener listener);

    void fireEvent(Event event);
}
