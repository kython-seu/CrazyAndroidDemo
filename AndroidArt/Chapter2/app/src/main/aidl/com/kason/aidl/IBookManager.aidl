// IBookManager.aidl
package com.kason.aidl;
import com.kason.aidl.Book;
import com.kason.aidl.IOnNewBookArrivedListener;
// Declare any non-default types here with import statements

interface IBookManager {
    List<Book> getBookList();
    void addBook(in Book book);
    void registerListener(IOnNewBookArrivedListener listener);
    void unregisterListener(IOnNewBookArrivedListener listener);
}
