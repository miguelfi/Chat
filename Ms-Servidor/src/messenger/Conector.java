// conector servidor
package messenger;
import java.net.*;
import java.io.*;
import messenger.VServidor;

public class Conector extends Thread {
    private Socket s;
    private ServerSocket ss;
    private InputStreamReader entradaSocket;
    private DataOutputStream salida;
    private BufferedReader entrada;
    final int puerto = 8180;
    
    public Conector(String nombre)
    {
        super(nombre);
    }
    public void enviarMSG(String msg)
    {
        try{
            this.salida.writeUTF(msg+"\n");
        }catch (IOException e){};
    }
    
    public void run()
    {
     String text="text";
     try{
         this.ss = new ServerSocket(puerto);// crear servidor
         this.s = ss.accept();// genera conexion cliente-servidor
         
         // creacion de objetos de entrada de datos para lectura de mensajes
         this.entradaSocket = new InputStreamReader(s.getInputStream());
         this.entrada = new BufferedReader(entradaSocket); // entrada para leer datos
         
         //creacion de salida de datos para el envio de mensajes
         this.salida = new DataOutputStream(s.getOutputStream());
         while(true) //
         {
             text = this.entrada.readLine(); // se guarda lo q recibo
             System.out.println(text);
             VServidor.jTextArea1.setText(VServidor.jTextArea1.getText()+"\n"+text);
             
         }
         }catch (IOException e){
         System.out.println("Error al enviar el mensaje");
         };
    }
    public String leerMSG()
    {
        try{
            return this.entrada.readLine(); // leer linea de texto del cliente
        }catch(IOException e){};
        return null;
    }
    public void desconectar()
    {
        try{
            s.close();
        }catch(IOException e){};
        try{
            ss.close();
        }catch(IOException e){};
    }
}
