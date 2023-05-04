package com.c4c.mongodb.service.api;

import com.c4c.mongodb.repository.documents.Doc;

public interface DocService {
    Doc insert(Doc doc);

    void clearCollection();
}
