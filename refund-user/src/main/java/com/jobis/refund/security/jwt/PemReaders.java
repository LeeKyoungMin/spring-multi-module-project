package com.jobis.refund.security.jwt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;

public class PemReaders {

    // public static PrivateKey getPrivateKey(byte[] keyBytes)
    //     throws Exception {

    //     PKCS8EncodedKeySpec spec =
    //         new PKCS8EncodedKeySpec(keyBytes);
    //     KeyFactory kf = KeyFactory.getInstance("RSA");
    //     return kf.generatePrivate(spec);
    // }

    // public static PublicKey getPublicKey(byte[] keyBytes)
    //     throws Exception {

    //     X509EncodedKeySpec spec =
    //         new X509EncodedKeySpec(keyBytes);
    //     KeyFactory kf = KeyFactory.getInstance("RSA");
    //     return kf.generatePublic(spec);
    // }

    // public static PrivateKey getPrivateKey(String filename)
    //     throws Exception {

    //     byte[] keyBytes = Files.readAllBytes(Paths.get(filename));

    //     PKCS8EncodedKeySpec spec =
    //         new PKCS8EncodedKeySpec(keyBytes);
    //     KeyFactory kf = KeyFactory.getInstance("RSA");
    //     return kf.generatePrivate(spec);
    // }

    // public static PublicKey getPublicKey(String filename)
    //     throws Exception {

    //     byte[] keyBytes = Files.readAllBytes(Paths.get(filename));

    //     X509EncodedKeySpec spec =
    //         new X509EncodedKeySpec(keyBytes);
    //     KeyFactory kf = KeyFactory.getInstance("RSA");
    //     return kf.generatePublic(spec);
    // }

    // public static PrivateKey getPrivateKey(String filename) throws Exception {

    //     byte[] keyBytes = Files.readAllBytes(Paths.get(filename));

    //     PKCS8EncodedKeySpec spec =
    //         new PKCS8EncodedKeySpec(keyBytes);
    //     KeyFactory kf = KeyFactory.getInstance("RSA");
    //     return kf.generatePrivate(spec);
    // }

    // public static PublicKey getPublicKey(String filename) throws Exception {

    //     Path path = Paths.get(filename);
    //     List<String> reads = Files.readAllLines(path);
    //     String read = "";
    //     for (String str : reads){
    //         read += str+"\n";
    //     }
    //     return PemReader.getPrivateKeyFromString(read);
    // }

    public static PrivateKey getPrivateKey(String pemPrivateKey) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
        StringReader reader = new StringReader(pemPrivateKey);
        BufferedReader bufferedReader = new BufferedReader(reader);
        PemReader pemReader = new PemReader(bufferedReader);
        PemObject pemObject = pemReader.readPemObject();
        byte[] content = pemObject.getContent();
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(content);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(pkcs8EncodedKeySpec);
    }

    public static PublicKey getPublicKey(String pemPublicKey) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
        StringReader reader = new StringReader(pemPublicKey);
        BufferedReader bufferedReader = new BufferedReader(reader);
        PemReader pemReader = new PemReader(bufferedReader);
        PemObject pemObject = pemReader.readPemObject();
        byte[] content = pemObject.getContent();
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(content);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(x509EncodedKeySpec);
    }
}