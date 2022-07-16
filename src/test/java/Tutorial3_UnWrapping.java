import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class Tutorial3_UnWrapping {
    ObjectMapper mapper = new ObjectMapper();


    @DisplayName("Map 객체 flattening - @JsonAnyGetter, @JsonAnySetter")
    @Test
    void testMapFlattening() throws JsonProcessingException {
        /*
        1. 직렬화 대상 객체 내 특정 필드를 Nested형태로 직렬화하기 위한 방법으로 @JsonGetter를 사용할 수 있다.
           참고. 이 경우에는 역직렬화도 고려해서 @JsonAnySetter를 지정해줄 필요가 있다
           참고. @JsonAnyGetter, @JsonAnySetter는 java.util.Map 타입에만 사용할 수 있다.
         */
        Map<String, String> properties = new HashMap<>();
        properties.put("key1", "value1");
        properties.put("key2", "value2");
        Table t = new Table("myTable", properties);

        /*
            {
                "name":"myTable",
                "key1":"value1", //원래대로라면 properties 필드 아래에 있어야 했지만 @JsonAnyGetter를 통해 flatten 가능하다.
                "key2":"value2"
            }
         */
        String se = mapper.writeValueAsString(t);
        Table de = mapper.readValue(se, Table.class);
        Assertions.assertEquals(de, t);
    }


    @DisplayName("커스텀 객체 flattening -  @JsonUnwrapped")
    @Test
    void testObjectFlattening() throws JsonProcessingException {
        /*
        1. 객체를 flattening하기 위해서는 @JsonUnwrapped를 필드에 사용하면 된다.
         */

        Team t = new Team("myTeam", new User("jh9310", 30));

        /*
            {
                "teamName":"myTeam",
                "name":"jh9310", //원래대로라면 user: { name:..., age:... }가 되어야 하지만 @JsonUnwrappeed를 통해 flatten 가능하다
                "age":30
            }
         */
        String se = mapper.writeValueAsString(t);
        Team de = mapper.readValue(se, Team.class);
        Assertions.assertEquals(de, t);
    }
}
