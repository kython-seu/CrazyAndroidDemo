package com.kason.aidl;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.SystemClock;
import android.util.Log;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class BookManagerService extends Service {
    private static final String TAG = "BookManagerService";
    private AtomicBoolean mIsServiceDestroyed = new AtomicBoolean(false);
    public BookManagerService() {
    }
    private CopyOnWriteArrayList<Book> mBookArrayList = new CopyOnWriteArrayList<>();
    //private CopyOnWriteArrayList<IOnNewBookArrivedListener> mListenerList
            //= new CopyOnWriteArrayList<>();
    private MyRemoteCallbackList<IOnNewBookArrivedListener> mRemoteCallbackList
            = new MyRemoteCallbackList<>();
    class MyRemoteCallbackList<IOnNewBookArrivedListener> extends RemoteCallbackList{
        @Override
        public void onCallbackDied(IInterface callback) {
            Log.i(TAG, "--------onCallbackDied------------");
            super.onCallbackDied(callback);
        }
    }
    @Override
    public void onCreate() {
        mBookArrayList.add(new Book(1,"Java"));
        mBookArrayList.add(new Book(2,"Python"));
        new Thread(new ServiceWork()).start();
        super.onCreate();
    }

    //private ArrayList<Book> mBookArrayList = new ArrayList<>();
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        //throw new UnsupportedOperationException("Not yet implemented");
        /*int check = checkCallingOrSelfPermission("com.kason_zhang.chapter2.ACCESS_BOOK_SERVICE");
        Log.i("Valid", "check: "+check);
        if(check == PackageManager.PERMISSION_DENIED){
            Log.i("Valid", "Permission denied");
            return null;
        }*/
        return myBinder;
    }
    private IBookManager.Stub myBinder = new IBookManager.Stub(){
        @Override
        public List<Book> getBookList() throws RemoteException {
            //simulation the time-consuming task;
            SystemClock.sleep(5000);
            Log.i(TAG, "get the request: getBookList from Client");
            return mBookArrayList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            Log.i(TAG, "get the request: addBook from Client");
            mBookArrayList.add(book);
        }

        @Override
        public void registerListener(IOnNewBookArrivedListener listener) throws RemoteException {

            /*if(!mListenerList.contains(listener)){
                mListenerList.add(listener);
            }else{
                Log.i(TAG, "already has added the listener");
            }*/
            mRemoteCallbackList.register(listener);
            int i = mRemoteCallbackList.beginBroadcast();
            Log.i(TAG, "registerListener size: "+i);
            mRemoteCallbackList.finishBroadcast();
        }

        @Override
        public void unregisterListener(IOnNewBookArrivedListener listener) throws RemoteException {
            /*if(mListenerList.contains(listener)){
                mListenerList.remove(listener);
                Log.i(TAG, "Listener remove success");
            }else{
                Log.i(TAG, "not find the listener, fail");
            }*/
            mRemoteCallbackList.unregister(listener);
            int i = mRemoteCallbackList.beginBroadcast();
            Log.i(TAG, "the listener remains : "+i);
            mRemoteCallbackList.finishBroadcast();
        }

        @Override
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            int check = checkCallingOrSelfPermission("com.kason_zhang.chapter2.ACCESS_BOOK_SERVICE");
            if(check == PackageManager.PERMISSION_DENIED){
                Log.i("Valid", "Permission is wrong");
                return false;
            }
            String packageName = null;
            String[] packages = getPackageManager().getPackagesForUid(
                    getCallingUid());
            if (packages != null && packages.length > 0) {
                packageName = packages[0];
            }
            Log.d("Valid", "onTransact: " + packageName);
            if (!packageName.startsWith("com.kason_zhang")) {
                Log.i("Valid", "Permission is wrong");
                return false;
            }

            return super.onTransact(code, data, reply, flags);
        }
    };


    @Override
    public void onDestroy() {
        mIsServiceDestroyed.set(true);
        super.onDestroy();
    }
    private class ServiceWork implements Runnable{
        @Override
        public void run() {
            //服务没有停止；
            while(!mIsServiceDestroyed.get()){
                Log.i(TAG, "going to create a book");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int bookid = mBookArrayList.size()+1;
                Book newBook = new Book(bookid,"new book#"+bookid);
                onNewBookArrived(newBook);
            }
        }
    }

    private void onNewBookArrived(Book newBook) {
        mBookArrayList.add(newBook);
        int number = mRemoteCallbackList.beginBroadcast();
        Log.i(TAG, "newBook is arrived, going to notify listeners : "+number);
        for(int i=0;i<number;i++){
            //IOnNewBookArrivedListener iOnNewBookArrivedListener = mListenerList.get(i);
            IOnNewBookArrivedListener iOnNewBookArrivedListener
                    = (IOnNewBookArrivedListener)mRemoteCallbackList.getBroadcastItem(i);
            Log.i(TAG, "notify listener:"+iOnNewBookArrivedListener);
            if(iOnNewBookArrivedListener != null) {
                try {
                    iOnNewBookArrivedListener.onNewBookArrived(newBook);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
        mRemoteCallbackList.finishBroadcast();
    }
}
