package data.event.impl;

import data.event.Event;

public class EventImpl implements Event {

    private Object source;

    public EventImpl(Object source) {
        this.source = source;
    }

    @Override
    public Object getEventSource() {
        return source;
    }
}
