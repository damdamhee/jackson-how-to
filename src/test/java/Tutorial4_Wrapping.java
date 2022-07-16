import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class Tutorial4_Wrapping {
    ObjectMapper mapper = new ObjectMapper();

    @DisplayName("객체를 특정 필드 아래에 두기#1 - mapper추가 설정 + @JsonRootValue")
    @Test
    void testNesting() throws JsonProcessingException {
        /*
        1. 객체를 직렬화하면 기본적으로 해당 객체는 루트 그 자체이다.
            ex. class User ( String name, int age )
                { name:... , age:... }
           하지만 { name:... , age:... }를 특정 필드 아래 두고 싶을 수도 있다.
            ex. { user : { name:..., age:... } }

        1. 이를 위해서는 ObjectMapper의 기본 설정을 바꾸어주어야 한다.
            * SerializationFeature
            * DeserializationFeature
         */

        Address a = new Address("gyeongi", "suwon", "yeongtong");
        /*
            {
                "doo":"gyeongi",
                "si":"suwon",
                "goo":"yeongtong"
            }
         */
        String se = mapper.writeValueAsString(a);

        mapper.enable(SerializationFeature.WRAP_ROOT_VALUE); //default false
        mapper.enable(DeserializationFeature.UNWRAP_ROOT_VALUE); //default false
        /*
            {
                "address":{
                    "doo":"gyeongi",
                    "si":"suwon",
                    "goo":"yeongtong"
                }
            }
         */
        String se2 = mapper.writeValueAsString(a);
        Address de = mapper.readValue(se2, Address.class);
    }


}
