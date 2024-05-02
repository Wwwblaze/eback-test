package com.example.eback.service;

import com.example.eback.entity.Book;
import com.example.eback.entity.OrderItem;
import com.example.eback.entity.Shopcart;
import com.example.eback.entity.Orders;

import java.util.List;

public interface BookService {
    List<Book> getBooks();
    boolean deleteBook_(Book book);

    boolean addBook_(Book book);

    List<Book> searchbook(String str);

    List<Shopcart> getShopcarts(int userId);

    List<Orders> getOrders(int userId);

    List<OrderItem> getorderitem(int userId);

    List<OrderItem> getallorderitem();

    List<Orders> getallorder();

    void addshopcart(Shopcart shopcart);

    boolean deleteshopcart(Shopcart shopcart);

    boolean addorder(List<OrderItem> orders);

    Book findBookByIsbn(String isbn);

    Book findBookByName(String name);

    Book findBookById(Integer id);

    boolean ModifyBook(Book book);
    void deleteBookByIsbn(String isbn);

    boolean deleteBook(Book book);

    boolean addBook(Book book);

}
