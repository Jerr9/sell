package com.o2ii.sell.mapper;

import com.o2ii.sell.dataobject.User;
import org.apache.ibatis.annotations.*;

import java.util.Map;

public interface UserMapper {
    @Insert("insert into t_user(uid, openid, mobile, sex, age, password, nickname) values(" +
            "#{uid, jdbcType=VARCHAR}, " +
            "#{openId, jdbcType=VARCHAR}, " +
            "#{mobile, jdbcType=VARCHAR}, " +
            "#{sex, jdbcType=TINYINT}, " +
            "#{age, jdbcType=TINYINT}, " +
            "#{password, jdbcType=VARCHAR}, " +
            "#{nickname, jdbcType=VARCHAR})"
    )
    int insertByMap(Map<String, Object> map);

    @Insert("insert into t_user(uid, openid, mobile, sex, age, password, nickname) values(" +
            "#{uid, jdbcType=VARCHAR}, " +
            "#{openId, jdbcType=VARCHAR}, " +
            "#{mobile, jdbcType=VARCHAR}, " +
            "#{sex, jdbcType=TINYINT}, " +
            "#{age, jdbcType=TINYINT}, " +
            "#{password, jdbcType=VARCHAR}, " +
            "#{nickname, jdbcType=VARCHAR})"
    )
    int insertByObject(User user);

    @Select("select * from t_user where uid = #{uid}")
    @Results({
            @Result(column = "openid", property = "openId")
    })
    User findByUid(String uid);

    @Update("update t_user set mobile=#{mobile}, age=#{age} where uid = #{uid}")
    int updateAgeAndMobileByUid(@Param("uid") String uid, @Param("mobile") String mobile, @Param("age") Integer age);

    User selectByUid(String uid);
}
