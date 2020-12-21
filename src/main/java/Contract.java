import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;

class ContractField<T> {
    Type type;
    T value;

    ContractField(Type type, T value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public String toString() {
        return type + " " + value;
    }
}

public class Contract {
    HashMap<String, ContractField<?>> fields;

    private ContractField<?> getValueField(Field f, Object o) {
        Type type;
        type = f.getGenericType();
        f.setAccessible(true);
        try {
            return switch (type.getTypeName()) {
                case "int" -> new ContractField<>(type, f.getInt(o));
                case "long" -> new ContractField<>(type, f.getLong(o));
                case "boolean" -> new ContractField<>(type, f.getBoolean(o));
                default -> new ContractField<>(type, "not primitive");
            };
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return new ContractField<>(type, "not primitive");
    }

    public Contract(Object ob) {
        fields = new HashMap<>();
        Arrays.stream(ob.getClass().getDeclaredFields()).forEach(field -> fields.put(field.getName(), getValueField(field, ob)));
    }

}
