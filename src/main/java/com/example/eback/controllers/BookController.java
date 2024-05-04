package com.example.eback.controllers;

import com.alibaba.fastjson.JSONObject;
import com.example.eback.entity.Book;
import com.example.eback.entity.Shopcart;
import com.example.eback.entity.Orders;
import com.example.eback.entity.OrderItem;
import com.example.eback.repository.OrderItemRepository;
import com.example.eback.service.BookService;
import lombok.var;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//
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
    public List<Book> searchbook(@RequestBody String str){
        return bookService.searchbook(str);
    }

    @CrossOrigin
    @PostMapping("/deletebook")
    public boolean deleteBook(@RequestBody Book book){
        return bookService.deleteBook(book);
    }

    @CrossOrigin
    @PostMapping("/addbook")
    public boolean addBook(@RequestBody Book book){
        return bookService.addBook(book);
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
    public HashMap<String, Integer> sortbooksbytime(@RequestBody JSONObject jsonObject) throws ParseException {

        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date startTime = simpleDateFormat.parse((String) jsonObject.get("starttime"));
        Date endTime = simpleDateFormat.parse((String) jsonObject.get("endtime"));

        if(endTime.before(startTime)) return null;
        HashMap<String, Integer> map = new HashMap<>();
        List<OrderItem> orderItems = orderItemRepository.findOrderItemsByUserid(1);
        for (OrderItem orderItem : orderItems) {
            Date time = simpleDateFormat.parse(orderItem.getTime());
            if(time.before(startTime) || time.after(endTime) ){ continue;}
            var key = orderItem.getName();
            if (map.containsKey(key)) {
                var value = map.get(orderItem.getName());
                map.put(key,value+1);
            }
            else map.put(key,1);
        }

        return map;
    }


}