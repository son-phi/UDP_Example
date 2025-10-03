import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPClient {
    public static void main(String[] args) throws IOException {
        DatagramSocket client = new DatagramSocket();

        String msg = "ptit abc xyz jnp 2025";
        byte[] data = msg.getBytes();

        InetAddress serverAddress = InetAddress.getByName("10.24.0.100");
        DatagramPacket dpRequest = new DatagramPacket(data, data.length, serverAddress, 2207);

        client.send(dpRequest);
        System.out.println("Client sent: " + msg);

        byte[] buff = new byte[1024];
        DatagramPacket dpResponse = new DatagramPacket(buff, buff.length);
        client.receive(dpResponse);

        String strReverse = new String(dpResponse.getData(), 0, dpResponse.getLength());
        System.out.println("Reverse string: " + strReverse);

        client.close();
    }
}
