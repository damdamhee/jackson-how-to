import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@JsonRootName("address")
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Address {
   private String doo;
   private String si;
   private String goo;
}
