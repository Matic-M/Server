import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {

        Properties properties = new Properties();
        FileInputStream inputStream = new FileInputStream("resources/config.properties");
        properties.load(inputStream);
        final String SECRET_KEY = properties.getProperty("SECRET_KEY");
        String PATH = properties.getProperty("PATH");



        Server server = new Server(InetAddress.getByAddress(new byte[]{127,0,0,1}),8080);

        server.start();
        server.send(server.getSocket(),PATH,SECRET_KEY);
        System.out.println(server.toString());
    }
}
