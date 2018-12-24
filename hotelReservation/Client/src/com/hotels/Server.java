package com.hotels;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.zip.CheckedOutputStream;

/**

 */
public class Server {

    private static final int HOTELLIST = 100;
    private static final int BOOKROOM = 101;
    private static final int BUYMEAL = 102;
    private static final int BUYDRINK = 108;
    private static final int USEGYM = 109;
    private static final int DISPLAYBOOKINGS = 110;
    private static final int CHECKIN=111;
    private static final int PAYMENT=113;
    private static final int EXIT = 114;
    private static final int LOADFILES = 115;
    private static final int CHECKOUT=112;
    private static final int LOGIN = 105;
    private static final int SIGNUP = 106;
    private static final int LOGOUT = 107;

    private static final int OKAY = 200;
    public static final int FAILURE = 201;
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 3000;


    private static boolean isLoggedIn=false;
    private static boolean isSignedUp=false;
//    private static int bookingNr;







    public static void hotelList(){
        connectToServer(new ICommand() {
            @Override
            public void clientCommand(InputStream inputStream, OutputStream outputStream) throws IOException {

                outputStream.write(HOTELLIST);
                int answer = inputStream.read();
                if (answer == OKAY) {

                    StringBuilder stringBuilder = new StringBuilder();
                    byte[] buffer = new byte[1024];
                    int actuallyRead;
                    while ((actuallyRead = inputStream.read(buffer)) != -1) {
                        stringBuilder.append(new String(buffer, 0, actuallyRead));
                    }
                    String messageFromServer = stringBuilder.toString();
                    System.out.println("Hotels Details: " + messageFromServer);
                } else
                    System.out.println("To view hotels list, you have to be logged int");


            }
        });
    }
    public static void bookRoom(final int hotelId, final int roomNum, final int numOfNigh, String bookedDate, String arrivalDate ){
        connectToServer(new ICommand() {
            @Override
            public void clientCommand(InputStream inputStream, OutputStream outputStream) throws IOException {
//                int bookingNr;
                outputStream.write(BOOKROOM);
                byte[] intBytes = new byte[4];
//                ByteBuffer.wrap(intBytes).putInt(id);
//                outputStream.write(intBytes);
                ByteBuffer.wrap(intBytes).putInt(hotelId);
                outputStream.write(intBytes);
                ByteBuffer.wrap(intBytes).putInt(roomNum);
                outputStream.write(intBytes);
                ByteBuffer.wrap(intBytes).putInt(numOfNigh);
                outputStream.write(intBytes);

                byte[] stringBytes = bookedDate.getBytes();
                outputStream.write(stringBytes.length);
                outputStream.write(stringBytes);

                stringBytes = arrivalDate.getBytes();
                outputStream.write(stringBytes.length);
                outputStream.write(stringBytes);

                int answer = inputStream.read();
                if(answer ==OKAY){

                    intBytes = new byte[4];
                    int actuallyRead;
                    actuallyRead = inputStream.read(intBytes);
                    if(actuallyRead != 4)
                        return;
                    int bookingNr = ByteBuffer.wrap(intBytes).getInt();
                    System.out.println("We'd like to thank you for choosing out hotels network, you have successfully booked a room,\n "
                            +" the system generated a booking reference number ("+bookingNr+"). This reference is needed to process your booking");

                }else
                    System.out.println("One or some data entered are invalid, please try again!!");






            }
        });
    }

    public static void buyMeal(){
        connectToServer(new ICommand() {
            @Override
            public void clientCommand(InputStream inputStream, OutputStream outputStream) throws IOException {

                outputStream.write(BUYMEAL);

//                byte[] intBytes = new byte[4];
//                ByteBuffer.wrap(intBytes).putInt(bookingNr);
//                outputStream.write(intBytes);

                int answer = inputStream.read();
                if (answer==OKAY){
                    int stringLength = inputStream.read();
                    if(stringLength == -1)
                        throw new IOException("end of stream");
                    byte[] stringBytes = new byte[stringLength];
                    int actuallyRead = inputStream.read(stringBytes);
                    if(actuallyRead != stringLength)
                        return;
                    String message = new String(stringBytes);
                    System.out.println(message);
                }else if(answer==FAILURE)
                    System.out.println("The guest hasn't checked in yet");
                else
                    System.out.println("error handling your Meal request");


            }
        });
    }



