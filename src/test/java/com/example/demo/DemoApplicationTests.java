package com.example.demo;

import com.example.model.User;
import com.example.service.ReportService;
import com.example.service.UserService;
import com.spire.doc.*;
import com.spire.doc.documents.*;
import com.spire.doc.fields.TextRange;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.awt.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {
    @Autowired
    private UserService userService;
    @Test
    public void test(){
        System.out.println(userService.selectByName("admin").toString());
    }

    @Test
    public void test2(){
        //加载Word文档
        Document document = new Document("/Users/chenghongzhi/Downloads/5指导记录.dotx");

        //使用新文本替换文档中的指定文本
        document.setReplaceFirst(true);
        document.replace("1", "工学分院", false, true);
        document.replace("2", "计算机科学与技术", false, true);
        document.replace("3", "程鸿志", false, true);
        document.replace("4", "201631950122", false, true);
        document.replace("5", "XXX", false, true);
        document.replace("6", "副教授", false, true);
        document.replace("7", "XXX", false, true);
        document.replace("8", "副教授", false, true);
        document.replace("9", "米奇妙妙屋", false, true);

        //保存文档
        document.saveToFile("ReplaceAllMatchedText.docx", FileFormat.Docx_2013);
//        /Users/chenghongzhi/Downloads
    }
}

