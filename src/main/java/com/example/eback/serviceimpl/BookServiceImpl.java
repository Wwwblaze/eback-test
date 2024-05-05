package com.example.eback.serviceimpl;

import com.alibaba.fastjson.JSONObject;
import com.example.eback.dao.bookDao;
import com.example.eback.entity.*;
import com.example.eback.repository.*;
import com.example.eback.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import com.example.eback.repository.BookRepository;


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

    @Autowired
    private BookRepository bookRepository;


    @Autowired
    private ShopcartnumRepository shopcartnumRepository;

    @Override
    public List<Book> getBooks() {
        return bookDao.getBooks();
    }

    public boolean deleteBook_(Book book){
        String bookIsbn = book.getIsbn();
        String bookName = book.getName();
        Book book1 = null;
        Book book2 = null;
        if(!bookIsbn.matches("^-?\\d+$")){
            if(bookName == null) return  false;
            else{
                book1 = bookDao.findBookByName(bookName);
            }
        }
        else{
            book2 = bookDao.findBookByIsbn(bookIsbn);
            if (bookName != null){
                book1 = bookDao.findBookByName(bookName);
            }
        }
        if(book1 == null && book2 == null) return false;
        if(book1 != null && book2 != null){
            if(!Objects.equals(book1.getIsbn(), book2.getIsbn()))
                return  false;
        }
        Book book3 = book1 == null ? book2 : book1;
        return bookDao.deleteBook(book3);
    }

    public boolean addBook_(Book book){
        String bookIsbn = book.getIsbn();
        String bookName = book.getName();
        if(!bookIsbn.matches("^-?\\d+$"))
            return false;
        Integer isbn = Integer.parseInt(bookIsbn);
        if (bookName == null) return  false;
        Integer len = bookName.length();
        if (isbn < 0 || isbn > 10000 || bookName.isEmpty() || len > 20)
            return false;
        Book book1 = bookDao.findBookByIsbn(bookIsbn);
        Book book2 = bookDao.findBookByName(bookName);
        if (book1 != null || book2 != null) return false;
        return bookDao.addBook(book);
    }

    public List<Orders> getOrders(int userId){return bookDao.getOrders(userId);}

    public List<OrderItem> getorderitem(int userId){return bookDao.getorderitem(userId);}

    public List<OrderItem> getallorderitem(){return bookDao.getallorderitem();}

    public List<Orders> getallorder(){return bookDao.getallorder();}

    public List<Shopcart> getShopcarts(int userId){
        return bookDao.getShopcarts(userId);
    }

    public void addshopcart(Shopcart shopcart){
        //1.shopcart是否为空,对应的book是否有余量
        if(shopcart == null){
            System.out.println("无法加入购物车，加入条目为空");
        }else{
            Book book = bookRepository.findBookById(shopcart.getBookid());
            if(book.getInventory() <= 0){
                System.out.println("无法加入购物车，对应书籍无库存");
            }else{
                bookDao.addshopcart(shopcart);
                System.out.println("成功加入购物车");
            }
        }
    }

    public boolean deleteshopcart(Shopcart shopcart){
        return bookDao.deleteshopcart(shopcart);
    }


    public boolean deleteAllshopcart(int userId) {
        //TODO: 循环遍历删除对应userId的购物车
        //1.该用户的购物车是否为空
        //2.是否全部删除
        List<Shopcart> shopcarts = bookDao.getShopcarts(userId);
        if(shopcarts != null && !shopcarts.isEmpty()){
            for(Shopcart shopcart : shopcarts){
                boolean flag = bookDao.deleteshopcart(shopcart);
                if(!flag){
                    System.out.println("无法删除购物车条目：" + shopcart.getBookid());
                    return false;
                }
                System.out.println("删除条目成功");
            }
            System.out.println("成功删除用户 " + userId + " 的购物车所有条目");
        }else{
            System.out.println("用户 " + userId + " 的购物车为空，无需删除");
        }
        return true;
    }


    public boolean editshopcart(int userId, int bookId, int num){
        //TODO: 增加编辑购物车条目的功能
        //1.是否在1~5
        if(num < 0 || num > 5){
            return false;
        }
        //2.是否超过库存限制
        //3.是否设置num为0
        Shopcartnum shopcartnum = shopcartnumRepository.getShopcartnumByUserIdAndBookId(userId, bookId);
        if(shopcartnum != null){
            int currentNum = shopcartnum.getNum();
            if(num == 0){
                shopcartnumRepository.delete(shopcartnum);
            }else{
                if(num <= currentNum){
                    System.out.println("小于current_num的修改");
                    shopcartnum.setNum(num);
                    shopcartnumRepository.save(shopcartnum);
                }else{
                    // 检查库存是否足够
                    int availableInventory = bookRepository.findBookById(bookId).getInventory();
                    if(num <= availableInventory){
                        System.out.println("库存充足");
                        shopcartnum.setNum(num);
                        shopcartnumRepository.save(shopcartnum);
                    }else{
                        // 库存不足
                        System.out.println("库存不足");
                        return false;
                    }
                }
            }
        }else{
            // 购物车中没有该条目
            int availableInventory = bookRepository.findBookById(bookId).getInventory();
            if(num > availableInventory){
                return false;
            }else{
                Shopcartnum newshopcartnum = new Shopcartnum(userId, bookId, num);
                shopcartnumRepository.save(newshopcartnum);
            }
        }
        return true;
    }

    public boolean addorder(List<OrderItem> orders){
        for(int i = 0; i < orders.size(); ++i){
            OrderItem orderItem = orders.get(i);
            int bookId = orderItem.getBookid();
            Book book = bookRepository.findBookById(bookId);
            int inventory = book.getInventory();
            int price = orderItem.getPrice();
            if(inventory > 0){
                if(price >= 0){
                    continue;
                }
                else {
                    return false;
                }
            }
            else {
                return false;
            }
        }
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = formatter.format(date);
        Orders newOrder = new Orders();
        newOrder.setTime(time);
        newOrder.setUserid(orders.get(0).getUserid());
        int orderid = newOrder.getId();
        for(int i = 0; i < orders.size(); i++){
            OrderItem tem = orders.get(i);
            int bookid = tem.getBookid();
            Book book = bookRepository.findBookById(bookid);
            int inventory = book.getInventory();

            book.setInventory(inventory-1);
            tem.setOrderid(orderid);
            tem.setTime(time);
        }
        return bookDao.addorder(orders);
    }

    @Override
    public Book findBookByIsbn(String isbn) {
        return bookDao.findBookByIsbn(isbn);
    }

    @Override
    public Book findBookByName(String name) {
        return bookDao.findBookByName(name);
    }




    @Override
    public boolean ModifyBook(Book book) {
        String isbn = book.getIsbn();
        String bookName = book.getName();
        if(!isbn.matches("^-?\\d+$"))
            return false;
        Integer book_isbn = Integer.parseInt(isbn);
        if (bookName == null) return  false;
        Integer len = bookName.length();
        if (book_isbn < 0 || book_isbn > 10000 || bookName.isEmpty() || len > 20)
            return false;
        Book book1 = findBookByIsbn(isbn);
        if (book1 == null) return false;
        book.setId(book1.getId());
        bookDao.ModifyBook(book);
        return true;
    }




    public List<Book> searchbook(String str){
        if(str.length() > 50) {
            System.out.println("输入大于50个字符");
            return null;
        }
        if (str.isEmpty()){
            return bookRepository.findAll();
        } else {
            List<Book> result = bookRepository.findDistinctBooksByNameContainingOrAuthorContainingOrTypeContaining(str, str, str);
            return result;
        }
    }

    public List<Map.Entry<String, Integer>> GetRankingList(JSONObject jsonObject) throws ParseException {


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String startT = (String) jsonObject.get("starttime");
        String endT = (String) jsonObject.get("endtime");

        if (startT.isEmpty() || endT.isEmpty()) return null;
        Date startTime = simpleDateFormat.parse(startT);
        Date endTime = simpleDateFormat.parse(endT);
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

        String startT = (String) jsonObject.get("starttime");
        String endT = (String) jsonObject.get("endtime");

        if (startT.isEmpty() || endT.isEmpty()) return null;

        Date startTime = simpleDateFormat.parse(startT);
        Date endTime = simpleDateFormat.parse(endT);
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