    public static void buyDrink(){
        connectToServer(new ICommand() {
            @Override
            public void clientCommand(InputStream inputStream, OutputStream outputStream) throws IOException {

                outputStream.write(BUYDRINK);
                int answer = inputStream.read();
                if (answer==OKAY){
                    int stringLength = inputStream.read();
                    if(stringLength == -1)
                        throw new IOException("end of stream");
                    byte[] stringBytes = new byte[stringLength];
                    int actuallyRead = inputStream.read(stringBytes);
                    if(actuallyRead != stringLength)
                        return;
                    String message = new String(stringBytes);
                    System.out.println(message);
                }else if(answer==FAILURE)
                    System.out.println("The guest hasn't checked in yet");
                else
                        System.out.println("error handling your Drink request");

            }
        });
    }


    public static void useGym(){
        connectToServer(new ICommand() {
            @Override
            public void clientCommand(InputStream inputStream, OutputStream outputStream) throws IOException {

                outputStream.write(USEGYM);
                int answer = inputStream.read();
                if (answer==OKAY){
                    StringBuilder stringBuilder = new StringBuilder();
                    byte[] buffer = new byte[1024];
                    int actuallyRead;
                    while ((actuallyRead = inputStream.read(buffer)) != -1) {
                        stringBuilder.append(new String(buffer, 0, actuallyRead));
                    }
                    String messageFromServer = stringBuilder.toString();
                    System.out.println(messageFromServer);

                } else if(answer==FAILURE)
                    System.out.println("The guest hasn't checked in yet");
                else
                        System.out.println("error handling your Gym request");
            }
        });
    }



    public static void displayBookings(){
        connectToServer(new ICommand() {
            @Override
            public void clientCommand(InputStream inputStream, OutputStream outputStream) throws IOException {

                outputStream.write(DISPLAYBOOKINGS);

                int answer = inputStream.read();
                if (answer==OKAY){
                    StringBuilder stringBuilder = new StringBuilder();
                    byte[] buffer = new byte[1024];
                    int actuallyRead;
                    while ((actuallyRead = inputStream.read(buffer)) != -1) {
                        stringBuilder.append(new String(buffer, 0, actuallyRead));
                    }
                    String messageFromServer = stringBuilder.toString();
                    System.out.println(messageFromServer);

                } else
                    System.out.println("Error handling your request");

            }
        });
    }


    public static void checkIn(){
        connectToServer(new ICommand() {
            @Override
            public void clientCommand(InputStream inputStream, OutputStream outputStream) throws IOException {

                outputStream.write(CHECKIN);
                int answer=inputStream.read();

                if (answer==OKAY){
                    int stringLength = inputStream.read();
                    if(stringLength == -1)
                        throw new IOException("end of stream");
                    byte[] stringBytes = new byte[stringLength];
                    int actuallyRead = inputStream.read(stringBytes);
                    if(actuallyRead != stringLength)
                        return;
                    String message = new String(stringBytes);
                    System.out.println(message);
                } else
                    System.out.println("you're already checked in");

            }
        });
    }



    public static void checkOut(String checkoutDate){
        connectToServer(new ICommand() {
            @Override
            public void clientCommand(InputStream inputStream, OutputStream outputStream) throws IOException {

                outputStream.write(CHECKOUT);

                int answer=inputStream.read();

                if (answer==OKAY){

                byte[] stringBytes = checkoutDate.getBytes();
                outputStream.write(stringBytes.length);
                outputStream.write(stringBytes);

                answer=inputStream.read();
                if (answer==OKAY){

                    byte[] intBytes = new byte[8];
                    int actuallyRead;
                    actuallyRead = inputStream.read(intBytes);
                    if(actuallyRead != 8)
                        return;
                    double totalCost = ByteBuffer.wrap(intBytes).getDouble();
                    System.out.println("Your bill is: "+totalCost);

                }else {
                    System.out.println("the date you entered is invalid");
                }




                } else {
                    System.out.println("Error handling your check out request");
                }



//                int answer=inputStream.read();
//                if (answer==OKAY){
//                    byte[] intBytes = new byte[8];
//                    int actuallyRead;
//                    actuallyRead = inputStream.read(intBytes);
//                    if(actuallyRead != 8)
//                        return;
//                    double totalCost = ByteBuffer.wrap(intBytes).getDouble();
//                    System.out.println("Your bill is: "+totalCost);



            }
        });
    }




