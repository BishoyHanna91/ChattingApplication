import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;

/**
 * Created by bisho on 5/6/2017.
 * Name: Bishoy Hanna
 */

public class ServerThread extends Thread {

    // the fields of the ServerThread class
    static Socket socket;
    static String user_name;
    Room R1;

    //creating a copy constructor for ServerThread constructor in order to
    // copy its data
    ServerThread(Socket socket, String user_name)
    {
        this.socket = socket;
        this.user_name = user_name;

    }

    // function for creating a new room
    public Room create_room(String user_name,Socket get_socket,String room_name)
    {


        // creating a constructor and passing these parameters
        // then add this to the room list
         R1 = new Room(user_name, get_socket, room_name);

         Server.TheRoom.add(R1);

         // copy the room data
         R1.copy_room_socket(get_socket);
         R1.copy_roomData(room_name);
         System.out.println("you created a new Room ");

        return R1;

     }


     // function for checking if the room is already created or the user needs to create
     // new room
     void  check_room(String user_name,Socket get_socket,String room_name)
    {

        // check if there is a room already
        if(Server.TheRoom.isEmpty())
        {

              R1 = create_room(user_name, get_socket,room_name);
        }

        else
            {

             // if the room is already created
            for (int i = 0; i < Server.TheRoom.size(); i++)

            {


                if (Server.TheRoom.get(i).room_matched(room_name) == 1) {

                    // copy the room data

                    Server.TheRoom.get(i).copy_matched_name(user_name);
                    Server.TheRoom.get(i).copy_room_socket(get_socket);

                    System.out.println("This room is already created ");
                    System.out.println(" you joined this room");

                }
                // create a new room
                else
                {


                  R1 = create_room(user_name, get_socket, room_name);

                }

            }
        }

    }

    // function for displaying the names of the users in the room
    public void display_room_names(String Room_name)
    {
        // check the name of the room
        for (int i = 0; i < Server.TheRoom.size(); i++)

        {


            // found then display the user's names
            if (Server.TheRoom.get(i).room_matched(Room_name) == 1)
            {

                Server.TheRoom.get(i).display_userNames();

            }
        }
    }

    // function for making the user leave the room
    public void leave_room(String Room_name, String NameUser)
    {
        // checking the room name
        for(int i = 0;i<Server.TheRoom.size(); i++)
        {
            // found this room, then go to that room and delete the user
            if (Server.TheRoom.get(i).room_matched(Room_name) == 1)
            {
                Server.TheRoom.get(i).delete_user(NameUser);
            }
        }
    }

    // function for displaying the room names
    public void display_roomNames( String NameUser)
    {

        for(int i = 0;i<Server.TheRoom.size();i++)
        {

            Server.TheRoom.get(i).search_name(NameUser);

        }

    }

    // function for displaying the room names
 static public void displayRooms()
 {

     System.out.println(Server.TheRoom);
 }

 // function for getting the socket of the server, the user socket, the room name, and the message which
 // will be sent
 public void sending_room_message ( Socket server_socket, Socket socket_user, String Room_Name, String send_Message)
 {
     PrintWriter printWriter = null;

     try {
         // loop over the users sockets
         for(int i = 0;i<Server.socket_numbers;i++)
         {
             // copy each user socket of an array of sockets.
             // then send message to all the users.
             if (server_socket!=null && socket_user !=null)
             {
                 Server.users_Sockets[i] = socket_user;
                 printWriter = new PrintWriter(Server.users_Sockets[i].getOutputStream(), true);


             }

         }

         // printing this message
         for (int i = 0; i < Server.TheRoom.size(); i++) {
             if (Server.TheRoom.get(i).room_matched(Room_Name) == 1)
             {
                   printWriter.print(" The USER IS TYPING:  ");
                   printWriter.println(send_Message);

             }
         }
     }
     catch (IOException e)
     {
         e.printStackTrace();
     }
 }




    // Here is a method in order to make the operating system
    // run this thread
    public void run() {



        try {


            String get_message = null;
            String part2 = null;
            String part3 = null;


            System.out.println(user_name + " has entered the system");

            // creating Printwriter in order to send the message to the user
           PrintWriter printWriter =  new PrintWriter(socket.getOutputStream(), true);


            // get the message from the user
            Scanner scan_message = new Scanner(socket.getInputStream());


            printWriter.println(" you have entered the system");


              while (scan_message.hasNext())
            {
                // getting message from user
                 get_message = scan_message.nextLine();

                 // split the message into parts
                 int i = get_message.indexOf(" ");



                part2 = get_message.substring(i+1);

                // this is the command message
                part3 = get_message.substring(0,i);

                // check if the user enters one of these commands, then a function will be
                // called in order to execute this command

                if(part3.compareTo("create")==0)
                 {
                     check_room(user_name,socket,part2);
                 }
                 if(part3.compareTo("display") == 0)
                 {
                     display_room_names(part2);
                 }
                 if(part3.compareTo("leave") == 0)
                 {
                   leave_room(part2, user_name);
                 }
                 if(part3.compareTo("displayR") == 0)
                 {
                     display_roomNames(user_name);

                 }

                 // if the user disconnect then the server socket will
                 // be closed
                 if(part3.compareTo("disconnect") == 0)
                 {

                     printWriter.println ("you disconnect from the server");
                     ServerThread.socket.close();
                     break;
                 }





                System.out.print("getting user message: ");
                 System.out.println(get_message);


                 // printing the message for the user which is received from the server
                printWriter.print( user_name + " :is typing: ");
                printWriter.println(get_message);

              //  sending_room_message(ServerThread.socket, socket,part2,get_message);


            }


            // closing the socket
            socket.close();

              // printing a message to handle the client crushes if the user stops
             // its process
             System.out.println(user_name + " :This clients left the server");

             printWriter.println(" The server is closed");



        } catch (IOException e)
        {
            e.printStackTrace();
        }

    }



}
