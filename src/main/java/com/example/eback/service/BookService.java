package com.example.eback.service;

import com.alibaba.fastjson.JSONObject;
import com.example.eback.entity.Book;
import com.example.eback.entity.OrderItem;
import com.example.eback.entity.Orders;
import com.example.eback.entity.Shopcart;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

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
