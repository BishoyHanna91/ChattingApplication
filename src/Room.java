
/**
 * Created by bisho on 5/7/2017.
 * Name: Bishoy Hanna
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;


public class Room {

    // the Room data
    static Socket room_socket;
    static String room_name;
    ArrayList<String> Users_names = new ArrayList<String>();

    // room constructor for initializing the fields of the room class
    public  Room()
    {

        Users_names = null;
        room_socket = null;
        room_name = " ";

    }
    // copy constructor for copying the room data as the name of the user
    // his socket and his room name
    public Room(String user_name, Socket room_socket, String room_name)
    {

        copy_matched_name(user_name);

        this.room_socket = room_socket;
        this.room_name = room_name;
    }

    // function for getting the socket of the room
public Socket getRoom_socket()

{
    return room_socket;
}

// function for getting the name of the room
 public  String get_roomName()

 {
    return room_name;
 }

 // function for copying the socket of the room
public void copy_room_socket(Socket room_Socket)
{
    room_socket = room_Socket;
}

 // function for copying the data of the room
 public void copy_roomData(String get_roomName)

{

    room_name = get_roomName;
}

 // function for displaying the name of the room
 public  void display_roomName()
{

    System.out.println(room_name);
}

// function for copying the room names into an array so that the
// the user can display the room names which he joined
public void copy_Room_Names()
{
    String [] RoomNames = new String[Server.TheRoom.size()];
    System.out.print("Room Names: ");
    for(int i = 0;i<Server.TheRoom.size();i++)
    {
        RoomNames[i] = room_name;
        System.out.println(RoomNames[i]);
    }
}

 // function for display the name of the users
    public void display_userNames()
{

        System.out.print("User Name: ");
        System.out.println(Users_names);

}
 // function for adding the name of the user into a list
 public void copy_matched_name(String name_matched)
{


    Users_names.add(name_matched);
}

// function for checking if the room is there
// returning 1 if the room is found, and 0 else
    public int room_matched(String room_Name)
{

    if( room_name.compareTo(room_Name)==0)
    {


        return 1;
    }

    else
    {


        return 0;
    }


}
   //function for deleting the user from the list of the users names
    // this function is used when the user leave the room
     public void delete_user(String user_name)
    {
       if (User.search_userName(user_name)==1)
       {
             Users_names.remove(user_name);
             System.out.println(user_name + ": is deleted " );
       }

    }

    // function for checking for the users, then copy the room names
    public int search_name(String NameUser)
   {
       for(int i = 0;i<Users_names.size();i++)
       {

           if(User.search_userName(NameUser)== 1)
           {

                 copy_Room_Names();

               return 1;
           }
       }
          return 0;
   }



}
