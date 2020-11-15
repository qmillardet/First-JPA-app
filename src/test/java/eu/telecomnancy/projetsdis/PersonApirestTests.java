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
    
}
