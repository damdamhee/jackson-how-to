import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class Tutorial5_CustomSerDe {
    ObjectMapper mapper = new ObjectMapper();

    @DisplayName("가장 간편한 커스텀 직렬화 방법 - @JsonValue")
    @Test
    void testBasicSerDe() throws JsonProcessingException {
        Drink d = new Drink("orange_juice", 100);

        /*
            "juice_orange_juice:100"
        */
        String se = mapper.writeValueAsString(d);
        //Drink de = mapper.readValue(se, Drink.class); //역직렬화 실패
    }

    @DisplayName("Custom Serializer, Deserializer 정의 후, 각 필드에 적용하기 - @JsonSerializer, @JsonDeserializer")
    @Test
    void testCustomSerDe() throws JsonProcessingException {
        Cafe cafe = new Cafe("myCafe", new Address("gyeonggido", "suwonsi", "yeongtongoo"));

        /*
            {
                "name":"myCafe",
                "address":"gyeonggido-suwonsi-yeongtongoo"
            }
         */
        String se = mapper.writeValueAsString(cafe);
        Cafe de = mapper.readValue(se, Cafe.class);
        Assertions.assertEquals(de, cafe);
    }
}