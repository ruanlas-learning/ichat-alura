package com.example.ruan.ichat_alura.module;

import android.app.Application;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;

import com.example.ruan.ichat_alura.service.IChatService;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ChatModule {

    private Application app;

    public ChatModule(Application app) {
        this.app = app;
    }

    @Provides
    public IChatService getIChatService(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.34:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IChatService chatService = retrofit.create(IChatService.class);
        return chatService;
    }

    @Provides
    public Picasso getPicasso(){
        Picasso picasso = new Picasso.Builder(app).build();
        return picasso;
    }

    @Provides
    public EventBus getEventBus(){
        return EventBus.builder().build();
    }

    @Provides
    public InputMethodManager getInputMethodManager(){
        InputMethodManager inputMethodManager = (InputMethodManager) app.getSystemService(Context.INPUT_METHOD_SERVICE);
        return inputMethodManager;
    }
}
