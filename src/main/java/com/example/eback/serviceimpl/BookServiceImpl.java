package com.example.eback.serviceimpl;

import com.example.eback.dao.bookDao;
import com.example.eback.entity.Book;
import com.example.eback.entity.OrderItem;
import com.example.eback.entity.Shopcart;
import com.example.eback.entity.Orders;
import com.example.eback.repository.BookRepository;
import com.example.eback.repository.OrderRepository;
import com.example.eback.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private bookDao bookDao;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private BookRepository bookRepository;

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

    public List<Book> searchbook(String str){return bookDao.searchbook(str);}

}
