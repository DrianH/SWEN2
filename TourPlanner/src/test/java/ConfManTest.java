import com.example.tourplanner.BuisnessLayer.Manager.ConfigPropManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConfManTest {

    private static final ConfigPropManager configurationManager = new ConfigPropManager();

    private static final String cs = "jdbc:postgresql://localhost:5432/postgres";
    private static final String user = "postgres";
    private static final String password = "postgres";
    private static final String key = "tbkD2woDXNpALt0pWnBLVrmOy9Zkuv0f";
    private static final String secret = "WlCUKiATxT0xGFAl";



    @Test
    public void test_TestProp() {
        String result = configurationManager.getConfigProp("test-prop");

        assertEquals(result, "test");
    }

    @Test
    public void test_DbConnectionString() {
        String result = configurationManager.getConfigProp("db-cs");

        assertEquals(result, cs);
    }


    @Test
    public void test_DbUser() {
        String result = configurationManager.getConfigProp("db-user");

        assertEquals(result, user);
    }


    @Test
    public void test_DbPassword() {
        String result = configurationManager.getConfigProp("db-password");

        assertEquals(result, password);
    }


    @Test
    public void test_ConsumerKey() {
        String result = configurationManager.getConfigProp("mq-key");

        assertEquals(result, key);
    }


    @Test
    public void test_ConsumerSecret() {
        String result = configurationManager.getConfigProp("mq-secret");

        assertEquals(result, secret);
    }
}
