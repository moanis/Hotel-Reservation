package com.hotels;

import java.io.*;

import java.util.ArrayList;


public class BytesStreamsAndFiles {


    public static boolean writingStringToFiles(String content, String fileName){
        boolean success = false;

        File file = new File(fileName);
        OutputStream outputStream = null;
        try{
            outputStream = new FileOutputStream(file);
            outputStream.write(content.getBytes());
            success = true;
        } catch (FileNotFoundException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }finally {
            if(outputStream != null)
                try {
                    outputStream.close();
                } catch (IOException e) {

                    e.printStackTrace();
                }
        }
        return success;
    }

    public static void writeArrayListToFile(ArrayList array, String file) {

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(fos);

        } catch (IOException e) {
            e.printStackTrace();
        }
        try {


            oos.writeObject(array);

        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static ArrayList readListFromFile(String file) {


        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            ArrayList hotels = (ArrayList<Object>) ois.readObject();
            return hotels;

        } catch (IOException e) {
            e.printStackTrace();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();


        }

        try {

            ois.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;


    }



}