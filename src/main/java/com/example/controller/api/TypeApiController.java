package com.example.controller.api;

import com.example.model.Type;
import com.example.service.TypeService;
import com.example.util.ResultJSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/type")
public class TypeApiController {
    @Autowired
    private TypeService typeService;

    @GetMapping("/list")
    public ResultJSON selectALL(){
        ResultJSON json = new ResultJSON();
        Map<String,Object> map = new HashMap<>();
        List<Type> list = typeService.selectAll();
        map.put("info",list);
        json.success(map);
        return json;
    }

}
