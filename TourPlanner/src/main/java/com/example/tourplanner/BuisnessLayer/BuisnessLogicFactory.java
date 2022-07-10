package com.example.tourplanner.BuisnessLayer;

public class BuisnessLogicFactory {
    private static IBuisnessLogic bl;

    public static IBuisnessLogic getBuisnessLogic(){
        if (bl==null)
            bl=new BuisnessLogic();
        return bl;
    }
}
