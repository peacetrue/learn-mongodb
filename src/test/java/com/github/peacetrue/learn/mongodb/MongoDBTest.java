package com.github.peacetrue.learn.mongodb;

import com.github.peacetrue.spring.beans.BeanUtils;
import com.github.peacetrue.util.stream.StreamUtils;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * @author peace
 **/
@Slf4j
public class MongoDBTest {

    private static final MongoClient mongoClient = MongoClients.create();
    private static final String dbname = "test";

    @BeforeAll
    static void beforeAll() {
        mongoClient.getDatabase(dbname).drop();
    }

    @Test
    void basic() {
        Assertions.assertTrue(StreamUtils.iteratorStream(mongoClient.listDatabaseNames().iterator()).noneMatch(db -> db.equals(dbname)), "默认没有 test 库");

        MongoDatabase database = mongoClient.getDatabase(dbname);
        Assertions.assertNotNull(database, "库不存在会自动创建");

        Assertions.assertFalse(StreamUtils.iteratorStream(database.listCollectionNames().iterator()).findAny().isPresent(), "库中没有集合");
        MongoCollection<Document> userCollection = database.getCollection("user");
        Assertions.assertNotNull(userCollection, "集合不存在会自动创建");

        Assertions.assertEquals(0, userCollection.countDocuments(), "集合中没有文档");
        User userNew = new User("1", "name", "password");
        Document userDocument = new Document(BeanUtils.getPropertyValues(userNew));
        userCollection.insertOne(userDocument);
        Document userPersist = userCollection.find().first();
        Assertions.assertNotNull(userPersist);
        Assertions.assertEquals(userDocument, userPersist);
    }

    @AfterAll
    static void afterAll() {
        mongoClient.getDatabase(dbname).drop();
    }
}
