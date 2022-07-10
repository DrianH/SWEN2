package com.example.tourplanner.BuisnessLayer.Event;

public interface IEventManager {
    void subscribe(String e, IEventListener l);
    void notify(String e, Object o);
    void unsubscribe(String e, IEventListener l);
}
