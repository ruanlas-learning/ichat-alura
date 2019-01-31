package com.example.ruan.ichat_alura.module;

import com.example.ruan.ichat_alura.service.IChatService;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ChatModule {

    @Provides
    public IChatService getIChatService(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.34:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IChatService chatService = retrofit.create(IChatService.class);
        return chatService;
    }
}
