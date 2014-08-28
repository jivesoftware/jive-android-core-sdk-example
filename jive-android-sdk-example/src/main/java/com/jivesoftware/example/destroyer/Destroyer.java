package com.jivesoftware.example.destroyer;


/**
 * Created by mark.schisler on 4/30/14.
 */
public class Destroyer {
    public static void destroy(IDestroyable... destroyables) {
        for ( IDestroyable destroyable : destroyables ) {
            if ( destroyable != null ) {
                destroyable.destroy();
            }
        }
    }
    public static <T> T destroyAndReturn(IDestroyable destroyable, IDestroyable returnable) {
        if ( destroyable != null ) {
            destroyable.destroy();
        }
        return (T)returnable;
    }
}
