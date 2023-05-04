package com.c4c.mongodb.repository.documents;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document("docs")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Doc {

    @Id
    private Integer docId;

    private String department;

    private Map<Integer, DocumentLevel> docLevel;

}

