package com.example.ruan.ichat_alura.app;

import android.app.Application;

import com.example.ruan.ichat_alura.component.ChatComponent;
import com.example.ruan.ichat_alura.component.DaggerChatComponent;

public class ChatApplication extends Application {
    private ChatComponent component;

    @Override
    public void onCreate() {
//        super.onCreate();
        component = DaggerChatComponent.builder().build();
    }

    public ChatComponent getComponent(){
        return component;
    }
}
