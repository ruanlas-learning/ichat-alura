package com.example.ruan.ichat_alura.callback;

import com.example.ruan.ichat_alura.activity.MainActivity;
import com.example.ruan.ichat_alura.modelo.Mensagem;

import retrofit2.Call;
import retrofit2.Response;

public class OuvirMensagensCallback implements retrofit2.Callback<Mensagem> {

    private MainActivity activity;

    public OuvirMensagensCallback(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onResponse(Call<Mensagem> call, Response<Mensagem> response) {
        if (response.isSuccessful()){
            Mensagem mensagem = response.body();
            activity.colocaNaList(mensagem);
        }
    }

    @Override
    public void onFailure(Call<Mensagem> call, Throwable t) {
        activity.ouvirMensagens();
    }
}
