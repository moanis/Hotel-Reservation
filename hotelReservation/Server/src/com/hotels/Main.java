package com.hotels;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Main {

    public static void main(String[] args) throws IOException {


        startServerSocket();
    }


    public static void startServerSocket() throws IOException {

        BookingSystem bookingSystem = new BookingSystem();

        int hotel1 = bookingSystem.addHotel("Hilton", "London");
        int hotel2 = bookingSystem.addHotel("Mariot", "London");
        int hotel3 = bookingSystem.addHotel("Dan", "Eilat");

        int room1 = bookingSystem.addRoom(hotel1, RoomType.Type.Family, 1, 300);
        int room2 = bookingSystem.addRoom(hotel1, RoomType.Type.Double, 100, 250);
        int room3 = bookingSystem.addRoom(hotel1, RoomType.Type.Family, 10, 300);
        int room4 = bookingSystem.addRoom(hotel1, RoomType.Type.Single, 11, 300);
        int room5 = bookingSystem.addRoom(hotel1, RoomType.Type.Family, 20, 300);
        int room6 = bookingSystem.addRoom(hotel2, RoomType.Type.Family, 1, 300);
        int room7 = bookingSystem.addRoom(hotel2, RoomType.Type.Double, 100, 250);
        int room8 = bookingSystem.addRoom(hotel2, RoomType.Type.Suite, 10, 300);
        int room9 = bookingSystem.addRoom(hotel2, RoomType.Type.Family, 11, 300);
        int room10 = bookingSystem.addRoom(hotel3, RoomType.Type.Family, 20, 300);
        int room11 = bookingSystem.addRoom(hotel3, RoomType.Type.Family, 1, 300);
        int room12 = bookingSystem.addRoom(hotel3, RoomType.Type.Double, 100, 250);
        int room13 = bookingSystem.addRoom(hotel3, RoomType.Type.Family, 10, 300);
        int room14 = bookingSystem.addRoom(hotel3, RoomType.Type.Family, 11, 300);
        int room15 = bookingSystem.addRoom(hotel3, RoomType.Type.Family, 20, 300);


        int c1 = bookingSystem.registerGuest("monis", "london", "koko");
        int b1 = bookingSystem.book(c1, hotel1, 10, "15/12/2018","18/12/2018", 3);


        bookingSystem.arrive(b1);
        bookingSystem.buyMeal(b1);
        bookingSystem.useGym(b1);
        bookingSystem.checkout(b1, "23/12/2018");
        bookingSystem.payment(b1, 1000);



        ServerSocket serverSocket = null;
        try{
            serverSocket = new ServerSocket(3000);



            while (true){
                Socket socket = serverSocket.accept();
                new ClientThread(socket, bookingSystem).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(serverSocket != null)
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }
}