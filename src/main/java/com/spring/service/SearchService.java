package com.spring.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.spring.dao.Function;
import com.spring.pojo.Absent;
import com.spring.pojo.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class SearchService {

    @Autowired
    private  Function allFunction;

    /**
     * 根据id查询学生记录
     * @param id 学生id
     * @return 返回学生json
     */
    public String searchID(String id){
        JSONArray jsonArray = new JSONArray();
        Student student = allFunction.QueryOneStudent(id);          //根据ID查找学生
        List<String> list = allFunction.selectAbsentStudentID();    //查找逃课学生列表
        if (student != null){
            if (list.contains(student.getStudentID())){             //进行比对，看该学号学生是否逃课
                List<Absent> absents = allFunction.QueryAbsentStudentById(student.getStudentID());
                //如果找出学生只为一个人
                if (absents.size() == 1){
                    JSONObject jsonObject = (JSONObject) JSON.toJSON(student);
                    absents.forEach(item->{
                        jsonObject.put("checkData",item.getCheckData());
                        jsonObject.put("period",item.getPeriod());
                        jsonObject.put("courseName",item.getCourseName());
                        jsonObject.put("absentType",item.getAbsentType());
                        jsonObject.put("ID",item.getID());
                    });
                    return jsonObject.toString();
                }
                //如果找出逃课大于1则遍历，使用lameda表达式
                absents.forEach(item->{
                    JSONObject jsonObject = (JSONObject)JSON.toJSON(student);
                    jsonObject.put("checkData",item.getCheckData());
                    jsonObject.put("period",item.getPeriod());
                    jsonObject.put("courseName",item.getCourseName());
                    jsonObject.put("absentType",item.getAbsentType());
                    jsonObject.put("ID",item.getID());
                    jsonArray.add(jsonObject);
                });
                return jsonArray.toString();
            } else {
                //没有逃课直接返回json
               return JSON.toJSONString(student);
            }
        }
        return JSON.toJSONString("false");
    }

    /**
     * 根据姓名查询学生记录
     * @param name 姓名
     * @return json
     */
    public String searchName(String name){
        JSONArray jsonArray = new JSONArray();
        List<Student> students = allFunction.QueryStudentByName(name);
        if (students != null){
            if (students.size() == 1){
                for (Student stu : students) {
                    return searchID(stu.getStudentID());
                }
            }else if (students.size() == 0){
                return JSON.toJSONString("false");
            }else {
                students.forEach(item->{
                    //判断是json数组还是json对象
                    String json = searchID(item.getStudentID());
                    if (json.contains("[") && json.contains("]")){
                        JSONArray jsonArray1 = JSON.parseArray(searchID(item.getStudentID()));
                        jsonArray.addAll(jsonArray1);
                    }else {
                        JSONObject jsonObject = JSON.parseObject(json);
                        jsonArray.add(jsonObject);
                    }
                });
                return jsonArray.toString();
            }
        }
        return JSON.toJSONString("false");
    }

    /**
     * 根据课程名课程名查找
     * @param courseName 课程名
     * @return json
     */
    public String searchCourse(String courseName){
        List<Absent> absents = allFunction.QueryAbsentStudentByCourseName(courseName);
        JSONArray jsonArray = new JSONArray();
        if (absents.size() == 1){
            JSONObject jsonObject =  new JSONObject();
            for (Absent item:absents){
                Student student = allFunction.QueryOneStudent(item.getStudentID());
                Absent absent = allFunction.QueryAbsentID(item.getID());
                jsonObject = (JSONObject)JSON.toJSON(student);
                jsonObject.put("checkData",absent.getCheckData());
                jsonObject.put("period",absent.getPeriod());
                jsonObject.put("courseName",absent.getCourseName());
                jsonObject.put("absentType",absent.getAbsentType());
                jsonObject.put("ID",absent.getID());
            }
            return jsonObject.toString();
        }else if (absents.size() == 0){
            return JSON.toJSONString("false");
        }else{
            absents.forEach(item->{
                Student student = allFunction.QueryOneStudent(item.getStudentID());
                Absent absent = allFunction.QueryAbsentID(item.getID());
                JSONObject jsonObject =  (JSONObject)JSON.toJSON(student);
                jsonObject.put("checkData",absent.getCheckData());
                jsonObject.put("period",absent.getPeriod());
                jsonObject.put("courseName",absent.getCourseName());
                jsonObject.put("absentType",absent.getAbsentType());
                jsonObject.put("ID",absent.getID());
                jsonArray.add(jsonObject);
            });
            return jsonArray.toString();
        }
    }
}
