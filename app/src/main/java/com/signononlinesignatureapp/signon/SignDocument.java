package com.signononlinesignatureapp.signon;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import java.io.FileOutputStream;
import java.io.IOException;

public class SignDocument extends AppCompatActivity {
    public String signPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/signon/word.pdf";
    public String newPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/signon/word-stamp.pdf";
    public String signaturePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/signon/sign.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_document);
        main();
    }
    public void main() {
        try {
            PdfReader pdfReader = new PdfReader(signPath);

            PdfStamper pdfStamper = new PdfStamper(pdfReader,
                    new FileOutputStream(newPath));

            Image image = Image.getInstance(signaturePath);

            for(int i=1; i<= pdfReader.getNumberOfPages(); i++){

                //put content under
                PdfContentByte content = pdfStamper.getUnderContent(i);
                image.setAbsolutePosition(100f, 150f);
                content.addImage(image);

                //put content over
                content = pdfStamper.getOverContent(i);
                image.setAbsolutePosition(300f, 150f);
                content.addImage(image);

                //Text over the existing page
                BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA,
                        BaseFont.WINANSI, BaseFont.EMBEDDED);
                content.beginText();
                content.setFontAndSize(bf, 18);
                content.showTextAligned(PdfContentByte.ALIGN_LEFT,"Page No: " + i,430,15,0);
                content.endText();

            }

            pdfStamper.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

}
