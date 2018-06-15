package ml.pevgen.example.springhazelcast.web;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Slf4j
@RestController
public class HazelcastMapResource {

    private HazelcastInstance hazelcastInstance;

    public HazelcastMapResource(HazelcastInstance hazelcastInstance) {
        this.hazelcastInstance = hazelcastInstance;
    }

    @RequestMapping("/to")
    public String toHazelcast(@RequestParam("test") String testParam) {
       log.info("testParam = {}", testParam);

        IMap<LocalDateTime, String> hzMap = hazelcastInstance.getMap("data");
        hzMap.put(LocalDateTime.now(), testParam);

        return "response: " + testParam + "; map.size =" + hzMap.size();
    }

    @RequestMapping("/from")
    public String toHazelcast() {

        StringBuilder result = new StringBuilder("response: ");
        IMap<LocalDateTime, String> hzMap = hazelcastInstance.getMap("data");
        hzMap.forEach((k,v) ->
            result.append("; key = ").append(k).append("; v = ").append(v).append("\n")
        );

        return result.toString();
    }

}
