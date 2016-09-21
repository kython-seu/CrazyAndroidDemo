// IOnNewBookArrivedListener.aidl
package com.kason.aidl;
import com.kason.aidl.Book;
// Declare any non-default types here with import statements

interface IOnNewBookArrivedListener {
    void onNewBookArrived(in Book newBook);
}
