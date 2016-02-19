package com.signononlinesignatureapp.signon;

import java.net.URI;

/**
 * Created by daniah on 2/19/2016.
 */


public class Signature {
    private String SignatureName;
    private String SignatureBase64;
    private String SignerID;
    private String key;

    public Signature(String key, String SignatureName, String SignatureBase64, String SignerID){
        this.key=key;
        this.SignatureName=SignatureName;
        this.SignatureBase64=SignatureBase64;
        this.SignerID=SignerID;

    }

    public void setSignatureName(String SignatureName){this.SignatureName=SignatureName;}
    public void setSignatureBase64(String SignatureBase64){this.SignatureBase64=SignatureBase64;}
    public void setSignerID(String SignerID){this.SignerID=SignerID;}

    public String getSignatureName(){return SignatureName;}
    public String getSignerID(){return SignerID;}
    public String getSignatureBase64(){return SignatureBase64;}
    public String getKey() {return key;}
}
