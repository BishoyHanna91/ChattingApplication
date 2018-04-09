import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by bisho on 5/22/2017.
 * Name: Bishoy Hanna
 */
// this class for the thread for each user
//so that every user can send and receive message

public class UserThread extends Thread {
    // the userThread data

    static Socket socket;
    static String user_name;

    // creating a copy constructor for copying the
    // data of the user Thread as the user name, and his socket
    UserThread(Socket socket, String user_name)

    {
        this.socket = socket;
        this.user_name = user_name;

    }

    // function for finding the name of the room, and its user in order for sending
    // the message for other users
    static public void get_matched_room(String Room_name, String User_name, String User_message)
    {
        // loop to find the room which the user wants to send this message
        for(int i=0; i <Server.TheRoom.size(); i++ )
        {
            if(Server.TheRoom.get(i).room_matched(Room_name) == 1)
            {
               System.out.println(User_name+ ":sending this message"+User_message);

            }
        }

    }



    public void run()
    {
        try {


            String get_message = null;

            // create scanner in order to get the message from the user
            Scanner scan_message = new Scanner(socket.getInputStream());

            System.out.println(user_name + " typing message: ");

            // this method for sending the message to the other users
            PrintWriter printWriter =  new PrintWriter(socket.getOutputStream(), true);



            // getting messages from the user
            while(scan_message.hasNext())
            {
                // getting the message and print it
                get_message = scan_message.nextLine();


                System.out.println(get_message);

            }


            // when the server crushed, it sends a message to every users
            System.out.println(" Server is crushed ");
            socket.close();
           }


        catch (IOException e)
        {
            e.printStackTrace();
        }


    }



}
