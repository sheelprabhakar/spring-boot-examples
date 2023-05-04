package com.c4c.mongodb.repository.documents;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DocumentLevel {
    private int docLevelId;
    private String docLevelName;
    private String reporter;
}
