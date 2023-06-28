import java.io.IOException;
import java.net.InetAddress;
import java.security.NoSuchAlgorithmException;

public class Main {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {

        Server server = new Server(InetAddress.getByAddress(new byte[]{127,0,0,1}),8080);

        server.start();
        server.send("/home/qkrakenx/Prejemi/Narcos (2015) Season 1 S01 (1080p BluRay x265 HEVC 10bit AAC 5.1 Vyndros)/Narcos.S01E10.Despegue.1080p.10bit.BluRay.AAC5.1.HEVC-Vyndros.mkv", server.getSocket());
        System.out.println(server.toString());
    }
}
