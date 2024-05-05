package com.example.eback.serviceimpl;

import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GetRankingListTest {

    @Resource
    private BookServiceImpl bookService;


    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getRankingListWithValidTimeRangeAndExistingOrders() throws ParseException {
        // Prepare test data
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("starttime", "2023-01-01 00:00:00");
        jsonObject.put("endtime", "2024-12-31 23:59:59");
        jsonObject.put("userId", 1); // Assuming this user has existing orders within the time range

        // Call the method
        List<Map.Entry<String, Integer>> result = bookService.GetRankingList(jsonObject);

        // Assertions
        assertNotNull(result);
        assertFalse(result.isEmpty());
        // Add more assertions as needed
    }

    @Test
    void getRankingListWithValidTimeRangeButNoOrders() throws ParseException {
        // Prepare test data
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("starttime", "2023-01-01 00:00:00");
        jsonObject.put("endtime", "2024-12-31 23:59:59");
        jsonObject.put("userId", 456); // Assuming this user has no orders within the time range

        // Call the method
        List<Map.Entry<String, Integer>> result = bookService.GetRankingList(jsonObject);

        // Assertions
        assertTrue(result.isEmpty());
        // Add more assertions as needed
    }

    @Test
    void getRankingListWithEmptyTimeRange() throws ParseException {
        // Prepare test data
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("starttime", "");
        jsonObject.put("endtime", "");
        jsonObject.put("userId", 1); // User ID doesn't matter for this test

        // Call the method
        List<Map.Entry<String, Integer>> result = bookService.GetRankingList(jsonObject);

        // Assertions
        assertNull(result);
        // Add more assertions as needed
    }

    @Test
    void getRankingListWithInvalidTimeRange() throws ParseException {
        // Prepare test data
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("starttime", "2024-12-31 23:59:59");
        jsonObject.put("endtime", "2024-01-01 00:00:00");
        jsonObject.put("userId", 1); // User ID doesn't matter for this test

        // Call the method
        List<Map.Entry<String, Integer>> result = bookService.GetRankingList(jsonObject);

        // Assertions
        assertNull(result);
        // Add more assertions as needed
    }

    @Test
    void getRankingListWithValidTimeRangeButNoOrderItems() throws ParseException {
        // Prepare test data
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("starttime", "2024-01-01 00:00:00");
        jsonObject.put("endtime", "2024-12-31 23:59:59");
        jsonObject.put("userId", 1); // Assuming this user has no order items

        // Call the method
        List<Map.Entry<String, Integer>> result = bookService.GetRankingList(jsonObject);

        // Assertions
        assertNotNull(result);
        assertTrue(result.isEmpty());
        // Add more assertions as needed
    }
}