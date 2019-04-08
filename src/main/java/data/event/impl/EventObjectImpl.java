package data.event.impl;

import data.event.Event;
import data.event.EventListener;
import data.event.EventObject;

import java.util.ArrayList;
import java.util.List;

public class EventObjectImpl implements EventObject {

    private List<EventListener> listeners = new ArrayList<>();

    @Override
    public void addEventListener(EventListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeEventListener(EventListener listener) {
        listeners.remove(listener);
    }

    @Override
    public void fireEvent(Event event) {
        listeners.forEach(x -> x.onEventFired(event));
    }
}
