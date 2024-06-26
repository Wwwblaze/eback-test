package com.example.eback.daoImpl;
import com.example.eback.dao.bookDao;
import com.example.eback.entity.Book;
import com.example.eback.entity.OrderItem;
import com.example.eback.entity.Orders;
import com.example.eback.repository.BookRepository;
import com.example.eback.repository.OrderItemRepository;
import com.example.eback.repository.ShopcartRepository;
import com.example.eback.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.example.eback.entity.Shopcart;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Date;

@Repository
public class bookDaoImpl implements bookDao{
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ShopcartRepository shopcartRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    public bookDaoImpl(BookRepository bookRepository,OrderRepository orderRepository) {
        this.bookRepository = bookRepository;
        this.orderRepository = orderRepository;
    }

    public List<Book> getBooks() {
        List<Book> result;
        result = bookRepository.findAll();
        return result;
    }


    public boolean deleteBook(Book book){
        bookRepository.delete(book);
        return true;
    }

    public boolean addBook(Book book){
        bookRepository.save(book);
        return true;
    }

    public List<Shopcart> getShopcarts(int userId){
        List<Shopcart> result;
        result = shopcartRepository.findShopcartsByUserid(userId);
        return result;
    }


    public void addshopcart(Shopcart shopcart){
        shopcartRepository.save(shopcart);
    }

    public boolean deleteshopcart(Shopcart shopcart){
       shopcartRepository.delete(shopcart);
       return true;
    }

    public List<Orders> getOrders(int userId){
        return orderRepository.findOrdersByUserid(userId);
    }

    public List<OrderItem> getorderitem(int userId){
        return orderItemRepository.findOrderItemsByUserid(userId);
    }

    public List<OrderItem> getallorderitem(){
        return orderItemRepository.findAll();
    }

    public List<Orders> getallorder(){
        return orderRepository.findAll();
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
        orderRepository.save(newOrder);
        int orderid = newOrder.getId();
        for(int i = 0; i < orders.size(); i++){
            OrderItem tem = orders.get(i);
            int bookid = tem.getBookid();
            Book book = bookRepository.findBookById(bookid);
            int inventory = book.getInventory();

            book.setInventory(inventory-1);
            bookRepository.save(book);
            tem.setOrderid(orderid);
            tem.setTime(time);
        }
        orderItemRepository.saveAll(orders);
        shopcartRepository.deleteAll();
        return true;
    }

    public List<Book> searchbook(String str) {
        if(str.length() > 50) return null;
        if (str.isEmpty()){
            return bookRepository.findAll();
        } else {
            List<Book> result = bookRepository.findDistinctBooksByNameContainingOrAuthorContainingOrTypeContaining(str, str, str);
            return result;
        }
    }

    public Book findBookById(Integer id){
        Book book = bookRepository.findBookById(id);
        return book;
    }

    public Book findBookByName(String bookName){
        Book book = bookRepository.findBookByNameEquals(bookName);
        return book;
    }

    @Override
    public Book findBookByIsbn(String isbn) {
        Book book = bookRepository.findBookByIsbnEquals(isbn);
        return book;
    }

    @Override
    public boolean ModifyBook(Book book) {
        bookRepository.save(book);
        return true;
    }

    @Override
    public boolean deleteBookByIsbn(String isbn) {
        bookRepository.deleteBookByIsbnEquals(isbn);
        return true;
    }


}
