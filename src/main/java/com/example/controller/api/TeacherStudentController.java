package com.example.controller.api;

import com.example.model.TeacherStudent;
import com.example.service.GroupTeacherService;
import com.example.service.TeacherStudentService;
import com.example.service.UserService;
import com.example.util.ResultJSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/teacherStudent")
public class TeacherStudentController {
    @Autowired
    private TeacherStudentService teacherStudentService;
    @Autowired
    private UserService userService;
    @Autowired
    private GroupTeacherService groupTeacherService;

    @PostMapping("/add")
    public ResultJSON add(@RequestBody Map<Object, Object> body){
        ResultJSON json = new ResultJSON();
        String teacherId = (String) body.get("teacherId");
        List<String> ids = (ArrayList) body.get("ids");
        TeacherStudent teacherStudent = new TeacherStudent();
        teacherStudent.setTeacherId(Integer.valueOf(teacherId));
        for (String id : ids) {
            teacherStudent.setStudentId(Integer.valueOf(id));
            teacherStudentService.insert(teacherStudent);
            userService.updateState(1,Integer.valueOf(id));
        }
        json.success();
        return json;
    }

}
