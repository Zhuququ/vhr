package org.javaboy.vhr.mapper;

import org.apache.ibatis.annotations.Param;
import org.javaboy.vhr.model.HrRole;
import org.springframework.security.core.parameters.P;

public interface HrRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(HrRole record);

    int insertSelective(HrRole record);

    HrRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(HrRole record);

    int updateByPrimaryKey(HrRole record);

    // 若参数个数为一个，则不用加@Param
    void deleteByHrid(Integer hrid);

    Integer insertHrRoles(@Param("hrid") Integer hrid,@Param("rids") Integer[] rids);
}