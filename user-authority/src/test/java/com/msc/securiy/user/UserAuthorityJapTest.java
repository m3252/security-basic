package com.msc.securiy.user;

import com.mongodb.MongoWriteException;
import com.msc.securiy.user.domain.Authority;
import com.msc.securiy.user.domain.User;
import com.msc.securiy.user.repository.UserRepository;
import com.msc.securiy.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataMongoTest
public class UserAuthorityJapTest {

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private UserRepository userRepository;

    private UserService userService;

    private UserTestHelper testHelper;

    @BeforeEach
    void before(){
        this.userRepository.deleteAll();
        this.userService = new UserService(mongoTemplate, userRepository);
        this.testHelper = new UserTestHelper(userService);
    }

    @DisplayName("1. 사용자를 생성한다.")
    @Test
    void test_1(){
        testHelper.createUser("user1");
        List<User> userList = this.userRepository.findAll();

        assertEquals(1, userList.size());
        testHelper.assertUser(userList.get(0), "user1");
    }

    @DisplayName("2. 사용자의 이름을 수정한다.")
    @Test
    void test_2(){
        User user1 = testHelper.createUser("user1");
        userService.updateUserName(user1.getUserId(), "user2");

        User savedUser = userService.findUser(user1.getUserId()).get();
        assertEquals("user2", savedUser.getName());
    }

    @DisplayName("3. authority 를 부여한다.")
    @Test
    void test_3(){
        User user1 = testHelper.createUser("user1", Authority.ROLE_USER);
        userService.addAuthority(user1.getUserId(), Authority.ROLE_ADMIN);
        User savedUser = userService.findUser(user1.getUserId()).get();
        testHelper.assertUser(savedUser, "user1", Authority.ROLE_USER, Authority.ROLE_ADMIN);
    }

    @DisplayName("4. authority 를 뺐는다.")
    @Test
    void test_4(){
        User user1 = testHelper.createUser("admin", Authority.ROLE_USER, Authority.ROLE_ADMIN);
        userService.removeAuthority(user1.getUserId(), Authority.ROLE_USER);
        User savedUser = userService.findUser(user1.getUserId()).get();
        testHelper.assertUser(savedUser, "admin", Authority.ROLE_ADMIN);
    }

    @DisplayName("5. email 로 검색이 된다.")
    @Test
    void test_5(){
        User user1 = testHelper.createUser("user1");
        User savedUser = (User) userService.loadUserByUsername("user1@test.com");
        testHelper.assertUser(savedUser, "user1");
    }

    @DisplayName("6. role 이 중복되서 추가되지 않는다.")
    @Test
    void test_6(){
        User user1 = testHelper.createUser("user1", Authority.ROLE_USER);
        userService.addAuthority(user1.getUserId(), Authority.ROLE_USER);
        User savedUser = userService.findUser(user1.getUserId()).get();
        testHelper.assertUser(savedUser, "user1", Authority.ROLE_USER);
    }

    @DisplayName("7. email 이 중복되어서 들어가는가 ? ")
    @Test
    void test_7(){
        testHelper.createUser("user1");
        assertThrows(DuplicateKeyException.class, ()->{
            testHelper.createUser("user1");
        });
    }


}
