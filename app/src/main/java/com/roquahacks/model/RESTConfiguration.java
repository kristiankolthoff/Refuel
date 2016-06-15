package com.roquahacks.model;

import android.content.res.AssetManager;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;

/**
 * Created by Studio on 06.06.2016.
 */
public class RESTConfiguration {

    //TODO needs to be a singleton
    private double lat;
    private double lng;
    private int radian;
    private SortPolicy sortPolicy;
    private FuelType fuelType;

    private static int MAX_RADIAN = 25;
    private static int MIN_RADIAN = 1;
    public static String API_KEY = "xxxxx";
    public static String BASE_URL = "https://creativecommons.tankerkoenig.de/";

    public RESTConfiguration(FuelType fuelType, double lat, double lng, int radian, SortPolicy sortPolicy) {
        this.fuelType = fuelType;
        this.lat = lat;
        this.lng = lng;
        this.radian = radian;
        this.sortPolicy = sortPolicy;
    }

    public RESTConfiguration() {

    }

    public RESTConfiguration setLat(double lat) {
        this.lat = lat;
        return this;
    }

    public RESTConfiguration setLng(double lng) {
        this.lng = lng;
        return this;
    }

    public RESTConfiguration setRadian(int radian) {
        if(radian > MAX_RADIAN || radian < MIN_RADIAN) {
            return this;
        } else {
            this.radian = radian;
            return this;
        }
    }

    public RESTConfiguration setSortPolicy(SortPolicy sortPolicy) {
        this.sortPolicy = sortPolicy;
        return this;
    }

    public RESTConfiguration setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
        return this;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public int getRadian() {
        return radian;
    }

    public SortPolicy getSortPolicy() {
        return sortPolicy;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public static enum SortPolicy {

        PRICE ("price"),
        DISTANCE ("dist");

        private String name;

        SortPolicy(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }


        @Override
        public String toString() {
           return name;
        }
    }

    public static enum FuelType {

        E5 ("e5"),
        E10 ("e10"),
        DIESEL ("diesel"),
        ALL ("all");

        private String name;

        FuelType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }


        @Override
        public String toString() {
            return name;
        }
    }

    public static OkHttpClient getUnsafeOkHttpClient() {

        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
                @Override
                public void checkClientTrusted(
                        java.security.cert.X509Certificate[] chain,
                        String authType) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(
                        java.security.cert.X509Certificate[] chain,
                        String authType) throws CertificateException {
                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            } };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts,
                    new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext
                    .getSocketFactory();

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .sslSocketFactory(sslSocketFactory).build();
//                    .hostnameVerifier(new HostnameVerifier() {
//
//                        @Override
//                        public boolean verify(String hostname, SSLSession session) {
//                            // TODO Auto-generated method stub
//                            if (hostname.equals("https://creativecommons.tankerkoenig.de"))
//                                return true;
//                            else
//                                return false;
//                        }
//                    }).build();
//            okHttpClient.setSslSocketFactory(sslSocketFactory);
//            okHttpClient.setHostnameVerifier(new HostnameVerifier() {
//
//                @Override
//                public boolean verify(String hostname, SSLSession session) {
//                    // TODO Auto-generated method stub
//                    if (hostname.еquals("https://somevendor.com"))
//                        return true;
//                    else
//                        return false;
//                }
//            });

            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static OkHttpClient  getOkClient (){
        OkHttpClient client1 = getUnsafeOkHttpClient();
//        OkClient _client = new OkClient(client1);
        return client1;
    }

    public static SSLSocketFactory getSSLSocketFactory(AssetManager assetManager) {
        CertificateFactory cf = null;
        StringBuilder builder = new StringBuilder();
        SSLContext context = null;
        try {
            cf = CertificateFactory.getInstance("X.509");

// From https://www.washington.edu/itconnect/security/ca/load-der.crt
            InputStream caInput = assetManager.open("tanker-cert.cer");
            Certificate ca;
            try {
                ca = cf.generateCertificate(caInput);
                System.out.println("ca=" + ((X509Certificate) ca).getSubjectDN());
            } finally {
                caInput.close();
            }

// Create a KeyStore containing our trusted CAs
            String keyStoreType = KeyStore.getDefaultType();
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca", ca);

// Create a TrustManager that trusts the CAs in our KeyStore
            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
            tmf.init(keyStore);

// Create an SSLContext that uses our TrustManager
            context = SSLContext.getInstance("TLS");
            context.init(null, tmf.getTrustManagers(), null);
        } catch (CertificateException e) {
            Log.d("Refuel", "Certificate error");
        } catch (FileNotFoundException e) {
            Log.d("Refuel", "File not found");
        } catch (NoSuchAlgorithmException e) {
            Log.d("Refuel", "NoSuchAlgo");
        } catch (MalformedURLException e) {
            Log.d("Refuel", "Malformed URL");
        } catch (IOException e) {
            Log.d("Refuel", "IOException");
        } catch (KeyStoreException e) {
            Log.d("Refuel", "Keystore");
        } catch (KeyManagementException e) {
            Log.d("Refuel", "Keymanagement");
        }
        return context.getSocketFactory();
    }
}

