package com.hotels;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;



public class ClientThread extends Thread {

    private static final int HOTELLIST = 100;
    private static final int BOOKROOM = 101;
    private static final int BUYMEAL = 102;
    private static final int BUYDRINK = 108;
    private static final int USEGYM = 109;
    private static final int DISPLAYBOOKINGS = 110;
    private static final int CHECKIN = 111;
    private static final int CHECKOUT= 112;

    private static final int LOGIN = 105;
    private static final int SIGNUP = 106;
    private static final int LOGOUT = 107;

    private static final int OKAY = 200;
    private static final int FAILURE = 201;


    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;
    private BookingSystem bookingSystem;
    private static boolean isLoggedIN = false;
    private static String name;
    private static int id;
    private static int bookingId;


    public ClientThread(Socket socket, BookingSystem bookingSystem) {

        this.socket = socket;
        this.bookingSystem = bookingSystem;

    }

    @Override
    public void run() {
        try {

            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
            int action = inputStream.read();
            switch (action){
                case HOTELLIST:
                    hotelList();

                    break;
                case BOOKROOM:
                    bookRoom();
                    break;
                case BUYMEAL:
                    buyMeal();
                    break;
                case BUYDRINK:
                    buyDrink();
                    break;
                case USEGYM:
                    useGym();
                    break;
                case SIGNUP:
                    signUp();
                    break;
                case LOGIN:
                    logIn();
                    break;
                case LOGOUT:
                    logout();
                    break;
                case DISPLAYBOOKINGS:
                    displayBookings();
                    break;
                case CHECKIN:
                    checkIn();
                    break;
                case CHECKOUT:
                    checkOut();
                    break;

            }
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

    private void hotelList() throws IOException{
        if (isLoggedIN) {
            outputStream.write(OKAY);
            String hotelList = bookingSystem.displayHotels();
            byte[] hotelListBytess = hotelList.getBytes();

            outputStream.write(hotelListBytess.length);
            outputStream.write(hotelListBytess);
        }else
            outputStream.write(FAILURE);
        System.out.println("you have to login to view the hotels");
    }


    private void bookRoom() throws IOException{

        int hotelId;
        int roomNum;
        int numOfNights;
        String bookedDate;
        String arrivalDate;

        byte[] intBytes = new byte[4];
        int actuallyRead;

        actuallyRead = inputStream.read(intBytes);
        if(actuallyRead != 4)
            return;
        hotelId = ByteBuffer.wrap(intBytes).getInt();
        actuallyRead = inputStream.read(intBytes);
        if(actuallyRead != 4)
            return;
        roomNum = ByteBuffer.wrap(intBytes).getInt();
        actuallyRead = inputStream.read(intBytes);
        if(actuallyRead != 4)
            return;
        numOfNights = ByteBuffer.wrap(intBytes).getInt();


        int stringLength = inputStream.read();
        if(stringLength == -1)
            throw new IOException("end of stream");
        byte[] stringBytes = new byte[stringLength];
        actuallyRead = inputStream.read(stringBytes);
        if(actuallyRead != stringLength)
            return;
        bookedDate = new String(stringBytes);

        stringLength = inputStream.read();
        if(stringLength == -1)
            throw new IOException("end of stream");
        stringBytes = new byte[stringLength];
        actuallyRead = inputStream.read(stringBytes);
        if(actuallyRead != stringLength)
            return;
        arrivalDate = new String(stringBytes);
        System.out.println(arrivalDate);
        bookingId = bookingSystem.book(id, hotelId, roomNum, bookedDate,arrivalDate, numOfNights);
        outputStream.write(OKAY);

        intBytes = new byte[4];
        ByteBuffer.wrap(intBytes).putInt(bookingId);
        outputStream.write(intBytes);

    }


    private void checkIn() throws IOException{
        if(!bookingSystem.getBooking(bookingId).isArrived()) {
            bookingSystem.arrive(bookingId);
            outputStream.write(OKAY);

            String checkinMsg = "Welcoming you to our hotel, your checkin has been processed and your room is ready.\n" +
                    " Also all hotel facilities are available now";
            byte[] stringBytes = checkinMsg.getBytes();

            outputStream.write(stringBytes.length);
            outputStream.write(stringBytes);
        }else {
            outputStream.write(FAILURE);
            System.out.println("Guest is already arrived");
        }


    }


    private void checkOut() throws IOException{

        if(!bookingSystem.getBooking(bookingId).isPaid() && bookingSystem.getBooking(bookingId).isArrived()
        && bookingSystem.getBooking(bookingId).isCheckedOut()) {
            int stringLength = inputStream.read();
            if (stringLength == -1)
                throw new IOException("end of stream");
            byte[] stringBytes = new byte[stringLength];
            int actuallyRead = inputStream.read(stringBytes);
            if (actuallyRead != stringLength)
                return;
            String checkoutDate = new String(stringBytes);

            if (!DateUtils.isDateValid(checkoutDate)) {

                outputStream.write(FAILURE);
                System.out.println("check out date is invalid");
            } else {


                bookingSystem.checkout(bookingId, checkoutDate);
                outputStream.write(OKAY);

                byte[] costBytes = new byte[8];
                ByteBuffer.wrap(costBytes).putDouble(bookingSystem.getBooking(bookingId).totalCost());
                outputStream.write(costBytes);
            }


        }

    }



    private void buyMeal() throws IOException{
        if(bookingSystem.getBooking(bookingId).isArrived()) {


            bookingSystem.buyMeal(bookingId);

            outputStream.write(OKAY);

            String buyingMeal = "The Meal bill is added to your account\n" + bookingSystem.getBooking(bookingId).getCosts().toString();
            byte[] stringBytes = buyingMeal.getBytes();

            outputStream.write(stringBytes.length);
            outputStream.write(stringBytes);
            System.out.println("meal bill is added");
        }else
            outputStream.write(FAILURE);
        System.out.println("guest is not at the hotel");

    }


    private void buyDrink() throws IOException{
        if(bookingSystem.getBooking(bookingId).isArrived()) {

        bookingSystem.buyDrink(bookingId);

        outputStream.write(OKAY);

        String buyingDrink = "The Drink bill is added to your account\n" + bookingSystem.getBooking(bookingId).getCosts().toString();
        byte[] stringBytes = buyingDrink.getBytes();

        outputStream.write(stringBytes.length);
        outputStream.write(stringBytes);
        System.out.println("drink bill is added");
        }else
            outputStream.write(FAILURE);
        System.out.println("guest is not at the hotel");

    }


    private void useGym() throws IOException{
        if(bookingSystem.getBooking(bookingId).isArrived()) {

        bookingSystem.useGym(bookingId);

        outputStream.write(OKAY);

        String usingGym = "The Gym bill is added to your account\n" + bookingSystem.getBooking(bookingId).getCosts().toString();


        System.out.println("testing gym booking "+usingGym);
        byte[] stringBytes = usingGym.getBytes();
        outputStream.write(stringBytes.length);
        outputStream.write(stringBytes);
        System.out.println("Gym bill is added");
        }else
            outputStream.write(FAILURE);
        System.out.println("guest is not at the hotel");



    }


    private void displayBookings() throws IOException{

        if(bookingSystem.getBooking(bookingId)!=null){
            String bookings = bookingSystem.getBooking(bookingId).toString();
            outputStream.write(OKAY);

            byte[] stringBytes = bookings.getBytes();

            outputStream.write(stringBytes.length);
            outputStream.write(stringBytes);
            System.out.println("display bookings for booking id " + bookingId);
        }else
            System.out.println("Error handling bookings display");



    }



    private void logIn()throws IOException{
        int uid;
        String password;
        byte[] buffer = new byte[4];
        int actuallyRead;
        actuallyRead = inputStream.read(buffer);
        if(actuallyRead != 4)
            throw new IOException("invalid first parameter");
        uid = ByteBuffer.wrap(buffer).getInt();


        int passwordLength = inputStream.read();
        if(passwordLength == -1)
            throw new IOException("end of stream");
        byte[] userNameBytes = new byte[passwordLength];
        actuallyRead = inputStream.read(userNameBytes);
        if(actuallyRead != passwordLength)
            return;
        password = new String(userNameBytes);
        System.out.println("id = "+uid+" password "+password);
        if (bookingSystem.validateLogin(uid, password) && !isLoggedIN){
            outputStream.write(OKAY);
            name=bookingSystem.getCustomer(uid).getName();
            System.out.println(name);
            bookingSystem.getCustomer(uid).setSignedUp(true);
            System.out.println("you have logged in");
            isLoggedIN = true;

        }
            else {
                outputStream.write(FAILURE);
            System.out.println("unsuccessful, you are already logged in");

        }

    }

    private void logout() throws IOException{

        if (isLoggedIN) {
            isLoggedIN = false;
            outputStream.write(OKAY);
            System.out.println(name);

            byte[] nameBytes = name.getBytes();
            outputStream.write(nameBytes.length);
            outputStream.write(nameBytes);
        }else
            outputStream.write(FAILURE);
    }

    private void signUp() throws IOException {

        String name, address, password;
        int userNameLength = inputStream.read();

        if(userNameLength == -1)
            throw new IOException("end of stream");
        byte[] userNameBytes = new byte[userNameLength];
        int actuallyRead = inputStream.read(userNameBytes);
        if(actuallyRead != userNameLength)
            return;
        name = new String(userNameBytes);


        int addressLength = inputStream.read();
        if(addressLength == -1)
            return;
        byte[] addressBytes = new byte[addressLength];
        actuallyRead = inputStream.read(addressBytes);
        if(actuallyRead != addressLength)
            return;
        address = new String(addressBytes);


        int passwordLength = inputStream.read();

        if(passwordLength == -1)
            return;
        byte[] passwordBytes = new byte[passwordLength];
        actuallyRead = inputStream.read(passwordBytes);

        if(actuallyRead != passwordLength)
            return;
        password = new String(passwordBytes);



        if(!bookingSystem.doesGuestExists(name, address) && !isLoggedIN) {
            outputStream.write(OKAY);
            id = bookingSystem.registerCustomer(name, address, password);
            System.out.println("A new guest has been successfully registered and his account details as follow:");
            System.out.println(bookingSystem.getCustomer(id));

            byte[] buffer = new byte[4];
            ByteBuffer.wrap(buffer).putInt(id);
            outputStream.write(buffer);
        } else
            outputStream.write(FAILURE);
    }








}