package com.example.ruan.ichat_alura.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ruan.ichat_alura.R;
import com.example.ruan.ichat_alura.modelo.Mensagem;

import java.util.List;

public class MensagemAdapter extends BaseAdapter {

    private List<Mensagem> mensagens;
    private Activity activity;
    private int idDoCliente;

    public MensagemAdapter(List<Mensagem> mensagens, Activity activity, int idDoCliente) {
        this.mensagens = mensagens;
        this.activity = activity;
        this.idDoCliente = idDoCliente;
    }

    @Override
    public int getCount() {
        return mensagens.size();
    }

    @Override
    public Mensagem getItem(int position) {
        return mensagens.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View linha = activity.getLayoutInflater().inflate(R.layout.mensagem, parent, false);
        TextView texto = linha.findViewById(R.id.tv_texto);

        Mensagem mensagem = getItem(position);

        if (idDoCliente != mensagem.getId()){
            linha.setBackgroundColor(Color.CYAN);
        }

        texto.setText(mensagem.getTexto());

        return linha;
    }
}
