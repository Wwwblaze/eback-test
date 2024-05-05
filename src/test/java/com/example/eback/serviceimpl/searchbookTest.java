package com.example.eback.serviceimpl;

import com.example.eback.entity.Book;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class searchbookTest {

    @Resource
    private BookServiceImpl bookService;


    @BeforeEach
    void setUp() {
        System.out.println("Test Start");
    }

    @AfterEach
    void tearDown() {
        System.out.println("Test End");
    }

    @Test
    void searchBookWithEmptyString_ReturnAllBooks() {
        List<Book> result = bookService.searchbook("");
        assertNotNull(result);
        // Add more assertions based on the expected behavior when the input is an empty string
    }

    @Test
    void searchBookWithLongString_ReturnNullAndPrintErrorMessage() {
        String longString = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
        List<Book> result = bookService.searchbook(longString);
        assertNull(result);
        // Add more assertions based on the expected behavior when the input string is too long
    }

    @Test
    void searchBookWithSubstringOfTitle_ReturnMatchingBooks() {
        List<Book> result = bookService.searchbook("动物");
        assertNotNull(result);
        // Add more assertions based on the expected behavior when the input is a substring of a title
    }

    @Test
    void searchBookWithSubstringOfAuthor_ReturnMatchingBooks() {
        List<Book> result = bookService.searchbook("雨果");
        assertNotNull(result);
        // Add more assertions based on the expected behavior when the input is a substring of an author
    }

    @Test
    void searchBookWithSubstringOfType_ReturnMatchingBooks() {
        List<Book> result = bookService.searchbook("儿童");
        assertNotNull(result);
        // Add more assertions based on the expected behavior when the input is a substring of a type
    }

    @Test
    void searchBookWithNoMatch_ReturnEmptyList() {
        List<Book> result = bookService.searchbook("xyz");
        assertNotNull(result);
        // Add more assertions based on the expected behavior when there is no match
    }
}