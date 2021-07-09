package com.spring.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.spring.pojo.Student;
import com.spring.service.AllFunction;
import com.spring.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.net.ResponseCache;
import java.net.http.HttpResponse;
import java.util.List;

@Controller
@Slf4j
public class StudentController {
    @Autowired
    private AllFunction allFunction;

    @Autowired
    private SearchService searchService;

    /**
     * 跳转主页
     */
    @RequestMapping("/")
    public String main(){
        return "main";
    }


    /**
     * 跳转添加学生页面
     */
    @PostMapping("/toaddStudent")
    public String toAddStudent(HttpServletResponse response){
        response.setHeader("Cache-Control","no-cache");                 // Pragma 控制页面缓存为HTTP 1.0中使用
        response.setHeader("Pragma","no-cache");                        // HTTP1.1中启用Cache-Control,no-cache，浏览器和缓存服务器都不应该缓存页面信息；
        response.setDateHeader("Expires",-1);                            // 设置过期实现 -1表示立马过期
        return "addStudent";
    }

    /**
     * 添加学生的处理url
     */
    @PostMapping("/addStudent")
    @ResponseBody
    public String addStudent(Student student){
        JSONObject jsonObject = new JSONObject();
        int repeat = 0;
        if (student.getStudentID() == null){
            jsonObject.put("status","false");
            jsonObject.put("reason","添加失败");
            return jsonObject.toString();
        }
        try {
            repeat = allFunction.selectStudentOne(student.getStudentID());
        }catch (Exception e){
            jsonObject.put("status","false");
            jsonObject.put("reason","添加失败");
            log.error(e.getMessage());
            return jsonObject.toString();
        }

        if (repeat != 0){
            jsonObject.put("status","false");
            jsonObject.put("reason","学号重复");
            return jsonObject.toString();
        }else {
            try {
                allFunction.addStudent(student);
            }catch (Exception e){
                System.out.println(e.getMessage());
                jsonObject.put("status","false");
                jsonObject.put("reason","添加失败");
                return jsonObject.toString();
            }
            jsonObject.put("status","true");
            return jsonObject.toString();
        }
    }

    /**
     * 跳转主页返回页面总数
     */
    @PostMapping("/tolist")
    public String list(Model model,HttpServletResponse response){
        response.setHeader("Cache-Control","no-cache");
        response.setHeader("Pragma","no-cache");
        response.setDateHeader("Expires",-1);
        int count = allFunction.selectAll();
        model.addAttribute("pageCount",count);
        return "list";
    }

    /**
     * 分页查询学生记录和缺课记录
     * @param index 起始页数
     * @return json
     */
    @RequestMapping("/list/{index}")
    @ResponseBody
    public String pageList(@PathVariable int index){
        JSONArray jsonArray = new JSONArray();
        List<Student> students = allFunction.QueryLimit(index);
        List<String> list = allFunction.selectAbsentStudentID();
        for (Student stu: students) {
            JSONObject jsonObject = (JSONObject) JSON.toJSON(stu);
            if (list.contains(stu.getStudentID())) {
                jsonObject.put("absentType", "是");
            }else {
                jsonObject.put("absentType","否");
            }
            jsonArray.add(jsonObject);
        }
        return jsonArray.toString();
    }

    /**
     * 跳转查询页面
     * @param model 返回的model
     * @return 返回十条信息
     */
    @PostMapping("/tosearch")
    public String toSearch(Model model,HttpServletResponse response){
        //防止页面回退过期
        response.setHeader("Cache-Control","no-cache");         // Pragma 控制页面缓存为HTTP 1.0中使用
        response.setHeader("Pragma","no-cache");                // HTTP1.1中启用Cache-Control,no-cache，浏览器和缓存服务器都不应该缓存页面信息；
        response.setDateHeader("Expires",-1);                    // 设置过期实现 -1表示立马过期
        List<Student> students = allFunction.QueryLimit(0);
        model.addAttribute("student",students);
        return "search";
    }

    /**
     * 根据id查询学生记录
     * @param id 学生id
     * @return 返回学生json
     */
    @RequestMapping("/search/{id}")
    @ResponseBody
    public String searchById(@PathVariable String id){
        return searchService.searchID(id);
    }

    /**
     * 根据姓名查询学生记录
     * @param name 姓名
     */
    @RequestMapping("/name/{name}")
    @ResponseBody
    public String searchByName(@PathVariable String name){
        return searchService.searchName(name);
    }

    /**
     * 删除学生记录
     * @param id 学生ID
     * @return 删除成功提示
     */
    @RequestMapping("/deleteStudent/{id}")
    @ResponseBody
    public String deleteStudent(@PathVariable String id){
        int success = allFunction.deleteStudent(id);
        if (success != 0){
            return JSON.toJSONString("true");
        }
        return JSON.toJSONString("false");
    }

    /**
     *  返回一个学生姓名
     * @param id    学号
     * @return 学生姓名json
     */
    @RequestMapping("/matchStudentName/{id}")
    @ResponseBody
    public String selectOneStudent(@PathVariable String id){
        Student student = allFunction.QueryOneStudent(id);
        if (student != null){
            return JSON.toJSONString(student.getName());
        }else {
            return JSON.toJSONString("false");
        }
    }

    /**
     * 跳转修改学生页面
     * @param beforeID 修改前学生ID
     * @param model 自动获取学生信息返回显示到前端页面
     * @return 跳转的页面
     */
    @PostMapping("toAlterStudent")
    public String toALterStudent(String beforeID,Model model,HttpServletResponse response){
        response.setHeader("Cache-Control","no-cache");
        response.setHeader("Pragma","no-cache");
        response.setDateHeader("Expires",-1);
        Student student = allFunction.QueryOneStudent(beforeID);
        model.addAttribute("stu",student);
        return "alterStudent";
    }

    /**
     * 修改学生处理URl
     * @param beforeID 修改前学生ID
     * @param student  修改后学生数据
     * @return 成功或失败json
     */
    @RequestMapping("/alterStudent")
    @ResponseBody
    public String updataStudent(String beforeID,Student student) {
        JSONObject jsonObject = new JSONObject();
        if (!beforeID.equals(student.getStudentID())){
            int count = allFunction.selectStudentOne(student.getStudentID());
            if (count != 0) {
                jsonObject.put("status", "false");
                jsonObject.put("reason", "学号重复");
                return jsonObject.toString();
            }
        }
        int success = allFunction.updataStudent(beforeID, student);
        if (success != 0) {
            jsonObject.put("status", "true");
            jsonObject.put("reason", "修改成功");
            return jsonObject.toString();
        }
        jsonObject.put("status", "false");
        return jsonObject.toString();
    }
}
