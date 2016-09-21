package com.kason.messenger.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

public class MyMessengerService extends Service {
    private static final String TAG = "MyMessengerService";
    private static final int MSG_FROM_CLIENT = 1;
    private static final int MSG_FROM_SERVER = 2;
    public MyMessengerService() {
    }

    private class ServerMessengerReceiveHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MSG_FROM_CLIENT:
                    //收到哭护短的数据；
                    Log.i(TAG, "MessengerServer get the request:"+msg.getData().getString("msg"));
                    //发送数据给客户端
                    Messenger reply_to_client_messenger = msg.replyTo;
                    Message message = Message.obtain(null,MSG_FROM_SERVER);
                    Bundle bundle = new Bundle();
                    bundle.putString("response","Hi,Client, I have got your message,Thank you");
                    message.setData(bundle);
                    try {
                        reply_to_client_messenger.send(message);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }

        }
    }
    private Messenger messenger = new Messenger(new ServerMessengerReceiveHandler());
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "going to return Binder to the client");
        return messenger.getBinder();
    }
}
