/**
 * Created by bisho on 5/6/2017.
 * Name: Bishoy Hanna
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

// the fields of the server class
public class Server {
    public static final int Port = 9094;
    public static final int socket_numbers = 2;
    static ArrayList<Room> TheRoom = new ArrayList<Room>();
    static Socket[] users_Sockets = new Socket[socket_numbers];


    public static void main(String []args) throws IOException {


        Server S = new Server();
        S.RunServer();
    }

    Server()
    {
        System.out.println("Server is created");

    }






    public void RunServer() throws IOException {
        // creating a socket which is a channel trough which connection
        // take place between server and user

        ServerSocket serverSocket = new ServerSocket(Port);
        System.out.println("Server up and ready for connection");

        String server_string = null;

       Scanner scan = new Scanner(System.in);


        // accepting different connection from different clients by using room
        while (true)
        {
            String name = null;
            // it gives the permission for the clients to accept
            // to connect to the server
            Socket socket = serverSocket.accept();

            // creating an object of ServerThread constructor and passing in socket
            // as parameter, then call the start method in order to start this thread

            Scanner scan_message = new Scanner(socket.getInputStream());
            name = scan_message.nextLine();


            ServerThread ST = new ServerThread(socket,name);
            ST.start();

            // some commands for the server, so he can enter the users, and disconnect

            server_string = scan.nextLine();
            if(server_string.compareTo("enter")==0)
            {
                System.out.println("user successfully enters the system");
            }

            // function for disconnecting the server from the users
            if(server_string.compareTo("disconnect")==0)
            {

                ServerThread.socket.close();

                socket.close();


                System.out.println("server disconnected");
                break;
            }

        }



    }

}
