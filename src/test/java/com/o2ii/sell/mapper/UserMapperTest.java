package com.o2ii.sell.mapper;

import com.o2ii.sell.dataobject.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // 解决websocket导致单元测试失败的问题
class UserMapperTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    void insertByMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("uid", "hellomaodemingzichoukule66666");
        map.put("openId", "oFtQywLXiiIJ5X52uxLYsYUSO22M");
        map.put("nickname", "猫的名字丑哭了");
        map.put("mobile", "18316556113");
        map.put("age", 18);
        map.put("sex", 1);
        map.put("password", "wodetian13");
        int result = userMapper.insertByMap(map);
        Assert.assertEquals(1, result);
    }

    @Test
    void insertByObject() {
        User user = new User();
        user.setUid("uidxxxxx");
        user.setOpenId("openIdxxxxxxx");
        user.setMobile("18316556111");
        user.setSex(1);
        user.setAge(18);
        user.setPassword("passwordddddd");
        user.setNickname("Jerryzzz");
        int result = userMapper.insertByObject(user);
        Assert.assertEquals(1, result);
    }

    @Test
    void findByUid() {
        String uid = "hellomaodemingzichoukule66666";
        User user = userMapper.findByUid(uid);
        Assert.assertEquals(uid, user.getUid());
    }

    @Test
    void updateAgeAndMobileByUid() {
        int result = userMapper.updateAgeAndMobileByUid("hellomaodemingzichoukule66666", "18316556112", 18);
        Assert.assertEquals(result, 1);
    }

    @Test
    void selectByUid() {
        User user = userMapper.selectByUid("hellomaodemingzichoukule66666");
        Assert.assertEquals(user.getUid(), "hellomaodemingzichoukule66666");
    }
}