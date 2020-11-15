package eu.telecomnancy.projetsdis;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApirestApplicationTests {
    
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
        assertThat(body).contains("[{\"id\":1,\"firstName\":\"Jack\",\"lastName\":\"Bauer\"},{\"id\":2,\"firstName\":\"Chloe\",\"lastName\":\"O'Brian\"},{\"id\":3,\"firstName\":\"Kim\",\"lastName\":\"Bauer\"},{\"id\":4,\"firstName\":\"David\",\"lastName\":\"Palmer\"},{\"id\":5,\"firstName\":\"Michelle\",\"lastName\":\"Dessler\"},{\"id\":6,\"firstName\":\"Camille\",\"lastName\":\"Knauss\"},{\"id\":7,\"firstName\":\"Jean-Mael\",\"lastName\":\"Itineo\"},{\"id\":8,\"firstName\":\"Pierre\",\"lastName\":\"Rapido\"},{\"id\":9,\"firstName\":\"George\",\"lastName\":\"EuraMobile\"},{\"id\":10,\"firstName\":\"Pierette\",\"lastName\":\"Pilote\"},{\"id\":11,\"firstName\":\"Jeanne\",\"lastName\":\"Hymmer\"},{\"id\":12,\"firstName\":\"Jeanne\",\"lastName\":\"Hymmer\"}]");
        
    }
    
    
    @Test
    void personFirstName() {
        String body = this.restTemplate.getForObject("/persons?firstname=Jack", String.class);
        assertThat(body).contains("[{\"id\":1,\"firstName\":\"Jack\",\"lastName\":\"Bauer\"}]");
    }
    
    @Test
    void personLastName() {
        String body = this.restTemplate.getForObject("/persons?lastname=Bauer", String.class);
        assertThat(body).contains("[{\"id\":1,\"firstName\":\"Jack\",\"lastName\":\"Bauer\"},{\"id\":3,\"firstName\":\"Kim\",\"lastName\":\"Bauer\"}]");
    }
    
    @Test
    void personLastNameAndFirstName() {
        String body = this.restTemplate.getForObject("/persons?lastname=Bauer&firstname=Jack", String.class);
        assertThat(body).contains("[{\"id\":1,\"firstName\":\"Jack\",\"lastName\":\"Bauer\"}]");
    }
    
    @Test
    void teamsAll() {
        String body = this.restTemplate.getForObject("/teams", String.class);
        assertThat(body).contains("{\"id\":13,\"creation\":\"")
                .contains("{\"id\":2,\"firstName\":\"Chloe\",\"lastName\":\"O'Brian\"}")
                .contains("\"name\":\"Francais\",\"complete\":true}");
    }
    
    @Test
    void TeamsName() {
        String body = this.restTemplate.getForObject("/teams?name=Japonnais", String.class);
        assertThat(body).contains("[{\"id\":15,\"creation\":\"")
                .contains("\",\"members\":[],\"name\":\"Japonnais\",\"complete\":false}]");
    }
    
}
