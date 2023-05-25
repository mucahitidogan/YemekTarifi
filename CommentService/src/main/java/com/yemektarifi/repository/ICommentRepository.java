package com.yemektarifi.repository;

import com.yemektarifi.repository.entity.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICommentRepository extends MongoRepository<Comment, String> {
    List<Comment> findByUserProfileId(String userProfileId);
}
