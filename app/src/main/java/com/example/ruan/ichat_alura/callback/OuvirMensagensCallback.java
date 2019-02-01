package com.example.ruan.ichat_alura.callback;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.example.ruan.ichat_alura.activity.MainActivity;
import com.example.ruan.ichat_alura.event.MensagemEvent;
import com.example.ruan.ichat_alura.modelo.Mensagem;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Response;

public class OuvirMensagensCallback implements retrofit2.Callback<Mensagem> {

    private EventBus eventBus;
    private Context context;

    public OuvirMensagensCallback(EventBus eventBus, Context context) {
        this.eventBus = eventBus;
        this.context = context;
    }

    @Override
    public void onResponse(Call<Mensagem> call, Response<Mensagem> response) {
        if (response.isSuccessful()){
            Mensagem mensagem = response.body();
//            activity.colocaNaList(mensagem);

//            Intent intent = new Intent("nova_mensagem");
//            intent.putExtra("mensagem", mensagem);
//
//            LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(context);
//            localBroadcastManager.sendBroadcast(intent);
            eventBus.post(new MensagemEvent(mensagem));
        }
    }

    @Override
    public void onFailure(Call<Mensagem> call, Throwable t) {
//        activity.ouvirMensagens();
    }
}
