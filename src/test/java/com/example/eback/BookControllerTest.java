package com.example.eback;

import com.alibaba.fastjson.JSON;
import com.example.eback.controllers.BookController;
import com.example.eback.entity.Book;
import com.example.eback.service.BookService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


@SpringBootTest
@WebAppConfiguration
public class BookControllerTest {

    protected Logger logger = LoggerFactory.getLogger(BookControllerTest.class);

    @Autowired
    BookController bookController;
    @Autowired
    BookService bookService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }

    @Test
    public void AddBookTest() throws Exception{
        Book book = new Book();
        book.setIsbn("abc");
        book.setName("abc");
        String add_book = JSON.toJSONString(book);
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/addbooktest")
                        .accept(MediaType.parseMediaType("application/json;charset=UTF-8"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(add_book)



        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        boolean return_result = Boolean.parseBoolean(result);
        logger.info("调用返回的结果：{}", result);
        Assertions.assertEquals(false,return_result);
    }

    @Test
    public void DeleteBookTest() throws Exception{
        Book book = new Book();
        book.setIsbn("abc");
        String delete_book = JSON.toJSONString(book);
        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/deletebooktest")
                                .accept(MediaType.parseMediaType("application/json;charset=UTF-8"))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(delete_book)



                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        boolean return_result = Boolean.parseBoolean(result);
        logger.info("调用返回的结果：{}", result);
        Assertions.assertEquals(false,return_result);
    }

    @Test
    public void ModifyBookTest() throws Exception{
        Book book = new Book();
        book.setIsbn("40");
        book.setName("case19");
        bookService.addBook_(book);
        book.setName("论持久战");
        String modify_book = JSON.toJSONString(book);
        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/modifybooktest")
                                .accept(MediaType.parseMediaType("application/json;charset=UTF-8"))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(modify_book)



                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        boolean return_result = Boolean.parseBoolean(result);
        logger.info("调用返回的结果：{}", result);
        Assertions.assertEquals(true,return_result);
    }
}
