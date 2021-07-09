package com.spring.dao;


import com.spring.pojo.Absent;
import com.spring.pojo.Student;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface Function {

    /**
     * 增加学生记录
     */
    public int addStudent(@Param("student") Student student);

    /**
     * 查看是否存在该学生
     * @return 查找到的总数
     */
    @Select("select count(*) from checking.student where studentID = #{stu}")
    public int selectStudentOne(@Param("stu") String student);

    /**
     * 增加缺课记录
     */
    public int addAbsent(@Param("absent")Absent absent);

    /**
     * 分页查询学生记录
     */
    @Select("select * from checking.student limit #{index},10")
    public List<Student> QueryLimit(@Param("index") int index);

    /**
     * 统计所有学生
     */
    @Select("select count(*) from checking.student")
    public int selectAll();

    /**
     * 查询缺课表内，缺课学生ID
     */
    @Select("select studentID FROM checking.absent")
    public List<String> selectAbsentStudentID();

    /**
     * 根据学号查询一个学生信息
     * @return 返回一个学生信息
     */
    @Select("select * from checking.student where studentID = #{stu}")
    public Student QueryOneStudent(@Param("stu") String student);

    /**
     * 根据学生名字查找信息
     * @param name 姓名
     * @return 学生集合
     */
    @Select("SELECT * FROM checking.student where name LIKE CONCAT('%',#{name},'%')")
    public List<Student> QueryStudentByName(@Param("name") String name);

    /**
     * 依据课程名称查找缺课学生ID
     * @param courseName 课程名
     * @return 学生列表
     */
    @Select("SELECT ID,studentID FROM checking.absent where courseName LIKE CONCAT(\'%\',#{coursename},\'%\')")
    public List<Absent> QueryAbsentStudentByCourseName(@Param("coursename") String courseName);

    /**
     * 根据学号查询逃课信息
     * @param studentID 学号
     * @return 逃课信息集合
     */
    @Select("SELECT ID,checkData,period,courseName,absentType FROM checking.absent where studentID = #{id}")
    public List<Absent> QueryAbsentStudentById(@Param("id") String studentID);

    /**
     * 删除缺课记录
     */
    @Delete("delete from checking.absent WHERE studentID = #{id}")
    public int deleteAbsent(String id);

    /**
     * 通过学号删除学生记录
     * @param id 学号
     * @return 成功行数
     */
    @Delete("delete from checking.student where studentID = #{id}")
    public int deleteStudent(String id);

    /** 根据缺课ID查询出缺课信息
     * @param id 缺课的ID
     * @return 缺课信息
     */
    @Select("select ID,checkData,period,courseName,studentName,absentType,studentID from checking.absent where ID = #{id}")
    public Absent QueryAbsentID(int id);

    /**
     * 更新学生表记录
     * @param id   学生学号
     * @return 影响行数
     */
    @Update("UPDATE checking.student SET studentID = #{stu.studentID}, name = #{stu.name}, sex =  #{stu.sex}, age =  #{stu.age}, classNum = #{stu.classNum} WHERE studentID = #{beforeID}")
    public int updataStudent(@Param("beforeID") String id,@Param("stu") Student student);

    /**
     * 更新缺课表记录
     * @param id 原缺课ID
     * @param absent 更新缺课信息
     * @return 影响行数
     */
    @Update("UPDATE checking.absent SET checkData = #{absent.checkData}, period = #{absent.period}, courseName = #{absent.courseName}, studentName = #{absent.studentName}, absentType = #{absent.absentType}, studentID = #{absent.studentID} WHERE ID = #{id}")
    public int updataAbsent(@Param("id") int id,@Param("absent") Absent absent);

}
