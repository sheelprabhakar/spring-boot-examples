package com.c4c.mongodb.repository;

import com.c4c.mongodb.repository.documents.Doc;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface DocsRepository extends MongoRepository<Doc, Integer> {

    @Query("{department:'?0'}")
    Doc findDocByDepartment(String department);
    public long count();

}