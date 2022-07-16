import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class Tutorial1_Basics {
    ObjectMapper mapper = new ObjectMapper();

    @DisplayName("기본 타입만을 포함하는 객체 SerDe#1 - 일반 객체인 경우")
    @Test
    void testSerDeOfObjectWithPrimitives() throws JsonProcessingException {
        /*
        1. Getter 부재 시
            * No serializer found for class Email and no properties discovered to create BeanSerializer
            => Se. 대상 객체에는 무조건 Getter를 붙이자
        1. 기본 생성자 부재시
            * com.fasterxml.jackson.databind.exc.InvalidDefinitionException:
              Cannot construct instance of `Email` (no Creators, like default constructor, exist)
            => De. 대상 객체에는 기본 생성자를 정의해주자. 단 접근 지시자를 protected or private으로 해주자
        */

        PersonEntity personEntity = new PersonEntity("jh9310s", "korea");

        /*
            { //PersonEntity
                "name":"jh9310s",
                "country":"korea"
            }
         */
        String se = mapper.writeValueAsString(personEntity);
        PersonEntity de = mapper.readValue(se, PersonEntity.class);

        Assertions.assertEquals(de,personEntity);
    }

    @DisplayName("기본 타입만을 포함하는 객체 SerDe#2 - Value 객체인 경우 - @JsonCreator, @JsonProperty")
    @Test
    void testSerDeOfValueObjectWithPrimitives() throws JsonProcessingException {
        /*
        1. Value 객체는 멤버를 모두 final로 선언한다. 따라서 기본 생성자를 가질 수 없다.
            * No serializer found for class Email and no properties discovered to create BeanSerializer
            => @JsonCreator, @JsonProperty 사용하면 역질렬화 시점에 기본생성자 대신 다른 생성자를 사용하도록 지정할 수 있다.
         */

        Email email = new Email("jh9310s", "google");

        /*
            { //Email
                "id":"jh9310s",
                "domain":"google"
            }
        */
        String se = mapper.writeValueAsString(email);
        Email de = mapper.readValue(se, Email.class);
        Assertions.assertEquals(de, email);

    }

    @DisplayName("Custom Object를 포함하는 객체 SerDe")
    @Test
    void testSerDeOfObjectWithCustomFields() throws JsonProcessingException {
        Profile profile = new Profile(new User("jh9310s", 30));

        /*
            { //Profile
                "user":{ //User
                    "name":"jh9310s",
                    "age":30
                }
            }
         */
        String se = mapper.writeValueAsString(profile);
        Profile de = mapper.readValue(se, Profile.class);
        Assertions.assertEquals(de, profile);
    }

}
