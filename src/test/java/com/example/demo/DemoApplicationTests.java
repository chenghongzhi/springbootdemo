package com.example.demo;

import com.example.model.Permission;
import com.example.model.Report;
import com.example.model.User;
import com.example.service.PermissionService;
import com.example.service.ReportService;
import com.example.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {
    @Autowired
    UserService userService;
    @Autowired
    ReportService reportService;
    @Test
    public void contextLoads() {
        List<Report> list=reportService.selectAll();
        System.out.println(list.get(0).getStatus());
    }

    @Test
    public void test(){
//        Random random = new Random();
//        random.ints().limit(10).forEach(System.out::println);
        List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);
// 获取对应的平方数
        List<Integer> squaresList = numbers.stream().map( i -> i*i).distinct().collect(Collectors.toList());
        Iterator iterator=squaresList.iterator();
        while (iterator.hasNext()) {
           int i=(int) iterator.next();
            System.out.println(i);
        }
    }


}
