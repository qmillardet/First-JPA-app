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
class TeamApirestTests {
    
    @Autowired
    private TestRestTemplate restTemplate;
    
    @Test
    void teamsAll() {
        String body = this.restTemplate.getForObject("/teams", String.class);
        assertThat(body).contains("{\"id\":13,\"creation\":\"")
                .contains("{\"id\":2,\"firstName\":\"Chloe\",\"lastName\":\"O'Brian\",\"age\":18}")
                .contains("\"name\":\"Francais\",\"complete\":true}");
    }
    
    @Test
    void TeamsName() {
        String body = this.restTemplate.getForObject("/teams?name=Japonnais", String.class);
        assertThat(body).contains("[{\"id\":15,\"creation\":\"")
                .contains("\",\"members\":[],\"name\":\"Japonnais\",\"complete\":false}]");
    }
    
}
