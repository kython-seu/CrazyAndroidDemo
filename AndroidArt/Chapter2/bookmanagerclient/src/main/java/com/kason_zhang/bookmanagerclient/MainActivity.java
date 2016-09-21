package com.kason_zhang.bookmanagerclient;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kason.aidl.Book;
import com.kason.aidl.IBookManager;
import com.kason.aidl.IOnNewBookArrivedListener;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private TextView textView;
    private Button getBook;
    private Button addBook;
    private IBookManager iBookManager;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    Log.i(TAG, "receive the new book: "+msg.obj);
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }

        }
    };
    private IOnNewBookArrivedListener mOnNewBookArrivedListener =
            new IOnNewBookArrivedListener.Stub(){
                @Override
                public void onNewBookArrived(Book newBook) throws RemoteException {
                    handler.obtainMessage(1,newBook).sendToTarget();
                }
            };
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i(TAG, "onServiceConnected: get the IBinder from server");
            iBookManager = IBookManager.Stub.asInterface(service);
            try {
                iBookManager.registerListener(mOnNewBookArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            /*try {
                service.linkToDeath(mDeathRecipient,0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }*/
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            if (iBookManager != null){
                iBookManager = null;
            }
        }
    };
    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient(){
        @Override
        public void binderDied() {
            if(iBookManager == null){
                return;
            }
            iBookManager.asBinder().unlinkToDeath(mDeathRecipient,0);
            iBookManager = null;
            //给Binder设置死亡代理，当Binder死亡的时候就可以收到通知了。
            Log.i(TAG, "Binder dead");
            //重新绑定远程service;
            //bindService(intent)
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView)findViewById(R.id.textView);
        getBook = (Button)findViewById(R.id.button);
        addBook = (Button)findViewById(R.id.add);
        bindBookManager();
        getBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //IBookManager iBookManager = new I
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            List<Book> bookList = iBookManager.getBookList();
                            for(Book book : bookList){
                                Log.i(TAG, "Book Message:"+book);
                            }
                            //Log.i(TAG, "onClick: ");
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

            }
        });
        addBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Book book = new Book();
                Random random = new Random();
                int rad = random.nextInt(100);
                book.setBookId(100+rad);
                book.setBookName("Java"+rad);
                try {
                    iBookManager.addBook(book);
                    iBookManager.registerListener(mOnNewBookArrivedListener);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }*/
                //iBookManager.
            }
        });
    }

    private void bindBookManager() {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.kason_zhang.chapter2"
                ,"com.kason.aidl.BookManagerService"));
        bindService(intent,conn, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        if(iBookManager !=null&&iBookManager.asBinder().isBinderAlive()){
            try {
                iBookManager.unregisterListener(mOnNewBookArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        unbindService(conn);
        super.onDestroy();
    }
}