    public static void payment(double amount){
        connectToServer(new ICommand() {
            @Override
            public void clientCommand(InputStream inputStream, OutputStream outputStream) throws IOException {

                outputStream.write(PAYMENT);
                byte[] paymentBytes = new byte[8];
                ByteBuffer.wrap(paymentBytes).putDouble(amount);
                outputStream.write(paymentBytes);

                int answer=inputStream.read();
                if (answer==OKAY){

                    int stringLength = inputStream.read();
                    if (stringLength == -1)
                        throw new IOException("end of stream");
                    byte[] stringBytes = new byte[stringLength];
                    int actuallyRead = inputStream.read(stringBytes);
                    if (actuallyRead != stringLength)
                        return;
                    String paymentCheckAnswer = new String(stringBytes);
                    System.out.println(paymentCheckAnswer);

                } else
                    System.out.println("Error handling your payment request");

            }
        });
    }


    public static void exit() {
        connectToServer(new ICommand() {
            @Override
            public void clientCommand(InputStream inputStream, OutputStream outputStream) throws IOException {
                outputStream.write(EXIT);
                int answer = inputStream.read();
                if (answer==OKAY) {
                    System.out.println("Saved to files");
                }else
                    System.out.println("Error saving to files");
            }
        });
    }

    public static void loadFiles() {
        connectToServer(new ICommand() {
            @Override
            public void clientCommand(InputStream inputStream, OutputStream outputStream) throws IOException {
                outputStream.write(LOADFILES);
                int answer = inputStream.read();
                if(answer==OKAY) {
                    System.out.println("Loading files was successful");
                }
                else
                    System.out.println("Loading files wasn't successful");
            }
        });
    }




    public static void logIn(int id, String password) {
        connectToServer(new ICommand() {
            @Override
            public void clientCommand(InputStream inputStream, OutputStream outputStream) throws IOException {
                if (!isLoggedIn) {

                    outputStream.write(LOGIN);
                    byte[] idBytes = new byte[4];
                    ByteBuffer.wrap(idBytes).putInt(id);
                    outputStream.write(idBytes);

                    byte[] passwordBytes = password.getBytes();
                    outputStream.write(passwordBytes.length);
                    outputStream.write(passwordBytes);
                    int answer = inputStream.read();
                    if (answer == OKAY) {
                        isLoggedIn=true;
                        System.out.println("you have successfully logged in");
                    } else System.out.println("logging unsuccessful");
                }else
                    System.out.println("you are already logged in, if you want to log in to another account please sign out");

            }


        });
    }


    public static void logOut() {
        connectToServer(new ICommand() {
            @Override
            public void clientCommand(InputStream inputStream, OutputStream outputStream) throws IOException {


                    outputStream.write(LOGOUT);
                int answer = inputStream.read();
                if (answer==OKAY) {
                        isLoggedIn=false;

                    int nameLength = inputStream.read();
                    if(nameLength == -1)
                        throw new IOException("end of stream");
                    byte[] userNameBytes = new byte[nameLength];
                    int actuallyRead = inputStream.read(userNameBytes);
                    if(actuallyRead != nameLength)
                        return;
                    String name = new String(userNameBytes);
                        System.out.println("you have logged out. Good bye " + name);
                }
                    else
                    System.out.println("you are not logged in");

            }


        });
    }




    public static void signUp(String name, String address, String password) {
        connectToServer(new ICommand() {
            @Override
            public void clientCommand(InputStream inputStream, OutputStream outputStream) throws IOException {
                outputStream.write(SIGNUP);
                byte[] userNameBytes = name.getBytes();
                byte[] addressBytes = address.getBytes();
                byte[] passwordBytes = password.getBytes();
                outputStream.write(userNameBytes.length);
                outputStream.write(userNameBytes);
                outputStream.write(addressBytes.length);
                outputStream.write(addressBytes);
                outputStream.write(passwordBytes.length);
                outputStream.write(passwordBytes);
                int answer = inputStream.read();
                if (answer==OKAY){

                    byte[] buffer = new byte[4];
                    int actuallyRead;
                    actuallyRead = inputStream.read(buffer);
                    if(actuallyRead != 4)
                        throw new IOException("invalid first parameter");
                    int id = ByteBuffer.wrap(buffer).getInt();
                    System.out.println("you have successfully registered, we have automatically generated a unique Id (" + id + ") that you should use to login along with your password");
                    isSignedUp=true;
                } else
                    System.out.println("this account is already exists, if you forget your details please contact us");
            }
        });
    }



    private static void connectToServer(ICommand whatToDo){
        if(whatToDo == null)
            return;
        Socket socket = null;
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try{
            socket = new Socket(HOST, PORT);

            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
            whatToDo.clientCommand(inputStream, outputStream);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }




    interface ICommand {
        void clientCommand(InputStream inputStream, OutputStream outputStream) throws IOException;
    }





}