import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    property = "type")
@JsonSubTypes({
    @Type(value = MergeTreeEngine.class, name = "MergeTree"),
    @Type(value = ReplicatedMergeTreeEngine.class, name = "ReplicatedMergeTree")
})
public abstract class Engine {

}
