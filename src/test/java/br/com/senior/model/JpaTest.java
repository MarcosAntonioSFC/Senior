package br.com.senior.model;

import javax.persistence.EntityManager;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2, replace = AutoConfigureTestDatabase.Replace.ANY)
@SpringBootTest(classes = JpaTest.class,
    properties = {
        "debug=true",
        "spring.jpa.show-sql=true",
        "spring.jpa.hibernate.ddl-auto=create"
    }, webEnvironment = SpringBootTest.WebEnvironment.NONE)
@EnableAutoConfiguration
@ComponentScan(value = JpaTest.BASE_PACKAGE_ENTITY)
public class JpaTest {

  public static final String BASE_PACKAGE_ENTITY = "br.com.senior.model";

  @Autowired
  private EntityManager entityManager;

  @Test
  public void testEntities() {
    Assert.assertNotNull(entityManager);
  }

}