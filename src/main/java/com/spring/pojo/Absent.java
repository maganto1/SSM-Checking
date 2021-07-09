package com.spring.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Alias("absent")
public class Absent {
    int ID;
    String checkData;
    String period;
    String courseName;
    String studentName;
    String absentType;
    String studentID;
}
