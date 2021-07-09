package com.spring.service;

import com.spring.dao.Function;
import com.spring.pojo.Absent;
import com.spring.pojo.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AllFunctionImpl implements AllFunction {

    @Autowired
    private Function function;

    @Override
    public int addStudent(Student student) {
        return function.addStudent(student);
    }

    @Override
    public int selectStudentOne(String stuID) {
        return function.selectStudentOne(stuID);
    }

    @Override
    public List<Student> QueryLimit(int index) {
        return function.QueryLimit(index);
    }

    @Override
    public int selectAll() {
        return function.selectAll();
    }

    @Override
    public List<String> selectAbsentStudentID() {
        return function.selectAbsentStudentID();
    }

    @Override
    public Student QueryOneStudent(String index) {
        return function.QueryOneStudent(index);
    }

    @Override
    public List<Student> QueryStudentByName(String name) {
        return function.QueryStudentByName(name);
    }

    @Override
    public List<Absent> QueryAbsentStudentById(String studentID) {
        return function.QueryAbsentStudentById(studentID);
    }

    @Override
    public int deleteStudent(String id) {
        return function.deleteStudent(id);
    }

    @Override
    public int addAbsent(Absent absent) {
        return function.addAbsent(absent);
    }

    @Override
    public int updataAbsent(int id, Absent absent) {
        return function.updataAbsent(id,absent);
    }

    @Override
    public Absent QueryAbsentID(int id) {
        return function.QueryAbsentID(id);
    }

    @Override
    public int deleteAbsent(String id) {
        return function.deleteAbsent(id);
    }

    @Override
    public List<Absent> QueryAbsentStudentByCourseName(String courseName) {
        return function.QueryAbsentStudentByCourseName(courseName);
    }

    @Override
    public int updataStudent(String id,Student student) {
        return function.updataStudent(id,student);
    }
}
