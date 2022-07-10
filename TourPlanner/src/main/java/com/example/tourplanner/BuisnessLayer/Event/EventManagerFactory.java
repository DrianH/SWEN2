package com.example.tourplanner.BuisnessLayer.Event;

public class EventManagerFactory {
    private static IEventManager em;

    //singleton
    public static IEventManager getEM(){
        if (em==null)
            em = new EventManager();
        return em;
    }
}
