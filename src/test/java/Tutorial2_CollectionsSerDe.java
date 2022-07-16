import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class Tutorial2_CollectionsSerDe {
    ObjectMapper mapper = new ObjectMapper();

    @DisplayName("Map<String, String> SerDe")
    @Test
    void testSerDeOfMapOfPrimitives() throws JsonProcessingException {
        Map<String, String> map = new HashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");

        /*
            {
                "key1":"value1",
                "key2":"value2"
            }
         */
        String se = mapper.writeValueAsString(map);
        Map<String, String> de = mapper.readValue(se, Map.class);
        Assertions.assertEquals(de, map);
    }

    @DisplayName("Map<String, Object> SerDe")
    @Test
    void testSerDeOfMapOfObjects() throws JsonProcessingException {
        Map<String, User> map = new HashMap<>();
        map.put("jh9310s", new User("jh9310s", 30));
        map.put("jh9310s2", new User("jh9310s2", 31));

        /*
            {
                "jh9310s2":{"name":"jh9310s2","age":31},
                "jh9310s":{"name":"jh9310s","age":30}
            }
         */
        String se = mapper.writeValueAsString(map);

        TypeReference<Map<String, User>> typeReference = new TypeReference<Map<String, User>>() {};
        Map<String, User> de = mapper.readValue(se, typeReference);
        Assertions.assertEquals(de, map);
    }


    @DisplayName("List<Primitive> SerDe")
    @Test
    void testSerDeOfListOfPrimitives() throws JsonProcessingException {
        List<String> values = Arrays.asList("ABC", "DEF");

        /*
            ["ABC","DEF"]
         */
        String se = mapper.writeValueAsString(values);
        List<String> de = mapper.readValue(se, List.class);
        Assertions.assertEquals(de, values);
    }

    @DisplayName("List<Object> SerDe")
    @Test
    void testSerDeOfListOfObjects() throws JsonProcessingException {
        /*
        1. List of 객체를 역직렬화 할 때는 TypeReference를 넘겨주어야 한다.
            * mapper.readValue(se, List.class)로 역직렬화하는 경우, java.util.LinkedHashMap로 역직렬화 된다.
                * [{name=jh9310s, age=30}, {name=jh9310s2, age=31}]
                * 따라서, 객체를 빼내서 사용할 때 이슈가 생긴다.
            * mapper.readValue(se, typeReference)로 역직렬화하는 경우,
                * [User(name=jh9310s, age=30), User(name=jh9310s2, age=31)]
         */
        List<User> users = Arrays.asList(new User("jh9310s", 30), new User("jh9310s2", 31));

        /*
            [{"name":"jh9310s","age":30},{"name":"jh9310s2","age":31}]
         */
        String se = mapper.writeValueAsString(users);

        /*
            아래 코드는 "CLassCastException: java.util.LinkedHashMap cannot be cast to User"이 발생한다.
            List<User> de = mapper.readValue(se, List.class);
            System.out.println(de.get(0).getName()); //User 객체 속성에 접근할 떄 에러 발생(런타임)
         */

        TypeReference<List<User>> typeReference = new TypeReference<List<User>>() {};
        List<User> de = mapper.readValue(se, typeReference);
        Assertions.assertEquals(de, users);
    }

    @DisplayName("List<Primitive>을 포함하는 객체 SerDe")
    @Test
    void testSerDeOfObjectWithListOfPrimitives() throws JsonProcessingException {
        Numbers n = new Numbers(Arrays.asList(10, 20, 30, 40, 50));

        /*
            {
                "numbers":[10,20,30,40,50]
            }
         */
        String se = mapper.writeValueAsString(n);
        Numbers de = mapper.readValue(se, Numbers.class);
        Assertions.assertEquals(de, n);
    }

    @DisplayName("List<Object>을 포함하는 객체 SerDe")
    @Test
    void testSerDeOfObjectWithCollectionOfCustomObjects() throws JsonProcessingException {
        Club c = new Club(
            "myclub",
            Arrays.asList(new User("jh9310s", 30), new User("jh9310s2", 31))
        );

        /*
            {
                "name":"myclub",
                "users":[
                    {"name":"jh9310s","age":30},
                    {"name":"jh9310s2","age":31}
                ]
            }
         */
        String se = mapper.writeValueAsString(c);
        Club de = mapper.readValue(se, Club.class);
        Assertions.assertEquals(de, c);
    }

}
