package com.example.eback.service;

import com.alibaba.fastjson.JSONObject;
import com.example.eback.entity.Book;
import com.example.eback.entity.OrderItem;
import com.example.eback.entity.Shopcart;
import com.example.eback.entity.Orders;
import org.springframework.web.bind.annotation.RequestBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public interface BookService {
    List<Book> getBooks();
    boolean deleteBook(Book book);

    boolean addBook(Book book);

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

    List<Map.Entry<String, Integer>> GetRankingList(JSONObject jsonObject) throws ParseException;

    List<Orders> sortOrdersByTime(JSONObject jsonObject) throws ParseException;
}
