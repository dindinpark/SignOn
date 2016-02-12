
package com.signononlinesignatureapp.signon;

/**
 * Created by Naseebah on 10/02/16.
 */
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;

import java.io.File;
import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 * A tester for the CryptoUtils class.
 * @author www.codejava.net
 *
 */
public class AESencrypttionFirst extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        main();
    }
    public static void main() {
        String newPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/signon/word.pdf";
        String encPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/signon/worde.enc";
        String decPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/signon/wordd.pdf";
        byte[] key = getKey();
        File inputFile = new File(newPath);
        File encryptedFile = new File(encPath);
        File decryptedFile = new File(decPath);

        try {
            AESencryptionSecond.encrypt(key, inputFile, encryptedFile);
            AESencryptionSecond.decrypt(key, encryptedFile, decryptedFile);
        } catch (CryptoException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }
    private static byte[] getKey(){


        KeyGenerator keyGen;


        byte[] dataKey=null;

        String Key=null;
        try {


            // Generate 256-bit key


            keyGen = KeyGenerator.getInstance("AES");


            keyGen.init(256);


            SecretKey secretKey = keyGen.generateKey();


            dataKey=secretKey.getEncoded();



        } catch (NoSuchAlgorithmException e) {


            // TODO Auto-generated catch block


            e.printStackTrace();

        }


        return dataKey;


    }
}

