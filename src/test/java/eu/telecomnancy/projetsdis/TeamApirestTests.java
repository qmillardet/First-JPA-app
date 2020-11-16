package eu.telecomnancy.projetsdis;

import eu.telecomnancy.projetsdis.entity.Team;
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
    
    @Test
    void testAddTeamnSuccess() {
        Team team = new Team();
        team.setName("Google");
        
        HttpEntity<Team> request = new HttpEntity<>(team);
        
        this.restTemplate.postForEntity("/team/create", request, String.class);
        
        //Verify request succeed
        String body = this.restTemplate.getForObject("/teams?name=Google", String.class);
        assertThat(body).contains("[{\"id\":16,\"creation\":\"")
                .contains("\",\"members\":[],\"name\":\"Google\",\"complete\":false}]");
    }
    
    @Test
    void testEditPersonSuccess() {
        Team team = new Team();
        team.setName("Apple");
        
        HttpEntity<Team> request = new HttpEntity<>(team);
        this.restTemplate.put("/team/15", request, String.class);
        
        //Verify request succeed
        String body = this.restTemplate.getForObject("/teams?name=Apple", String.class);
        assertThat(body).contains("[{\"id\":15,\"creation\":\"")
                .contains("\",\"members\":[],\"name\":\"Apple\",\"complete\":false}]");
    }
    
    @Test
    void testRemovePersonSuccess() {
        
        this.restTemplate.delete("/team/15");
        ResponseEntity<String> result = this.restTemplate.getForEntity("/team/15", String.class);
    
        //Verify request succeed
        Assert.assertEquals(404, result.getStatusCodeValue());
    }
    
    @Test
    void TeamMembers() {
        String body = this.restTemplate.getForObject("/team/13/members", String.class);
        assertThat(body).contains("{\"id\":6,\"firstName\":\"Camille\",\"lastName\":\"Knauss\",\"age\":110}").contains("{\"id\":3,\"firstName\":\"Kim\",\"lastName\":\"Bauer\",\"age\":22}").contains("{\"id\":8,\"firstName\":\"Pierre\",\"lastName\":\"Rapido\",\"age\":110}").contains("{\"id\":4,\"firstName\":\"David\",\"lastName\":\"Palmer\",\"age\":23}").contains("{\"id\":1,\"firstName\":\"Jack\",\"lastName\":\"Bauer\",\"age\":18}").contains("{\"id\":5,\"firstName\":\"Michelle\",\"lastName\":\"Dessler\",\"age\":110}").contains("{\"id\":7,\"firstName\":\"Jean-Mael\",\"lastName\":\"Itineo\",\"age\":110}").contains("{\"id\":2,\"firstName\":\"Chloe\",\"lastName\":\"O'Brian\",\"age\":18}");
    }
    
    @Test
    void teamComplete() {
        String body = this.restTemplate.getForObject("/teams/complete", String.class);
        assertThat(body).contains("{\"id\":13,\"creation\":\"").contains("\",\"members\":").contains("{\"id\":6,\"firstName\":\"Camille\",\"lastName\":\"Knauss\",\"age\":110}").contains("{\"id\":2,\"firstName\":\"Chloe\",\"lastName\":\"O'Brian\",\"age\":18}").contains("{\"id\":1,\"firstName\":\"Jack\",\"lastName\":\"Bauer\",\"age\":18}").contains("{\"id\":4,\"firstName\":\"David\",\"lastName\":\"Palmer\",\"age\":23}").contains("{\"id\":5,\"firstName\":\"Michelle\",\"lastName\":\"Dessler\",\"age\":110}").contains("{\"id\":7,\"firstName\":\"Jean-Mael\",\"lastName\":\"Itineo\",\"age\":110}").contains("{\"id\":8,\"firstName\":\"Pierre\",\"lastName\":\"Rapido\",\"age\":110}").contains("{\"id\":3,\"firstName\":\"Kim\",\"lastName\":\"Bauer\",\"age\":22}").contains("\"name\":\"Francais\",\"complete\":true}");
    }
}
