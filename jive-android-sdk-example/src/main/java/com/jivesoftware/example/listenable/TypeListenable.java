package com.jivesoftware.example.listenable;

import com.jivesoftware.example.destroyer.IDestroyable;

import java.util.HashMap;

/**
 * Created by mark.schisler on 8/20/14.
 */
public class TypeListenable implements ITypeListenable, IDestroyable {
    HashMap<Enum, IListener> listenerHashMap = new HashMap<Enum, IListener>();
    HashMap<Enum, IValueListener<?>> valueListenerHashMap = new HashMap<Enum, IValueListener<?>>();

    @Override
    public void destroy() {
        listenerHashMap.clear();
        valueListenerHashMap.clear();
    }

    public void setListener(IListener listener, Enum e) {
        listenerHashMap.put(e, listener);
    }

    public void setListener(IValueListener<?> listener, Enum e) {
        valueListenerHashMap.put(e, listener);
    }

    public void post(Enum e) {
        IListener listener = listenerHashMap.get(e);
        if ( listener != null ) {
            listener.onPost();
        }
    }

    public <T> void post(T value, Enum e) {
        IValueListener<T> listener = (IValueListener<T>) valueListenerHashMap.get(e);
        if ( listener != null) {
            listener.onPost(value);
        }
    }
}
