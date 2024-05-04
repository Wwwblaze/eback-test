package com.example.eback;

import com.example.eback.entity.Book;
import com.example.eback.service.BookService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;


@SpringBootTest
public class BookServiceTest{

    private static final Map<String, Book> testCasesAddBook = new HashMap<>();

    private static final Map<String, Book> testCasesDeleteBook = new HashMap<>();

    private static final Map<String, Book> testCasesModifyBook = new HashMap<>();
    @Autowired
    private BookService bookService;


    @BeforeEach
    public void setUp(){
        Book book1 = new Book();
        book1.setIsbn("9");
        book1.setName("fff");
        Book book2 = new Book();
        book2.setIsbn("100000");
        book2.setName("fff");
        Book book3 = new Book();
        book3.setIsbn("-1");;
        book3.setName("fff");
        Book book4 = new Book();
        book4.setIsbn("1");
        Book book5 = new Book();
        book5.setIsbn("1");
        book5.setName("abcdefghijklmnopqrstuvwxyz");
        Book book6 = new Book();
        book6.setIsbn("1");
        book6.setName("三国演义");
        Book book7 = new Book();
        book7.setIsbn("30");
        book7.setName("福尔摩斯探案全集");
        Book book8 = new Book();
        book8.setIsbn("abc");
        book8.setName("abc");
        Book book1111 = new Book();
        book1111.setIsbn("110");
        book1111.setName("");
        testCasesAddBook.put("case1", book1);
        testCasesAddBook.put("case2", book2);
        testCasesModifyBook.put("case2", book2);
        testCasesAddBook.put("case3", book3);
        testCasesModifyBook.put("case3", book3);
        testCasesAddBook.put("case4", book4);
        testCasesModifyBook.put("case4", book4);
        testCasesAddBook.put("case5", book5);
        testCasesModifyBook.put("case5", book5);
        testCasesAddBook.put("case6", book6);
        testCasesAddBook.put("case7", book7);
        testCasesAddBook.put("case8", book8);
        testCasesModifyBook.put("case8", book8);
        testCasesAddBook.put("case1111",book1111);
        testCasesModifyBook.put("case1111",book1111);
        Book book9 = new Book();
        book9.setIsbn("abc");
        testCasesDeleteBook.put("case9", book9);
        Book book10 = new Book();
        book10.setIsbn("abc");
        book10.setName("abc");
        testCasesDeleteBook.put("case10", book10);
        Book book11 = new Book();
        book11.setIsbn("abc");
        book11.setName("case11");
        Book book11_ = new Book();
        book11_.setIsbn("1");
        book11_.setName("case11");
        bookService.addBook_(book11_);
        testCasesDeleteBook.put("case11",book11);
        Book book12 = new Book();
        book12.setIsbn("100");
        testCasesDeleteBook.put("case12", book12);
        Book book13 = new Book();
        book13.setIsbn("100");
        book13.setName("case13");
        testCasesDeleteBook.put("case13",book13);
        Book book14 = new Book();
        book14.setIsbn("101");
        book14.setName("case14");
        bookService.addBook_(book14);
        Book book15 = new Book();
        book15.setIsbn("8");
        book15.setName("case14");
        testCasesDeleteBook.put("case14", book15);
        Book book16 = new Book();
        book16.setIsbn("102");
        book16.setName("case16");
        bookService.addBook_(book16);
        book16.setName("abc");
        testCasesDeleteBook.put("case15", book16);
        Book book17 = new Book();
        book17.setIsbn("107");
        book17.setName("case17");
        bookService.addBook_(book17);
        book17.setIsbn("1000");
        testCasesDeleteBook.put("case16", book17);
        Book book18 = new Book();
        book18.setIsbn("123");
        book18.setName("case18");
        testCasesModifyBook.put("case18", book18);
        Book book19 = new Book();
        book19.setIsbn("40");
        book19.setName("case19");
        bookService.addBook_(book19);
        book19.setName("论持久战");
        testCasesModifyBook.put("case19", book19);
    }


    @Test
    public void testAddBook(){
        System.out.println("Add Book Test Cases: "+testCasesAddBook.size());
        Set<String> keys = testCasesAddBook.keySet();
        for (String key : keys){
            Book book = testCasesAddBook.get(key);
            boolean AddBook = bookService.addBook_(book);
            if(Objects.equals(key, "case7"))
            {
                Assertions.assertEquals(true,AddBook);
                String isbn = book.getIsbn();
                Book book1 = bookService.findBookByIsbn(isbn);
                bookService.deleteBook_(book1);
            }
            else
                Assertions.assertEquals(false,AddBook);
        }
    }

    @Test
    public void testDeleteBook(){
        System.out.println("Delete Book Test Cases: "+testCasesDeleteBook.size());
        Set<String> keys = testCasesDeleteBook.keySet();
        for (String key : keys){
            Book book = testCasesDeleteBook.get(key);
            boolean DeleteBook = bookService.deleteBook_(book);
            if(Objects.equals(key, "case11"))
                Assertions.assertEquals(true,DeleteBook);
            else if(Objects.equals(key, "case14")){
                Assertions.assertEquals(false,DeleteBook);
                Book delete_book = bookService.findBookByName("case14");
                boolean delete_book_ = bookService.deleteBook_(delete_book);
                Assertions.assertEquals(true,delete_book_);
            } else if(Objects.equals(key, "case15"))
            {
                Assertions.assertEquals(true,DeleteBook);

            }else if(Objects.equals(key,"case16"))
            {
                Assertions.assertEquals(true,DeleteBook);
            }
            else Assertions.assertEquals(false,DeleteBook);
        }
    }
    @Test
    public void testModifyBook(){
        System.out.println("Modify Book Test Cases: "+testCasesModifyBook.size());
        Set<String> keys = testCasesModifyBook.keySet();
        for (String key : keys){
            Book book = testCasesModifyBook.get(key);
            boolean modifyBook = bookService.ModifyBook(book);
            if(Objects.equals(key, "case19"))
            {
                Assertions.assertEquals(true,modifyBook);
            }
            else
                Assertions.assertEquals(false,modifyBook);
        }
    }
}
