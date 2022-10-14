package com.github.peacetrue.learn.mongodb;

import com.mongodb.client.MongoClient;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * @author peace
 **/
@SpringBootTest(
        classes = {
                MongoAutoConfiguration.class,
                MongoDataAutoConfiguration.class,
        },
        properties = {
                "spring.data.mongodb.database=test"
        }
)
@Slf4j
@EnableMongoRepositories
public class MongoDBSpringTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void basic() {
        Assertions.assertNotNull(userRepository);
        Assertions.assertTrue(userRepository.findAll().isEmpty());

        User user = userRepository.insert(new User(null, "name", "password"));
        Assertions.assertTrue(userRepository.findById(user.getId()).isPresent());
    }
}
