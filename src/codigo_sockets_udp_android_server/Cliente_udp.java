package codigo_sockets_udp_android_server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;

public class Cliente_udp {
    
    public int puertoServidor;
    public InetAddress hostServidor;
    
    public ArrayList<String> chat = new ArrayList<>();

    public Cliente_udp(int puertoServidor, InetAddress hostServidor) {
        this.puertoServidor = puertoServidor;
        this.hostServidor = hostServidor;
    }
    
    

    public void actualizar() throws SocketException, IOException {
        
        DatagramSocket socketUDP = new DatagramSocket();
        String msg = "//ACT//"+this.chat.size();
        byte[] mensaje = msg.getBytes();
        DatagramPacket peticion = new DatagramPacket(mensaje, mensaje.length, this.hostServidor, this.puertoServidor);
        socketUDP.send(peticion);
        byte[] buffer = new byte[1003];
        DatagramPacket respuesta = new DatagramPacket(buffer, buffer.length);
        socketUDP.receive(respuesta);
        String resp_chat = new String(respuesta.getData());
        String[] messages = resp_chat.split("//");
        
        this.chat.addAll(Arrays.asList(messages));
        this.chat.remove(this.chat.size()-1);
         socketUDP.close();
    }
    
    public void enviar(String nombre, String mensaje) throws SocketException, IOException{
        DatagramSocket socketUDP = new DatagramSocket();
        String msg = "//ENV"+nombre + ":" + mensaje;
        byte[] mensaje_data = msg.getBytes();
        DatagramPacket peticion = new DatagramPacket(mensaje_data, mensaje_data.length, this.hostServidor, this.puertoServidor);
        socketUDP.send(peticion);
    }
}
