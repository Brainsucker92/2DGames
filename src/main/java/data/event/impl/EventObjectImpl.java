package data.event.impl;

import java.util.HashSet;
import java.util.Set;

import data.event.Event;
import data.event.EventListener;
import data.event.EventObject;

public class EventObjectImpl implements EventObject {

    private final Set<EventListener> listeners = new HashSet<>();

    @Override
    public void addEventListener(EventListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeEventListener(EventListener listener) {
        listeners.remove(listener);
    }

    @Override
    public boolean hasEventListener(EventListener listener) {
        return listeners.contains(listener);
    }

    @Override
    public void fireEvent(Event event) {
        listeners.forEach(x -> x.onEventFired(event));
    }
}
