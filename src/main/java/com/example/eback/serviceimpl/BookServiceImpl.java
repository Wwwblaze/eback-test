package com.example.eback.serviceimpl;

import com.alibaba.fastjson.JSONObject;
import com.example.eback.dao.bookDao;
import com.example.eback.entity.Book;
import com.example.eback.entity.OrderItem;
import com.example.eback.entity.Shopcart;
import com.example.eback.entity.Orders;
import com.example.eback.repository.OrderItemRepository;
import com.example.eback.repository.OrderRepository;
import com.example.eback.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private bookDao bookDao;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    public List<Book> getBooks() {
        return bookDao.getBooks();
    }

    public boolean deleteBook(Book book){
        return bookDao.deleteBook(book);
    }

    public boolean addBook(Book book){
        return bookDao.addBook(book);
    }

    public List<Shopcart> getShopcarts(int userId){
        return bookDao.getShopcarts(userId);
    }

    public List<Orders> getOrders(int userId){return bookDao.getOrders(userId);}

    public List<OrderItem> getorderitem(int userId){return bookDao.getorderitem(userId);}

    public List<OrderItem> getallorderitem(){return bookDao.getallorderitem();}

    public List<Orders> getallorder(){return bookDao.getallorder();}

    public void addshopcart(Shopcart shopcart){
        bookDao.addshopcart(shopcart);
    }

    public boolean deleteshopcart(Shopcart shopcart){return bookDao.deleteshopcart(shopcart);}

    public boolean addorder(List<OrderItem> orders){return bookDao.addorder(orders);}

    public List<Book> searchbook(String str){return bookDao.searchbook(str);}

    public List<Map.Entry<String, Integer>> GetRankingList(JSONObject jsonObject) throws ParseException {


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date startTime = simpleDateFormat.parse((String) jsonObject.get("starttime"));
        Date endTime = simpleDateFormat.parse((String) jsonObject.get("endtime"));
        int userId = (int) jsonObject.get("userId");

        if (endTime.before(startTime)) return null;

        Map<String, Integer> map = new HashMap<>();
        List<OrderItem> orderItems = orderItemRepository.findOrderItemsByUserid(userId);

        for (OrderItem orderItem : orderItems) {
            Date time = simpleDateFormat.parse(orderItem.getTime());
            if (time.before(startTime) || time.after(endTime)) {
                continue;
            }
            String key = orderItem.getName();
            map.put(key, map.getOrDefault(key, 0) + 1);
        }

        List<Map.Entry<String, Integer>> sortedList = map.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(10)
                .collect(Collectors.toList());

        return sortedList;
    }

    public List<Orders> sortOrdersByTime(@RequestBody JSONObject jsonObject) throws ParseException {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date startTime = simpleDateFormat.parse((String) jsonObject.get("starttime"));
        Date endTime = simpleDateFormat.parse((String) jsonObject.get("endtime"));
        int userId = (int) jsonObject.get("userId");

        if (endTime.before(startTime)) return null;

        List<Orders> orders = orderRepository.findOrdersByUserid(userId);
        Iterator<Orders> iterator = orders.iterator();
        while (iterator.hasNext()) {
            Orders orderItem = iterator.next();
            Date time = simpleDateFormat.parse(orderItem.getTime());
            if (time.before(startTime) || time.after(endTime)) {
                iterator.remove();
            }
        }

        return orders;
    }

}
