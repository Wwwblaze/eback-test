package com.example.eback.serviceimpl;

import com.example.eback.daoImpl.bookDaoImpl;
import com.example.eback.entity.Book;
import com.example.eback.entity.OrderItem;
import com.example.eback.entity.Shopcart;
import com.example.eback.entity.Shopcartnum;
import com.example.eback.repository.BookRepository;
//import org.junit.jupiter.api.Assertions;
import com.example.eback.repository.ShopcartRepository;
import com.example.eback.repository.ShopcartnumRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.assertj.core.api.Assertions;
import javax.annotation.Resource;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookServiceImplTest {

    @Resource
    private BookServiceImpl bookService;
    @Resource
    private BookRepository bookRepository;
    @Resource
    private ShopcartRepository shopcartRepository;
    @Resource
    private ShopcartnumRepository shopcartnumRepository;

    @Test
    void getShopcarts() {
        bookService.getShopcarts(1);
    }

    @Test
    void addshopcart() {
        // test0
        Shopcart shopcart = null;
        bookService.addshopcart(shopcart);

        // test1
        Shopcart shopcart1 = new Shopcart();
        shopcart1.setCartid(107);
        shopcart1.setUserid(1);
        shopcart1.setBookid(8);
        shopcart1.setImg("http://img3m7.ddimg.cn/13/15/27912667-1_u_1.jpg");
        shopcart1.setName("悲惨世界（上中下）（精装版）");
        shopcart1.setAuthor("雨果");
        shopcart1.setPrice(105);
        bookService.addshopcart(shopcart1);

        // test2
        Shopcart shopcart2 = new Shopcart();
        shopcart2.setCartid(108);
        shopcart2.setUserid(1);
        shopcart2.setBookid(30);
        shopcart2.setImg(null);
        shopcart2.setName("test");
        shopcart2.setAuthor("test");
        shopcart2.setPrice(105);
        bookService.addshopcart(shopcart2);
    }

    @Test
    void deleteshopcart() {
//        // test1
//        Shopcart shopcart = new Shopcart();
//        boolean flag = bookService.deleteshopcart(shopcart);
//        Assertions.assertThat(flag).isEqualTo(true);

        // test2
        Shopcart shopcart1 = new Shopcart();
        shopcart1.setCartid(107);
        shopcart1.setUserid(1);
        shopcart1.setBookid(8);
        shopcart1.setImg("http://img3m7.ddimg.cn/13/15/27912667-1_u_1.jpg");
        shopcart1.setName("悲惨世界（上中下）（精装版）");
        shopcart1.setAuthor("雨果");
        shopcart1.setPrice(105);
        boolean flag1 = bookService.deleteshopcart(shopcart1);
        Assertions.assertThat(flag1).isEqualTo(true);

        Shopcart shopcart2 = new Shopcart();
        shopcart2.setCartid(108);
        shopcart2.setUserid(1);
        shopcart2.setBookid(30);
        shopcart2.setImg(null);
        shopcart2.setName("test");
        shopcart2.setAuthor("test");
        shopcart2.setPrice(105);
        boolean flag2 = bookService.deleteshopcart(shopcart2);
        Assertions.assertThat(flag2).isEqualTo(true);

    }

    @Test
    void deleteAllshopcart() {
        // test0
        int userId = 0;
        bookService.deleteAllshopcart(userId);

        // test1
        int userId1 = 2;
        Shopcart shopcart = new Shopcart(109, userId1, 8, "http://img3m7.ddimg.cn/13/15/27912667-1_u_1.jpg",
                "悲惨世界（上中下）（精装版）", "雨果", 105);
        Shopcart shopcart1 = new Shopcart(110, userId1, 8, "http://img3m7.ddimg.cn/13/15/27912667-1_u_1.jpg",
                "悲惨世界（上中下）（精装版）", "雨果", 105);
        shopcartRepository.save(shopcart);
        shopcartRepository.save(shopcart1);
        bookService.deleteAllshopcart(userId1);
    }

    @Test
    void editshopcart() {
        Shopcartnum shopcartnum = new Shopcartnum(1, 8, 3);
        Shopcartnum shopcartnum1 = new Shopcartnum(1, 9, 4);
        Shopcartnum shopcartnum2 = new Shopcartnum(1, 31, 1);
//        Shopcartnum shopcartnum3 = new Shopcartnum(1, 32, 1);

        shopcartnumRepository.save(shopcartnum);
        shopcartnumRepository.save(shopcartnum1);
        shopcartnumRepository.save(shopcartnum2);
//        shopcartnumRepository.save(shopcartnum3);

        //test0,num
        bookService.editshopcart(1,8,-1);
        //test1,num
        bookService.editshopcart(1,8,6);
        //test2,null,库存
        bookService.editshopcart(1,10, 3);
        //test3,null,无库存
        bookService.editshopcart(1,32, 3);
        //test4，小于当前num的修改
        bookService.editshopcart(1,8, 1);
        //test5，大于当前num的修改，库存不足
        bookService.editshopcart(1, 31, 2);
        //test6，大于当前num的修改，库存充足
        bookService.editshopcart(1,8, 5);
        //test7,num=0
        bookService.editshopcart(1,8, 0);

        shopcartnumRepository.deleteAll();
    }

    @Test
    void addOrder(){
        //库存正常，价格正常
        OrderItem orderItem1 = new OrderItem();
        orderItem1.setOrderid(500);
        orderItem1.setUserid(1);
        orderItem1.setBookid(25);
        orderItem1.setImg("http://img3m2.ddimg.cn/84/17/23273202-1_w_1.jpg");
        orderItem1.setName("天龙八部(全五册)");
        orderItem1.setTime("2024-05-22 05:29:24");
        orderItem1.setAuthor("金庸");
        orderItem1.setPrice(102);
        List<OrderItem> orders1 = new ArrayList<>();
        orders1.add(orderItem1);


        //库存正常，价格异常
        OrderItem orderItem2 = new OrderItem();
        orderItem2.setOrderid(600);
        orderItem2.setUserid(1);
        orderItem2.setBookid(25);
        orderItem2.setImg("http://img3m2.ddimg.cn/84/17/23273202-1_w_1.jpg");
        orderItem2.setName("天龙八部(全五册)");
        orderItem2.setTime("2024-05-22 05:29:24");
        orderItem2.setAuthor("金庸");
        orderItem2.setPrice(-102);
        List<OrderItem> orders2 = new ArrayList<>();
        orders2.add(orderItem2);

        //插入库存为0的书籍
        Book book = new Book();
        book.setIsbn("500");
        book.setName("testing");
        book.setType("testing");
        book.setAuthor("testing");
        book.setPrice(BigDecimal.valueOf(1.1));
        book.setDescription("testing");
        book.setInventory(0);
        book.setImage("http://img3m2.ddimg.cn/84/17/23273202-1_w_1.jpg");
        bookRepository.saveAndFlush(book);

        //库存异常，价格正常
        OrderItem orderItem3 = new OrderItem();
        orderItem3.setOrderid(700);
        orderItem3.setUserid(1);
        orderItem3.setBookid(335);
        orderItem3.setImg("http://img3m2.ddimg.cn/84/17/23273202-1_w_1.jpg");
        orderItem3.setName("testing");
        orderItem3.setTime("2024-05-22 05:29:24");
        orderItem3.setAuthor("testing");
        orderItem3.setPrice(1);
        List<OrderItem> orders3 = new ArrayList<>();
        orders3.add(orderItem3);

        //库存异常，价格异常
        OrderItem orderItem4 = new OrderItem();
        orderItem4.setOrderid(700);
        orderItem4.setUserid(1);
        orderItem4.setBookid(335);
        orderItem4.setImg("http://img3m2.ddimg.cn/84/17/23273202-1_w_1.jpg");
        orderItem4.setName("testing");
        orderItem4.setTime("2024-05-22 05:29:24");
        orderItem4.setAuthor("testing");
        orderItem4.setPrice(-1);
        List<OrderItem> orders4 = new ArrayList<>();
        orders4.add(orderItem4);

        //测试1：库存正常，价格正常
        boolean flag1 = bookService.addorder(orders1);
        Assertions.assertThat(flag1).isEqualTo(true);
        //测试2：库存正常，价格异常
        boolean flag2 = bookService.addorder(orders2);
        Assertions.assertThat(flag2).isEqualTo(false);
        //测试3：库存异常，价格正常
        boolean flag3 = bookService.addorder(orders3);
        Assertions.assertThat(flag3).isEqualTo(false);
        //测试4：库存异常，价格异常
        boolean flag4 = bookService.addorder(orders4);
        Assertions.assertThat(flag4).isEqualTo(false);
    }
}