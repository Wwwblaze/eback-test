package com.example.eback.serviceimpl;

import com.alibaba.fastjson.JSONObject;
import com.example.eback.entity.Book;
import com.example.eback.entity.Orders;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class SortOrdersByTimeTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Resource
    private BookServiceImpl bookService;

    //时间段为空
    @Test
    void testEmptyTimeRange() throws ParseException {
        JSONObject jsonObject = createJsonObject("", "", 1);

        List<Orders> result = bookService.sortOrdersByTime(jsonObject);

        assertNull(result);
    }

    //时间顺序错误
    @Test
    void testInvalidTimeRange() throws ParseException {
        JSONObject jsonObject = createJsonObject("2024-12-31 23:59:59", "2024-01-01 00:00:00", 1);

        List<Orders> result = bookService.sortOrdersByTime(jsonObject);

        assertNull(result);
    }

    //输入时间段正确但订单不存在的情况
    @Test
    void testNoOrdersExist() throws ParseException {
        JSONObject jsonObject = createJsonObject("2024-01-01 00:00:00", "2024-12-31 23:59:59", 1);

        List<Orders> result = bookService.sortOrdersByTime(jsonObject);

        assertTrue(result.isEmpty());
    }


    // 测试用例：验证指定时间范围内的订单是否正确返回
    @Test
    void testOrdersExistInTimeRange() throws ParseException {
        JSONObject jsonObject = createJsonObject("2023-01-01 00:00:00", "2024-12-31 23:59:59", 1);

        List<Orders> result = bookService.sortOrdersByTime(jsonObject);
        assertFalse(result.isEmpty());
    }


    // Helper method to create JSONObject with given start time, end time, and user ID
    private JSONObject createJsonObject(String startTime, String endTime, int userId) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("starttime", startTime);
        jsonObject.put("endtime", endTime);
        jsonObject.put("userId", userId);
        return jsonObject;
    }

}