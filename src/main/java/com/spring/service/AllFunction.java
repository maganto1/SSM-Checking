package com.spring.service;

import com.spring.pojo.Absent;
import com.spring.pojo.Student;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface AllFunction {
    public int addStudent(Student student);

    public int selectStudentOne(String stuID);

    public int addAbsent(Absent absent);

    public List<Student> QueryLimit(int index);

    public int selectAll();

    public List<String> selectAbsentStudentID();

    public Student QueryOneStudent(String index);

    public List<Absent> QueryAbsentStudentByCourseName(String courseName);

    public List<Student> QueryStudentByName(String name);

    public List<Absent> QueryAbsentStudentById(String studentID);

    public int deleteAbsent(String id);

    public int deleteStudent(String id);

    public Absent QueryAbsentID(int id);

    public int updataAbsent(int id,Absent absent);

    public int updataStudent(String id,Student student);
}
