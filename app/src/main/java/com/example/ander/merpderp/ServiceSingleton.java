package com.example.ander.merpderp;

/**
 * Created by ander on 8/14/2016.
 */

public class ServiceSingleton {

    public String mIntermText;

    public static ServiceSingleton instance;
    private DataChangeListener dataChangeListener;

    // creating singleton
    private ServiceSingleton() {
    }

    // creating singleton
    public static ServiceSingleton getInstance() {
        if (instance == null) {
            instance = new ServiceSingleton();
        }
        return instance;
    }

    // creating listener
    public void setListener(DataChangeListener listener) {
        dataChangeListener = listener;
    }

    // creating listener
    public interface DataChangeListener {
        void onDataChanged(String text);
    }

    private void notifyDataChanged(String text) {
        if (dataChangeListener != null) {
            dataChangeListener.onDataChanged(text);
        }
    }

    public void updateText(String text) {
        String oldText = mIntermText;
        mIntermText = text;
        notifyDataChanged(oldText);
    }

    public String getMyString(){
        return mIntermText;
    }

}