import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


public class Server {

    // encryption
    private static final String ENCRYPTION_ALGORITHM = "AES";

    private static final String SECRET_KEY = "3E7B5263C8ABD2F2A15F6D2A0A5B6F9A7C92E1C3A0D5371E5829FDE8C4376549";

    private InetAddress ip;
    private int port;
    private int backlog;

    private Socket socket;


    public Server(InetAddress ip, int port){
        this.ip=ip;
        this.port=port;
    }
    public Server(InetAddress ip, int port, int backlog){
        this(ip,port);
        this.backlog=backlog;
    }

    // server start - listening
    public void start(){

        try{
            // connection setup
            ServerSocket serverSocket = new ServerSocket(port,backlog,ip);
            System.out.println("Server successfully started! Listening on port: " + port );

            //accept client connection
            Socket socket = serverSocket.accept();
            this.socket=socket;

            System.out.println("Client with IP: " + socket.getInetAddress() + " connected!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // download file
    public void send(String path, Socket socket) throws IOException {

        try {

            SecretKey secretKey = new SecretKeySpec(hexToByteArray(SECRET_KEY), ENCRYPTION_ALGORITHM);

            // read
            File file = new File(path);
            byte[] data = Files.readAllBytes(file.toPath());

            // encryptedData
            Cipher cipher = Cipher.getInstance(ENCRYPTION_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedData = cipher.doFinal(data);

            // send
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(encryptedData);

            outputStream.flush();
            outputStream.close();

        } catch (IOException e) {

        } catch (NoSuchPaddingException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        } catch (BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    private static byte[] hexToByteArray(String hexString) {
        int length = hexString.length();
        byte[] byteArray = new byte[length / 2];
        for (int i = 0; i < length; i += 2) {
            byteArray[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
                    + Character.digit(hexString.charAt(i + 1), 16));
        }
        return byteArray;
    }

    // disconnect with client, close server socket
    public void disconnect(Socket socket, ServerSocket serverSocket) {
        try {
            socket.close();
        } catch (IOException e) {
            // Handle or log the exception
        } finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                // Handle or log the exception
            }
        }
    }


    public Socket getSocket(){
        return socket;
    }

    @Override
    public String toString() {
        return "Server{" +
                "ip=" + ip +
                ", port=" + port +
                ", socket=" + socket.toString() +
                '}';
    }
}
