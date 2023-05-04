package com.c4c.mongodb.service.impl;

import com.c4c.mongodb.repository.DocsRepository;
import com.c4c.mongodb.repository.documents.Doc;
import com.c4c.mongodb.service.api.DocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DocServiceImpl implements DocService {

    @Autowired
    private DocsRepository repository;

    @Override
    public Doc insert(final Doc doc){

        return this.repository.insert(doc);
    }

    @Override
    public void clearCollection(){
        this.repository.deleteAll();
    }
}
