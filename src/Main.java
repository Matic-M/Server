import java.io.IOException;
import java.net.InetAddress;
import java.security.NoSuchAlgorithmException;

public class Main {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {

        Server server = new Server(InetAddress.getByAddress(new byte[]{127,0,0,1}),8080);

        server.start();
        server.send("/home/qkrakenx/Desktop/hereami.txt", server.getSocket());
        System.out.println(server.toString());
    }
}
