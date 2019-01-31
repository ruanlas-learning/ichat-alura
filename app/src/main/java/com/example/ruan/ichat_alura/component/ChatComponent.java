package com.example.ruan.ichat_alura.component;

import com.example.ruan.ichat_alura.activity.MainActivity;
import com.example.ruan.ichat_alura.adapter.MensagemAdapter;
import com.example.ruan.ichat_alura.module.ChatModule;

import dagger.Component;

@Component(modules = ChatModule.class)
public interface ChatComponent {

    void inject(MainActivity activity);
    void inject(MensagemAdapter mensagemAdapter);
}
