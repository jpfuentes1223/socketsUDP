package codigo_sockets_udp_android_server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;

public class Server_udp {

    public static void main(String[] args) {
        try {
            DatagramSocket socketUDP = new DatagramSocket(5555);
            ArrayList<String> memoria = new ArrayList<>();

            while (true) {
                DatagramPacket peticion = receiveSocket(socketUDP);
                String peticion_data = new String(peticion.getData()).trim();

                if (peticion_data.contains("//ACT")) {
                    actualizar_server(memoria,peticion_data, peticion,socketUDP);
                }else if(peticion_data.contains("//ENV")){
                    enviar_server(memoria, peticion_data, peticion, socketUDP);
                }
            }
        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        }
    }

    private static DatagramPacket receiveSocket(DatagramSocket socket) throws IOException {
        byte[] buffer = new byte[1000];
        DatagramPacket peticion = new DatagramPacket(buffer, buffer.length);
        socket.receive(peticion);

        return peticion;
    }
    
    private static void actualizar_server(ArrayList chat, String peticionData, DatagramPacket packet, DatagramSocket socket) throws IOException{
        String[]data = peticionData.split("//");
        System.out.println("yeyeye"+peticionData);
        System.out.println(data[2]);
        String final_resp = "";
        for (int i = Integer.parseInt(data[2]); i < chat.size(); i++) {
            final_resp += chat.get(i) + "//";
        }
        System.out.println("woo"+final_resp);
        DatagramPacket respuesta =  new DatagramPacket(final_resp.getBytes(),final_resp.length(),packet.getAddress(),packet.getPort());
        socket.send(respuesta);
        
    }
    private static void enviar_server(ArrayList chat, String peticionData, DatagramPacket packet, DatagramSocket socket ){
        String msg_edited = peticionData.substring(peticionData.indexOf("//ENV")+5);
        chat.add(msg_edited);
        System.out.println("enviado = "+msg_edited);
    }

}
