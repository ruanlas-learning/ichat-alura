package com.example.ruan.ichat_alura.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ruan.ichat_alura.R;
import com.example.ruan.ichat_alura.modelo.Mensagem;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MensagemAdapter extends BaseAdapter {

    private List<Mensagem> mensagens;
    private Activity activity;
    private int idDoCliente;

    @BindView(R.id.tv_texto)
    TextView texto;
    @BindView(R.id.iv_avatar_mensagem)
    ImageView avatar;

    @Inject
    Picasso picasso;

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

        ButterKnife.bind(this, linha);

//        Picasso.with(this).load("http://api.adorable.io/avatars/285/leonardo.png").into(avatar);

        Mensagem mensagem = getItem(position);

        picasso.with(activity).load("http://api.adorable.io/avatars/285/"+ mensagem.getId() +".png").into(avatar);

        if (idDoCliente != mensagem.getId()){
            linha.setBackgroundColor(Color.CYAN);
        }

        texto.setText(mensagem.getTexto());

        return linha;
    }
}
