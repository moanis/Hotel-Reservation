package com.hotels;



import java.util.InputMismatchException;
import java.util.Scanner;

public class MainClient {
    private static boolean MainMenu = true;
    private static boolean SubMenu = true;
    private static Scanner input;


    public static void main(String[] args) {
        input = new Scanner(System.in);

        while (MainMenu) {
            while (SubMenu) {
                System.out.println("¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨");
                System.out.println("Hello and Welcome to our HackerU Hotels Reservation Program");
                System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
                System.out.println("Please select one of the following options:");
                System.out.println("1. Create an account");
                System.out.println("2. Log in");
                System.out.println("3. Log out");
                System.out.println("4. Show hotels list.");
                System.out.println("5. Book A New Room.");
                System.out.println("6. Check in at the desk");
                System.out.println("7. Add hotel facilities");
                System.out.println("8. Display your bookings");
                System.out.println("9. Check out");
                System.out.println("10. Payment");
                System.out.println("11. Load files");
                System.out.println("0. Exit");


                int selection = input.nextInt();
                if (selection == 0){
                    System.out.println("Thank you for visiting us and we look forward to seeing u again soon....BYE");
                    Server.exit();
                    return;
                }

                switch (selection) {
                    case 1:
                        guestRegister();

                        break;
                    case 2:
                        logIn();
                        break;
                    case 3:
                        logout();
                        break;
                    case 4:
                        Server.hotelList();
                        break;
                    case 5:
                        bookRoom();
                        break;
                    case 6:
                        Server.checkIn();
                        break;
                    case 7:
                        useHotelFacilities();
                        break;
                    case 8:
                        Server.displayBookings();
                        break;
                    case 9:
                        checkOut();
                        break;
                    case 10:
                        payment();
                        break;

                    case 11:
                        Server.loadFiles();
                        break;


                    default:
                        System.out.println("Invalid Selection");
                        break;
                }
                System.out.println("¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨");
                System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
                System.out.println("Would you like to Select another Option\n1 ) Yes\n2 ) No");
                System.out.println("¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨");
                System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
                SubMenu = input.nextInt() == 1;
            }
            SubMenu = true;
            System.out.println("¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨");
            System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
            System.out.println("Would You Like To Continue With The Program\n1 ) Yes\n2 ) No");
            System.out.println("¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨");
            System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
            if (input.nextInt() == 1) {
                MainMenu = true;
            } else {

                System.exit(0);

            }


        }


    }






    private static void guestRegister() {
        String name, address, password;
        input = new Scanner(System.in);


        do {
            System.out.println("Enter your name");
            name = input.nextLine();
            System.out.println("Enter your address");
            address = input.nextLine();
            System.out.println("Enter your password");
            password = input.nextLine();

        } while (name.equals("") || address.equals("") || password.equals(""));


        Server.signUp(name, address, password);
    }

    private static void logIn() {
        int id;
        String password;
        input = new Scanner(System.in);

        System.out.println("Enter your Id number and your password");
        do {

            System.out.println("Enter your ID");

            id = input.nextInt();
            input.nextLine();
            System.out.println("Enter your password");
            password = input.nextLine();

        } while((id<0) && password.equals(""));

        Server.logIn(id, password);

    }


    private static void logout() {
        input = new Scanner(System.in);
        String answer;

        System.out.println("Are you sure you want to log out? y/n");

        answer = input.nextLine();

        while(true) {
            if (answer.equalsIgnoreCase("y")) {
                Server.logOut();
                break;
            }else if ((answer.equals("n")))
                break;
            else {


                System.out.println("please enter y/n...");
                answer = input.nextLine();
            }
        }

    }


    private static void bookRoom(){
        input = new Scanner(System.in);

        String bookedDate="";
        String arrivalDate="";
        int hotelId=0;
        int roomNum=0;
        int numOfNigh=0;

        System.out.println("Welcome to our reservation system, we are going to guide you through the steps to book a room ");
        System.out.println("To book a room you need your Id Nr, hotel's Id, room number, date of booking , date of arrival and number of nights");


        try {

            System.out.println("Enter the id of your chosen hotel, this info is found in option 4");
            hotelId = input.nextInt();
            input.nextLine();
            System.out.println("Enter the number of your chosen room, the rooms numbers are shown in option 4");
            roomNum = input.nextInt();
            input.nextLine();
            System.out.println("Enter the booking date(dd/MM/yyyy)... the booking date must be no later than today");
            bookedDate = input.nextLine();
            System.out.println("Enter the arrival date(dd/MM/yyyy)... arrival date could be from today and later on");
            arrivalDate = input.nextLine();
            System.out.println("Enter number of nights");
            numOfNigh = input.nextInt();
        } catch (InputMismatchException e) {
            e.printStackTrace();
        }


        Server.bookRoom(hotelId, roomNum,numOfNigh,bookedDate,arrivalDate);
    }


    private static void useHotelFacilities(){

        System.out.println("We provide a range of facilities in our chain of hotels around the world, please select your facility!!");
        input = new Scanner(System.in);
        int answer;

        do {
            System.out.println("1. Buy a meal");
            System.out.println("2. Buy a drink");
            System.out.println("3. gym/spa");
            System.out.println("4. return to main list");
            answer = input.nextInt();

            switch(answer){
                case 1:
                    Server.buyMeal();
                    break;
                case 2:
                    Server.buyDrink();
                    break;
                case 3:
                    Server.useGym();
                    break;
                case 4:
                    break;

                default:
                    System.out.println("Invalid selection");
                    break;


            }
        } while (answer != 4);

    }


    private static void checkOut(){
        input = new Scanner(System.in);

        System.out.println("please enter the date you want to check out, this is the day you leave the room");
        String checkoutDate = input.nextLine();



        Server.checkOut(checkoutDate);

    }

    private static void payment(){
        input = new Scanner(System.in);
        System.out.println("please enter the amount you want to pay!");
        double amount = input.nextDouble();
        Server.payment(amount);
    }



}










