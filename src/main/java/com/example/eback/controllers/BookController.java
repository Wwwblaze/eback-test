package com.example.eback.controllers;

import com.alibaba.fastjson.JSONObject;
import com.example.eback.entity.Book;
import com.example.eback.entity.Shopcart;
import com.example.eback.entity.Orders;
import com.example.eback.entity.OrderItem;
import com.example.eback.repository.OrderItemRepository;
import com.example.eback.repository.OrderRepository;
import com.example.eback.service.BookService;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import java.util.*;
import java.util.stream.Collectors;

@RestController
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private OrderItemRepository orderItemRepository;


    @RequestMapping("/getbooks")
    public List<Book> getBooks() {
        return bookService.getBooks();
    }

    @CrossOrigin
    @PostMapping("/searchbook")
    public List<Book> searchbook(@RequestBody JSONObject jsonObject){
        String keyword = jsonObject.getString("search");
        return bookService.searchbook(keyword);
    }

    @CrossOrigin
    @PostMapping("/deletebook")
    public boolean deleteBook(@RequestBody Book book){
        return bookService.deleteBook_(book);
    }

    @CrossOrigin
    @PostMapping("/addbook")
    public boolean addBook(@RequestBody Book book){
        return bookService.addBook_(book);
    }


    @CrossOrigin
    @PostMapping("/getorder")
    public List<Orders> getorder(@RequestBody int userId){
        return bookService.getOrders(userId);
    }

    @CrossOrigin
    @PostMapping("/getorderitem")
    public List<OrderItem> getorderitem(@RequestBody int userId){
        return bookService.getorderitem(userId);
    }

    @CrossOrigin
    @PostMapping("/getallorderitem")
    public List<OrderItem> getallorderitem(){
        return bookService.getallorderitem();
    }

    @CrossOrigin
    @PostMapping("/getallorder")
    public List<Orders> getallorder(){
        return bookService.getallorder();
    }


    // 修改购物车服务，增加逻辑判断
    @CrossOrigin
    @PostMapping("/getshopcart")
    public List<Shopcart> getshopcart(@RequestBody int userId){
        return bookService.getShopcarts(userId);
    }

    @CrossOrigin
    @PostMapping("/addshopcart")
    public void addshopcart(@RequestBody Shopcart shopcart){
        bookService.addshopcart(shopcart);
    }

    @CrossOrigin
    @PostMapping("/deleteshopcart")
    public boolean deleteshopcart(@RequestBody Shopcart shopcart){
        return bookService.deleteshopcart(shopcart);
    }

    @CrossOrigin
    @PostMapping("/addorder")
    public boolean addorder(@RequestBody List<OrderItem> orders){
        return bookService.addorder(orders);
    }

    //////////////////////////////////////

    @CrossOrigin
    @PostMapping("/sortbooksbytime")
    public List<Map.Entry<String, Integer>> GetRankingList(@RequestBody JSONObject jsonObject) throws ParseException {
        return bookService.GetRankingList(jsonObject);
    }

    @CrossOrigin
    @PostMapping("/sortordersbytime")
    public List<Orders> sortOrdersByTime(@RequestBody JSONObject jsonObject) throws ParseException {
       return bookService.sortOrdersByTime(jsonObject);
    }

    @CrossOrigin
    @PostMapping("/addbooktest")
    public boolean addBook_(@RequestBody Book book){
        return bookService.addBook_(book);
    }

    @CrossOrigin
    @PostMapping("/deletebooktest")
    public boolean deleteBook_(@RequestBody Book book){
        return bookService.deleteBook_(book);
    }

    @CrossOrigin
    @PostMapping("/modifybooktest")
    public boolean ModifyBook_(@RequestBody Book book){
        return bookService.ModifyBook(book);
    }



}