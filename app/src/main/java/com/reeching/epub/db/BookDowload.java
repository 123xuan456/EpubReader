package com.reeching.epub.db;

/**
 * Created by 绍轩 on 2017/11/20.
 */

public class BookDowload extends DataSupportCompat<BookDowload>{
    @PrimaryKey
    private String bookName;//书名
    private byte[] book;//

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public byte[] getBook() {
        return book;
    }

    public void setBook(byte[] book) {
        this.book = book;
    }
}
