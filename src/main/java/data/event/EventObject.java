package data.event;

/**
 * Additional abstraction layer to separate fireEvent(...) method from EventSource object. This is purely for
 * information-hiding purposes to prevent foreign objects from triggering events.
 */
public interface EventObject extends EventSource {

    void fireEvent(Event event);
}
