package data.grid.event.impl;

import data.grid.event.Event;
import data.grid.event.EventListener;
import data.grid.event.EventObject;

import java.util.ArrayList;
import java.util.List;

public class EventObjectImpl implements EventObject {

    private List<EventListener> listeners = new ArrayList<>();

    @Override
    public void addListener(EventListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(EventListener listener) {
        listeners.remove(listener);
    }

    @Override
    public void fireEvent(Event event) {
        listeners.forEach(x -> x.onEventFired(event));
    }
}
