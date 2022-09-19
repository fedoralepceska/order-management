package mk.finki.ukim.emt.ordermanagement.xport.client;

import mk.finki.ukim.emt.ordermanagement.domain.valueobjects.Room;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.List;

@Service
public class RoomClient {
    private final RestTemplate restTemplate;
    private final String serverUrl;

    public RoomClient(@Value("${app.room-catalog.url}") String serverUrl) {
        this.restTemplate = new RestTemplate();
        this.serverUrl = serverUrl;
        var requestFactory = new SimpleClientHttpRequestFactory();
        this.restTemplate.setRequestFactory(requestFactory);
    }

    private UriComponentsBuilder uri(){
        return UriComponentsBuilder.fromUriString(this.serverUrl);
    }

    public List<Room> findAll(){
        try {
            return restTemplate.exchange(uri().path("/api/rooms").build().toUri(),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Room>>() {}).getBody();
        }catch(Exception e){
            return Collections.emptyList();
        }
    }
}
