package com.signononlinesignatureapp.signon;

/**
 * Created by daniah on 2/29/2016.
 */
////////////////////////////// wherever you want to upload use new HDWFTP_Upload().execute("/sdcard/signon/word.pdf");
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;

import com.firebase.client.Firebase;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;

public class HDWFTP_Upload extends AsyncTask <String, Void, Long>{
    private Context context;
    String messagedigest, ekey, documentOwnerID, documentName, documentURL;
    documents document;
    documentsArrayAdapter documentAdapter;
    Firebase ref = new Firebase("https://torrid-heat-4458.firebaseio.com/users");
    HDWFTP_Upload(Context context){
        this.context=context;
    }

    protected Long doInBackground(String... FULL_PATH_TO_LOCAL_FILE ) {

        {
            Firebase.setAndroidContext(context);

            FTPClient ftpClient = new FTPClient();
            int reply;
            try {
                System.out.println("Entered Data Upload loop!");
                ftpClient.connect("ftp.byethost4.com", 21);
                ftpClient.login("b4_17442719", "pnuisalie");
                ftpClient.sendCommand("QUOTE SITE NAMEFMT 1");
                ftpClient.changeWorkingDirectory("/htdocs/");


                reply = ftpClient.getReplyCode();


                if (FTPReply.isPositiveCompletion(reply)) {
                    System.out.println("Connected Success");
                } else {
                    System.out.println("Connection Failed");
                    ftpClient.disconnect();
                }
///////////////create directory
                if(!(ftpClient.changeWorkingDirectory("/htdocs/"+session.userkey+"/"))){
                    ftpClient.makeDirectory("/htdocs/"+session.userkey+"/");
                    ftpClient.changeWorkingDirectory("/htdocs/" + session.userkey + "/");
                }
//                int length=ftpClient.listNames().length;
          //      System.out.println("length"+length);

       //         String[] names=ftpClient.listNames();

                String Picture_File_name = new File(FULL_PATH_TO_LOCAL_FILE[0]).getName();
                boolean exist=false;
             /*   for (String name : names) {
                    if (name.equals(Picture_File_name))
                        exist = true;
                }*/
                if(true){
                if (ftpClient.getReplyString().contains("250")) {
                    ftpClient.setFileType(org.apache.commons.net.ftp.FTP.BINARY_FILE_TYPE);
                    //////////////////encrypt////////////////////////////
try {
    File f = new File(FULL_PATH_TO_LOCAL_FILE[0]);
    byte[] key = AESencryptionSecond.getKey();
    String k = new String(key, Charset.forName("ASCII"));
    AESencryptionSecond.encrypt(key, f, f);
}
catch (CryptoException ex) {
    System.out.println(ex.getMessage());
    ex.printStackTrace();
}






                    BufferedInputStream buffIn = null;
                    System.out.println("Created an input stream buffer");
                    System.out.println(FULL_PATH_TO_LOCAL_FILE.toString());

                    buffIn = new BufferedInputStream(new FileInputStream(FULL_PATH_TO_LOCAL_FILE[0]));
                    ftpClient.enterLocalPassiveMode();

                    System.out.println("Entered binary and passive modes");



                    boolean result = ftpClient.storeFile(Picture_File_name, buffIn);

                    if (result) {
                        System.out.println("Success");
                        documentName=Picture_File_name;
                        documentOwnerID=session.userkey;
                        documentURL="ftp.byethost4.com/htdocs/"+session.userkey+"/"+Picture_File_name+"/";
                        messagedigest=SHA512.calculateSHA512(new File(FULL_PATH_TO_LOCAL_FILE[0]));
                        ///temp
                        ekey=null;
                        document=new documents(null,messagedigest,ekey,documentURL,documentOwnerID,documentName);
                        documentAdapter=new documentsArrayAdapter(context);
                        documentAdapter.addItem(document);
                    }


                    System.out.println("File saved");


                    ftpClient.logout();
                    ftpClient.disconnect();
                }}
                else{
                    AlertDialog alert = new AlertDialog.Builder(context).setMessage("A file with the same name already exist").setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    }).show();}

            } catch (SocketException e) {
                Log.e("HDW FTP", e.getStackTrace().toString());
                System.out.println("Socket Exception!");
            } catch (UnknownHostException e) {
                Log.e("HDW FTP", e.getStackTrace().toString());
            } catch (IOException e) {
                Log.e("HDW FTP", e.getStackTrace().toString());
                System.out.println("IO Exception!");
            }

            return null;

        }


    }}
