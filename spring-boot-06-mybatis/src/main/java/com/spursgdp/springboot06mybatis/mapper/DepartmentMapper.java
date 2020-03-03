package com.spursgdp.springboot06mybatis.mapper;

import com.spursgdp.springboot06mybatis.bean.Department;
import org.apache.ibatis.annotations.*;

/**
 * @author zhangdongwei
 * @create 2020-03-03-15:16
 */

//@Mapper
public interface DepartmentMapper {

    @Select("select * from department where id=#{id}")
    public Department getDeptById(Integer id);

    @Options(useGeneratedKeys = true,keyProperty = "id")
//    @Insert("insert into department(departmentName) values(#{departmentName})")
    @Insert("insert into department(department_name) values(#{departmentName})")
    public Integer insertDept(Department dept);

    @Delete("delete from department where id=#{id}")
    public Integer deleteDeptById(Integer id);

//    @Update("update department set departmentName=#{departmentName} where id=#{id}")
    @Update("update department set department_name=#{departmentName} where id=#{id}")
    public Integer updateDept(Department dept);

}
