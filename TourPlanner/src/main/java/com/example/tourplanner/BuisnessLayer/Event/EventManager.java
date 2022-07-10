package com.example.tourplanner.BuisnessLayer.Event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventManager implements IEventManager{
    private final Map<String, List<IEventListener>> ls = new HashMap<>();

    @Override
    public void subscribe(String e, IEventListener l) {
        List<IEventListener> es = ls.getOrDefault(e, new ArrayList<>());
        es.add(l);
        ls.put(e, es);
    }

    @Override
    public void notify(String e, Object o) {
        for(IEventListener l : ls.getOrDefault(e, new ArrayList<>())) {
            l.update(e, o);
        }
    }

    @Override
    public void unsubscribe(String e, IEventListener l) {
        List<IEventListener> es = ls.getOrDefault(e, new ArrayList<>());
        es.remove(l);
        ls.put(e, es);
    }
}
