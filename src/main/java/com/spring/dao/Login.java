package com.spring.dao;

import com.spring.pojo.UserLogin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
//@Mapper
public interface Login {

    @Select("select username,password from checking.login where username=#{username}")
    public UserLogin Login(@Param("username") String username);
}
