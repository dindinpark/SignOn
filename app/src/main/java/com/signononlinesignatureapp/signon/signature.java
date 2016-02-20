package com.signononlinesignatureapp.signon;

import java.net.URI;

/**
 * Created by daniah on 2/19/2016.
 */


public class signature {
    private String signatureName;
    private String signatureBase64;
    private String signerID;
    private String key;
    public signature(){}
    public signature(String key, String signatureName, String signatureBase64, String signerID){
        this.key=key;
        this.signatureName=signatureName;
        this.signatureBase64=signatureBase64;
        this.signerID=signerID;

    }

    public void setSignatureName(String signatureName){this.signatureName=signatureName;}
    public void setSignatureBase64(String SignatureBase64){this.signatureBase64=SignatureBase64;}
    public void setSignerID(String signerID){this.signerID=signerID;}

    public String getSignatureName(){return signatureName;}
    public String getSignerID(){return signerID;}
    public String getSignatureBase64(){return signatureBase64;}
    public String getKey() {return key;}
}
