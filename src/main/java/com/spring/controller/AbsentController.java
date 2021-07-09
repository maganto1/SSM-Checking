package com.spring.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.spring.pojo.Absent;
import com.spring.pojo.Student;
import com.spring.service.AllFunction;
import com.spring.service.SearchService;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@Slf4j
public class AbsentController {
    @Autowired
    private AllFunction function;

    @Autowired
    private SearchService searchService;

    /**
     * 跳转缺课页面
     */
    @PostMapping("/toaddAbsent")
    public String toAbsent(HttpServletResponse response){
        response.setHeader("Cache-Control","no-cache");
        response.setHeader("Pragma","no-cache");
        response.setDateHeader("Expires",-1);
        return "addAbsent";
    }

    /**
     * 添加缺课学生记录
     * @param absent
     * @return
     */
    @RequestMapping("/addAbsent")
    @ResponseBody
    public String Absent(Absent absent){
        JSONObject jsonObject = new JSONObject();
        int i = 0;
        i = function.selectStudentOne(absent.getStudentID());
        //业务逻辑可能出错,判断没有找到
        if (i != 0){
            try {
                function.addAbsent(absent);
                jsonObject.put("status","true");
                jsonObject.put("reason","添加成功");
                return jsonObject.toString();
            }catch (Exception e){
                log.error(e.getMessage());
            }
        }else {
            jsonObject.put("status","false");
            jsonObject.put("reason","没有该学号学生");
            log.debug("没有学号");
            return jsonObject.toString();
        }
        jsonObject.put("status","false");
        return jsonObject.toString();
    }

    /**
     * 删除缺课记录
     */
    @RequestMapping("/deleteabsent/{id}")
    @ResponseBody
    public String delete(@PathVariable String id){
        int success = function.deleteAbsent(id);
        if (success != 0){
            return JSON.toJSONString("true");
        }
        return JSON.toJSONString("false");
    }

    /**
     * 删除学生记录和缺课记录
     * @param id 学生id
     */
    @RequestMapping("/deleteAll/{id}")
    @ResponseBody
    public String deleteAll(@PathVariable String id){
        int absent = function.deleteAbsent(id);
        int student = function.deleteStudent(id);
        if (student != 0 && absent != 0){
            return JSON.toJSONString("true");
        }
        return JSON.toJSONString("false");
    }

    /**
     *根据课程名查询缺课记录
     * @return
     */
    @RequestMapping("/selectBycourse/{coursename}")
    @ResponseBody
    public String selectBycourse(@PathVariable String coursename){
        return  searchService.searchCourse(coursename);
    }

    /**
     * 跳转修改缺课页面
     */
    @PostMapping("/toAlterAbsent")
    public String toUpdataAbsent(int beforeID, Model model,HttpServletResponse response){
        response.setHeader("Cache-Control","no-cache");
        response.setHeader("Pragma","no-cache");
        response.setDateHeader("Expires",-1);
        Absent absent = function.QueryAbsentID(beforeID);
        model.addAttribute("absent",absent);
        return "alterAbsent";
    }

    /**
     * 修改缺课记录
     * @param beforeid 修改前缺课ID
     * @param absent   修改后的缺课数据
     * @return 成功或失败json
     */
    @RequestMapping("/alterAbsent")
    @ResponseBody
    public String updataAbsent(int beforeid,Absent absent){
        JSONObject jsonObject = new JSONObject();
        if (beforeid != absent.getID()){
            Absent check = function.QueryAbsentID(absent.getID());
            if (check == null){
                int i = function.updataAbsent(beforeid, absent);
                if (i != 0){
                    jsonObject.put("status", "true");
                    jsonObject.put("reason", "修改成功");
                    return jsonObject.toString();
                }else {
                    jsonObject.put("status", "false");
                    jsonObject.put("reason", "添加失败");
                    return jsonObject.toString();
                }
            }
        }
        jsonObject.put("status", "false");
        jsonObject.put("reason", "添加失败");
        return jsonObject.toString();
    }
}
