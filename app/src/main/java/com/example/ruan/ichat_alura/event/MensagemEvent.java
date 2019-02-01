package com.example.ruan.ichat_alura.event;

import com.example.ruan.ichat_alura.modelo.Mensagem;

public class MensagemEvent {
    public Mensagem mensagem;

    public MensagemEvent(Mensagem mensagem) {
        this.mensagem = mensagem;
    }
}
