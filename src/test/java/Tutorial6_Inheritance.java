import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class Tutorial6_Inheritance {
    ObjectMapper mapper = new ObjectMapper();

    @DisplayName("역직렬화 시점에 다형성 적용하기 - @JsonTypeInfo, @JsonSubTypes를 Super 클래스에 적용하기")
    @Test
    void testSubTypeIsResolvedWhenDeserialized() throws JsonProcessingException {
        Engine e = new ReplicatedMergeTreeEngine("{/path/to/zk}", "{replica}");

        /*
            {
                "type":"ReplicatedMergeTree", //필드로 존재하지 않음에도 생겨난다. 이 필드는 역직렬화 시 어느 구체 타입으로 역직렬화할지 결정할 때 사용된다.
                "zkPath":"{/path/to/zk}",
                "replica":"{replica}"
            }
         */
        String se = mapper.writeValueAsString(e);
        Engine de = mapper.readValue(se, Engine.class);
        Assertions.assertEquals(de, e);
    }

}
