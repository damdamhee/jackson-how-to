import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Drink {
    private String name;
    private int price;

    @JsonValue
    public String getValue() {
        return String.format("juice_%s:%s", name, price);
    }
}
