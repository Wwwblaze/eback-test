package com.example.eback.serviceimpl;

import com.example.eback.dao.bookDao;
import com.example.eback.entity.Book;
import com.example.eback.entity.OrderItem;
import com.example.eback.entity.Orders;
import com.example.eback.entity.Shopcart;
import com.example.eback.repository.OrderRepository;
import com.example.eback.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private bookDao bookDao;

    @Autowired
    private OrderRepository orderRepository;

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

    @Override
    public Book findBookByIsbn(String isbn) {
        return bookDao.findBookByIsbn(isbn);
    }

    @Override
    public Book findBookByName(String name) {
        return bookDao.findBookByName(name);
    }

    @Override
    public Book findBookById(Integer id) {
        return bookDao.findBookById(id);
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

    @Override
    public void deleteBookByIsbn(String isbn) {
        deleteBookByIsbn(isbn);
    }

    @Override
    public boolean deleteBook(Book book){
        return bookDao.deleteBook(book);
    }

    public boolean addBook(Book book){
        return bookDao.addBook(book);
    }

    public List<Book> searchbook(String str){return bookDao.searchbook(str);}

}
