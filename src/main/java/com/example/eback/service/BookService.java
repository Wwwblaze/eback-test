package com.example.eback.service;

import com.alibaba.fastjson.JSONObject;
import com.example.eback.entity.Book;
import com.example.eback.entity.OrderItem;
import com.example.eback.entity.Shopcart;
import com.example.eback.entity.Orders;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public interface BookService {
    List<Book> getBooks();
    boolean deleteBook_(Book book);

    boolean addBook_(Book book);

    List<Book> searchbook(String str);

    List<Orders> getOrders(int userId);

    List<OrderItem> getorderitem(int userId);

    List<OrderItem> getallorderitem();

    List<Orders> getallorder();

    List<Shopcart> getShopcarts(int userId);

    void addshopcart(Shopcart shopcart);

    boolean deleteshopcart(Shopcart shopcart);

    boolean deleteAllshopcart(int userId);

    boolean editshopcart(int userId, int bookId, int num);

    boolean addorder(List<OrderItem> orders);

    Book findBookByIsbn(String isbn);

    Book findBookByName(String name);

    Book findBookById(Integer id);

    boolean ModifyBook(Book book);
    void deleteBookByIsbn(String isbn);

    boolean deleteBook(Book book);

    boolean addBook(Book book);

    List<Map.Entry<String, Integer>> GetRankingList(JSONObject jsonObject) throws ParseException;

    List<Orders> sortOrdersByTime(JSONObject jsonObject) throws ParseException;
}
