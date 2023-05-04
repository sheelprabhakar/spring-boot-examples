package com.c4c.mongodb;

import com.c4c.mongodb.repository.documents.Doc;
import com.c4c.mongodb.repository.documents.DocumentLevel;
import com.c4c.mongodb.service.api.DocService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class MongoDbExampleApplicationTests {

	@Autowired
	private DocService docService;



	@Test
	void contextLoads() {
		init();
	}

	void init(){
		this.docService.clearCollection();
		DocumentLevel level = new DocumentLevel(1, "name1", "A");
		Map<Integer, DocumentLevel> levelMap = new HashMap<>();
		levelMap.put(1,level);
		Doc  doc = new Doc(1,"Accounts",levelMap );

		Doc docRe= this.docService.insert(doc);

		 level = new DocumentLevel(1, "name3", "A");
		 levelMap = new HashMap<>();
		levelMap.put(1,level);


	}


}
