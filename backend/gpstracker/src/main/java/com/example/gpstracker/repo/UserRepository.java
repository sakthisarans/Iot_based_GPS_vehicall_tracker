package com.example.gpstracker.repo;

import com.example.gpstracker.pojo.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User,String> {
    @Query("{'username':?0}")
    public User fingByEmail(String email);
    public List<User> findAll();
}
