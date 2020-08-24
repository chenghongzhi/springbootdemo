package com.example.controller.api;

import com.example.model.LeaderTeacher;
import com.example.service.GroupTeacherService;
import com.example.service.UserService;
import com.example.util.ResultJSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/groupTeacher")
public class GroupTeacherController {
    @Autowired
    private UserService userService;
    @Autowired
    private GroupTeacherService groupTeacherService;

    @PostMapping("/add")
    public ResultJSON add(@RequestBody Map<Object, Object> body){
        ResultJSON json = new ResultJSON();
        String groupId = (String) body.get("groupId");
        List<String> ids = (ArrayList) body.get("ids");
        LeaderTeacher leaderTeacher = new LeaderTeacher();
        leaderTeacher.setLeaderId(Integer.valueOf(groupId));
        for (String id : ids) {
            leaderTeacher.setTeacherId(Integer.valueOf(id));
            groupTeacherService.insert(leaderTeacher);
            userService.updateState(2,Integer.valueOf(id));
        }
        json.success();
        return json;
    }

}
