import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@EqualsAndHashCode
public class Email {
    private final String id;
    private final String domain;

    @JsonCreator
    public Email(@JsonProperty("id") String id,@JsonProperty("domain") String domain) {
        this.id = id;
        this.domain = domain;
    }
}
