import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.Scanner;

/**
 * Created by bisho on 5/6/2017.
 * Name: Bishoy Hanna
 */
public class User {

    // the user fields
    public static final int Port = 9094;

       static private String user_name;
      static private Socket socket;


    public static void main(String []args) throws IOException
    {
         // creating an object for the user and call its run_user
         // function in order to run user interface

            User U = new User();
           U.run_user();






    }


    // user constructor for initializing  the fields of the user
    public User()
    {
        user_name = null;

        socket = null;
    }

    // function for getting the name of the user
static public String get_User_name()
{
    return user_name;
}

// function for setting the socket of the user, then get it
 static public Socket get_socket()throws IOException
    {
        socket = new Socket("localhost", Port);
        return socket;
    }

    // function for copying the user name
    static public void copy_userName(String copy_name)
    {
        user_name = copy_name;
    }

// function for displaying the data of the user which his name
public void display_data()
{
    System.out.print("user_name: ");

    System.out.println(user_name);



}

//function for searching for the name of the user
// return 1 when the name is found
// else return 0
static public int search_userName(String searched_name)
{
    user_name = ServerThread.user_name;

    if(user_name.compareTo(searched_name) == 0)
    {
        System.out.println("name is found");
        return 1;
    }
    else
    {
        System.out.println("name is not valid");
        return 0;
    }

}


// function is responsible for creating a socket for
  //  the user, and sending messages through this socket

public void run_user()throws IOException
{


    Scanner scan = new Scanner(System.in);
    String room_name  = null;
    String message = null;

    // message is shown for the user to enter his name
    System.out.print("please enter the user name: ");

    // getting the user's name
    user_name = scan.nextLine();


    // creating a socket which is a channel trough which connection
    // take place between server and user
    socket = new Socket("localhost", Port);


    // creating buffer reader to read the message from the client and sending it to the server
    BufferedReader ReadFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));


    // creating print writer in order to print the name of the user who wrote the message
    PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);

    printWriter.println(user_name);

    // create the scanner to get the message from the user
    Scanner scan_message = new Scanner(socket.getInputStream());

    message = scan_message.nextLine();
    System.out.println(message);



    System.out.print("enter the message: ");



    // this method for getting the message back from the server
    BufferedReader bufferedReader = new java.io.BufferedReader(new InputStreamReader(System.in));

    // creating the userThread and call its function
    UserThread UT = new UserThread(socket,user_name);
    UT.start();


    // this loop for keep getting messages from the users
                while (true)
                {
                    String user_message = bufferedReader.readLine();

                    printWriter.println(user_message);



                }

   }


}
