package com.kason_zhang.messengerclient;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final int MSG_FROM_CLIENT = 1;
    private static final int MSG_FROM_SERVER = 2;
    private Button send;
    private Messenger clientMessenger;
    private Messenger getReplyFromServerMessenger = new Messenger
            (new ClientMessageReceiveHandler());
    private class ClientMessageReceiveHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MSG_FROM_SERVER:
                    String receive_from_server = msg.getData().getString("response");
                    Log.i(TAG, "the data receive from server is : "+receive_from_server);
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }

        }
    }
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder iBinder) {
            Log.i(TAG, "get the binder from the server");
            clientMessenger = new Messenger(iBinder);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            if(clientMessenger != null){
                clientMessenger = null;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        send = (Button)findViewById(R.id.button);
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.kason_zhang.messengerserver"
                ,"com.kason.messenger.service.MyMessengerService"));
        bindService(intent,conn, Context.BIND_AUTO_CREATE);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message message = Message.obtain(null,MSG_FROM_CLIENT);
                Bundle bundle = new Bundle();
                bundle.putString("msg","hello, I am data from client");
                message.setData(bundle);
                message.replyTo = getReplyFromServerMessenger;
                try {
                    clientMessenger.send(message);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        unbindService(conn);
        super.onDestroy();
    }
}
