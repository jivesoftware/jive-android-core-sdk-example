package com.jivesoftware.example.listenable;

/**
 * Created by mark.schisler on 8/21/14.
 */
public interface ITypeListenable {
    public void setListener(IListener listener, Enum e);
    public void setListener(IValueListener<?> listener, Enum e);
}
