import java.lang.reflect.Field;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Test con1 = new Test(1, 456, true);
        Field[] fields = con1.getClass().getDeclaredFields();
        Arrays.stream(fields).map(Field::getName).forEach(System.out :: println);
        try {
            fields[0].setAccessible(true);
            int w = fields[0].getInt(con1);
            System.out.println(w);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        System.out.println(con1.getClass().getName());

        System.out.println(new Contract(con1).fields.toString());
        System.out.println(new Contract(con1).fields.get("id").value + 2);
    }
}
