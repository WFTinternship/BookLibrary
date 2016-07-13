/**
 * Created by ${Sona} on 7/13/2016.
 */
public class GenClass {
    protected void closeResource(AutoCloseable closeable){
        try{
            if (closeable != null ) closeable.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
