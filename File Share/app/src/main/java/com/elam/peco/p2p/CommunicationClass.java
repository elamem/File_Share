package com.elam.peco.p2p;

import android.app.Activity;
import android.content.Context;
import android.icu.text.SymbolTable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URI;
import java.net.UnknownHostException;

public class CommunicationClass extends AsyncTask<String,Void,Void> {

    Activity activity;
    public CommunicationClass(Activity activity){

        this.activity  = activity;
    }

    @Override
    protected Void doInBackground(String... strings) {



        try {
            try {
                Socket socket = new Socket("192.168.43.141", 9999);

                if(strings[1].equals("SEND")) {

                File file = new File(strings[0]);//Environment.getExternalStorageDirectory(), "Download/test.jpg");

               final String filename[] = file.getPath().split("/");
                PrintWriter printWriter = new PrintWriter(
                        new OutputStreamWriter(
                                socket.getOutputStream()
                        )
                );

                printWriter.print(filename[filename.length - 1]);
                printWriter.flush();

                //BufferedReader stdIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                //String in = stdIn.readLine();
                // Log.e("GET",in);


                byte[] mybytearray = new byte[(int) file.length()];
                FileInputStream fis = new FileInputStream(file);
                BufferedInputStream bis = new BufferedInputStream(fis);
                bis.read(mybytearray, 0, mybytearray.length);
                OutputStream os = socket.getOutputStream();
                System.out.println("Sending...");

                activity.runOnUiThread(new Runnable(){
                    @Override
                    public void run() {
                        FileTransferActivity.output.append("\nSending....\n");

                        FileTransferActivity.output.append("File Name : "+ filename[filename.length - 1]+"\n");
                    }
                });
                os.write(mybytearray, 0, mybytearray.length);

                os.flush();
                socket.close();

            }
            else if(strings[1].equals("RECEIVE")){

                   // DataInputStream dIn = new DataInputStream(socket.getInputStream());
                   // final Byte in = dIn.;

                    DataInputStream dis2 = new DataInputStream(socket.getInputStream());

                    final   String string = dis2.readLine();
                    Log.e("str",string);
                   if(string != null) {
                       activity.runOnUiThread(new Runnable() {
                           @Override
                           public void run() {
                               FileTransferActivity.output.append("\nReceiving....\n");

                               FileTransferActivity.output.append(string + "\n");
                           }
                       });
                   }
                   else{
                       FileTransferActivity.output.append("NULL\n");
                   }

                }



                } catch(UnknownHostException e){
                    e.printStackTrace();
                } catch(IOException e){
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }



        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        FileTransferActivity.output.append("Completed :)\n");
    }
}
