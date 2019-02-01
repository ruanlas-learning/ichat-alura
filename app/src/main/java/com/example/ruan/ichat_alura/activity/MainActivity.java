package com.example.ruan.ichat_alura.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.PersistableBundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.ruan.ichat_alura.app.ChatApplication;
import com.example.ruan.ichat_alura.callback.EnviarMensagemCallback;
import com.example.ruan.ichat_alura.callback.OuvirMensagensCallback;
import com.example.ruan.ichat_alura.R;
import com.example.ruan.ichat_alura.adapter.MensagemAdapter;
import com.example.ruan.ichat_alura.component.ChatComponent;
import com.example.ruan.ichat_alura.event.FailureEvent;
import com.example.ruan.ichat_alura.event.MensagemEvent;
import com.example.ruan.ichat_alura.modelo.Mensagem;
import com.example.ruan.ichat_alura.service.IChatService;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

public class MainActivity extends AppCompatActivity {

    private int idDoCliente = 1;

    @BindView(R.id.botao_enviar)
    Button button;
    @BindView(R.id.lv_mensagens)
    ListView listaDeMensagens;
    @BindView(R.id.et_texto)
    EditText editText;
    @BindView(R.id.iv_avatar_usuario)
    ImageView avatar;

    private List<Mensagem> mensagens;
//    private ChatService chatService;
    @Inject
    IChatService chatService;
    private ChatComponent component;

    @Inject
    Picasso picasso;

    @Inject
    EventBus eventBus;

//    private BroadcastReceiver receiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            Mensagem mensagem = (Mensagem) intent.getSerializableExtra("mensagem");
//            colocaNaList(mensagem);
//        }
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        picasso.with(this).load("http://api.adorable.io/avatars/285/"+ idDoCliente +".png").into(avatar);

        ChatApplication app = (ChatApplication) getApplication();
        component = app.getComponent();
        component.inject(this);


        if (savedInstanceState != null){
            mensagens = (List<Mensagem>) savedInstanceState.getSerializable("mensagens");
        }else {
//        mensagens = Arrays.asList(new Mensagem(1, "olá alunos de android"), new Mensagem(2, "Oi"));
            mensagens = new ArrayList<>();
        }

        MensagemAdapter adapter = new MensagemAdapter(mensagens, this, idDoCliente);
        listaDeMensagens.setAdapter(adapter);

//        chatService = new ChatService(this);

//        Call<Mensagem> call = chatService.ouvirMensagens();
//        call.enqueue(new OuvirMensagensCallback(eventBus,this));
        ouvirMensagens(new MensagemEvent(null));

        eventBus.register(this);

//        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
//        localBroadcastManager.registerReceiver(receiver, new IntentFilter("nova_mensagem"));
    }

    @OnClick(R.id.botao_enviar)
    public void enviarMensagem(){
        chatService.enviar(new Mensagem(idDoCliente, editText.getText().toString())).enqueue(new EnviarMensagemCallback());
        editText.getText().clear();
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    @Subscribe
    public void colocaNaList(MensagemEvent mensagemEvent){
        Mensagem mensagem = mensagemEvent.mensagem;
        mensagens.add(mensagem);
        MensagemAdapter adapter = new MensagemAdapter(mensagens, this, idDoCliente);
        listaDeMensagens.setAdapter(adapter);

//        chatService.ouvirMensagens().enqueue(new OuvirMensagensCallback(this));
//        ouvirMensagens();
    }

    @Subscribe
    public void ouvirMensagens(MensagemEvent mensagemEvent){
        chatService.ouvirMensagens().enqueue(new OuvirMensagensCallback(eventBus, this));
    }

    @Subscribe
    public void lidaCom(FailureEvent failureEvent){
        ouvirMensagens(new MensagemEvent(null));
    }

    @Override
    protected void onStop() {
        super.onStop();

//        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
//        localBroadcastManager.unregisterReceiver(receiver);
        eventBus.unregister(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable("mensagens", (ArrayList<Mensagem>) mensagens);
    }

//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//        if (savedInstanceState != null){
//            mensagens = (List<Mensagem>) savedInstanceState.getSerializable("mensagens");
//        }else {
//            mensagens = new ArrayList<>();
//        }
//    }
}
