package com.signononlinesignatureapp.signon;

/**
 * Created by Naseebah on 28/01/16.
 */

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import java.io.FileOutputStream;
import java.io.IOException;

public class MyPdfViewerActivity extends Pdftry {//implements View.OnTouchListener {

    public String signPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/signon/word.pdf";
    public String newPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/signon/word-S.pdf";
    public String signaturePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/signon/sign.png";
    public void displayAlertDialog() {
//////////
        AlertDialog.Builder alert = new AlertDialog.Builder(MyPdfViewerActivity.this);
        alert.setTitle("Signing");
        alert.setMessage("Do you want to sign on all pages?");
        alert.setCancelable(false);
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                allOrNot=false;

            }
        });

        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                allOrNot=true;
            }
        });

        AlertDialog dialog = alert.create();
        dialog.show();

    }

    public void merge(float x,float y,int pageNum) {
        try {

            PdfReader pdfReader = new PdfReader(signPath);
            //fix y
            y=pdfReader.getCropBox(1).getHeight()-y;
            PdfStamper pdfStamper = new PdfStamper(pdfReader,
                    new FileOutputStream(newPath));

            Image image = Image.getInstance(signaturePath);

            if (pageNum==-1) {

                for (int i = 1; i <= pdfReader.getNumberOfPages(); i++) {

                    //put content under
                    PdfContentByte content = pdfStamper.getUnderContent(i);
                    image.setAbsolutePosition(x, y);
                    content.addImage(image);

                    //put content over
                    content = pdfStamper.getOverContent(i);
                    image.setAbsolutePosition(x, y);
                    content.addImage(image);

                    //Text over the existing page
                    /*BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA,
                            BaseFont.WINANSI, BaseFont.EMBEDDED);
                    content.beginText();
                    content.setFontAndSize(bf, 18);
                    content.showTextAligned(PdfContentByte.ALIGN_LEFT, "Page No: " + i, 430, 15, 0);
                    content.endText();*/
                }
            }
            else{
                PdfContentByte content =  pdfStamper.getOverContent(pageNum);
                image.setAbsolutePosition(x, y);
                content.addImage(image);

            }
            pdfStamper.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    public int getPreviousPageImageResource() {

        return R.drawable.left_arrow;
    }

    public int getNextPageImageResource() {
        return R.drawable.right_arrow;
    }

    public int getZoomInImageResource() {
        return R.drawable.zoom_in;
    }

    public int getZoomOutImageResource() {
        return R.drawable.zoom_out;
    }
    public int getSelectSignatureImageResource(){
        return  R.drawable.select_signature;
    }
    public int getPdfPasswordLayoutResource() {
        return R.layout.pdf_file_password;
    }

    public int getPdfPageNumberResource() {
        return R.layout.dialog_pagenumber;
    }

    public int getPdfPasswordEditField() {
        return R.id.etPassword;
    }

    public int getPdfPasswordOkButton() {
        return R.id.btOK;
    }

    public int getPdfPasswordExitButton() {
        return R.id.btExit;
    }

    public int getPdfPageNumberEditField() {

        return R.id.pagenum_edit;}


    /////////////////////////////////////////////////////////////////////////
    public int getsignatureImageReasource(){return R.drawable.signature;}
    /////////////////////////////////////////////////////////////////////////


    // @Override
    // public int getNextPageImageResource() {
    // return 0;
    // }
    //
    // @Override
    // public int getPdfPageNumberEditField() {
    // return 0;
    // }
    //
    // @Override
    // public int getPdfPageNumberResource() {
    // return 0;
    // }
    //
    // @Override
    // public int getPdfPasswordEditField() {
    // return 0;
    // }
    //
    // @Override
    // public int getPdfPasswordExitButton() {
    // // TODO Auto-generated method stub
    // return 0;
    // }
    //
    // @Override
    // public int getPdfPasswordLayoutResource() {
    // // TODO Auto-generated method stub
    // return 0;
    // }
    //
    // @Override
    // public int getPdfPasswordOkButton() {
    // // TODO Auto-generated method stub
    // return 0;
    // }

}