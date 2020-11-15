package eu.telecomnancy.projetsdis;

import eu.telecomnancy.projetsdis.entity.Person;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class PersonApirestTests {
    
    @Autowired
    private TestRestTemplate restTemplate;
    
    @Test
    void homePage() {
        String body = this.restTemplate.getForObject("/", String.class);
        assertThat(body).contains("/teams")
                .contains("/persons");
    }
    
    @Test
    void personsListTest() {
        String body = this.restTemplate.getForObject("/persons", String.class);
        assertThat(body).contains("[{\"id\":1,\"firstName\":\"Jack\",\"lastName\":\"Bauer\",\"age\":18},{\"id\":2,\"firstName\":\"Chloe\",\"lastName\":\"O'Brian\",\"age\":18},{\"id\":3,\"firstName\":\"Kim\",\"lastName\":\"Bauer\",\"age\":22},{\"id\":4,\"firstName\":\"David\",\"lastName\":\"Palmer\",\"age\":23},{\"id\":5,\"firstName\":\"Michelle\",\"lastName\":\"Dessler\",\"age\":110},{\"id\":6,\"firstName\":\"Camille\",\"lastName\":\"Knauss\",\"age\":110},{\"id\":7,\"firstName\":\"Jean-Mael\",\"lastName\":\"Itineo\",\"age\":110},{\"id\":8,\"firstName\":\"Pierre\",\"lastName\":\"Rapido\",\"age\":110},{\"id\":9,\"firstName\":\"George\",\"lastName\":\"EuraMobile\",\"age\":110},{\"id\":10,\"firstName\":\"Pierette\",\"lastName\":\"Pilote\",\"age\":110},{\"id\":11,\"firstName\":\"Jeanne\",\"lastName\":\"Hymmer\",\"age\":110},{\"id\":12,\"firstName\":\"Jeanne\",\"lastName\":\"Hymmer\",\"age\":110}]");
        
    }
    
    
    @Test
    void personFirstName() {
        String body = this.restTemplate.getForObject("/persons?firstname=Jack", String.class);
        assertThat(body).contains("[{\"id\":1,\"firstName\":\"Jack\",\"lastName\":\"Bauer\",\"age\":18}]");
    }
    
    @Test
    void personLastName() {
        String body = this.restTemplate.getForObject("/persons?lastname=Bauer", String.class);
        assertThat(body).contains("[{\"id\":1,\"firstName\":\"Jack\",\"lastName\":\"Bauer\",\"age\":18},{\"id\":3,\"firstName\":\"Kim\",\"lastName\":\"Bauer\",\"age\":22}]");
    }
    
    @Test
    void personLastNameAndFirstName() {
        String body = this.restTemplate.getForObject("/persons?lastname=Bauer&firstname=Jack", String.class);
        assertThat(body).contains("[{\"id\":1,\"firstName\":\"Jack\",\"lastName\":\"Bauer\",\"age\":18}]");
    }
    
    @Test
    void testAddPersonSuccess() {
        Person person = new Person("Quentin", "Millardet", 72);
        
        HttpEntity<Person> request = new HttpEntity<>(person);
        
        this.restTemplate.postForEntity("/person/create", request, String.class);
        
        //Verify request succeed
        String body = this.restTemplate.getForObject("/persons?firstname=Quentin", String.class);
        assertThat(body).contains("[{\"id\":16,\"firstName\":\"Quentin\",\"lastName\":\"Millardet\",\"age\":72}]");
    }
    
    @Test
    void testEditPersonSuccess() {
        Person person = new Person("Jeane", "Do", 1);
        
        HttpEntity<Person> request = new HttpEntity<>(person);
        this.restTemplate.put("/person/3", request, String.class);
        
        //Verify request succeed
        String body = this.restTemplate.getForObject("/persons?firstname=Jeane", String.class);
        assertThat(body).contains("[{\"id\":3,\"firstName\":\"Jeane\",\"lastName\":\"Do\",\"age\":1}]");
    }
    
    @Test
    void testRemovePersonSuccess() {
        
        this.restTemplate.delete("/person/2");
        ResponseEntity<String> result = this.restTemplate.getForEntity("/person/2", String.class);
        
        //Verify request succeed
        Assert.assertEquals(404, result.getStatusCodeValue());
    }
    
    
}
