import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Cafe {
    private String name;
    @JsonDeserialize(using=AddressDeserializer.class)
    @JsonSerialize(using=AddressSerializer.class)
    private Address address;

}

class AddressSerializer extends StdSerializer<Address> {

    public AddressSerializer() {
        this(null);
    }

    protected AddressSerializer(Class<Address> t) {
        super(t);
    }

    @Override
    public void serialize(Address value, JsonGenerator gen, SerializerProvider provider)
        throws IOException {
        String customSerialized = String.format("%s-%s-%s", value.getDoo(), value.getSi(), value.getGoo());
        gen.writeString(customSerialized);
    }
}

class AddressDeserializer extends StdDeserializer<Address> {

    public AddressDeserializer() {
        this(null);
    }
    protected AddressDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Address deserialize(JsonParser p, DeserializationContext ctxt)
        throws IOException, JacksonException {
        String text = p.getText();
        String[] split = text.split("-");
        return new Address(split[0], split[1], split[2]);
    }
}
