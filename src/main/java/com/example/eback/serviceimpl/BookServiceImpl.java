package com.example.eback.serviceimpl;

import com.example.eback.dao.bookDao;
import com.example.eback.entity.*;
import com.example.eback.repository.BookRepository;
import com.example.eback.repository.OrderRepository;
import com.example.eback.repository.ShopcartnumRepository;
import com.example.eback.repository.UserRepository;
import com.example.eback.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private bookDao bookDao;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ShopcartnumRepository shopcartnumRepository;

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

    public boolean addorder(List<OrderItem> orders){return bookDao.addorder(orders);}

    public List<Book> searchbook(String str){return bookDao.searchbook(str);}

}
