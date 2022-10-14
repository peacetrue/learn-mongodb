package com.github.peacetrue.learn.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;

/**
 * @author peace
 **/
public interface UserRepository extends MongoRepository<User, String>, CrudRepository<User, String> {

}
