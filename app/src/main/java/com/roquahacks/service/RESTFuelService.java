package com.roquahacks.service;

import com.roquahacks.model.RESTStationDetail;
import com.roquahacks.model.RESTStatus;
import com.roquahacks.model.Station;
import com.roquahacks.model.RESTConfiguration;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Studio on 07.06.2016.
 */
public interface RESTFuelService {

    @GET("json/list.php")
    Observable<RESTStatus> fetchListStations(@Query("lat") double lat, @Query("lng") double lng,
                                             @Query("rad") int radian, @Query("sort") RESTConfiguration.SortPolicy sortPolicy,
                                             @Query("type") RESTConfiguration.FuelType fuelType,
                                             @Query("apikey") String apiKey);

    @GET("json/details.php")
    Observable<RESTStationDetail> fetchStationDetail(@Query("id") String id, @Query("apiKey") String apiKey);

    public class Factory {

        private InputStream caInput;

        private OkHttpClient getOkHttpClient() {
            CertificateFactory cf = null;
            StringBuilder builder = new StringBuilder();
            SSLContext context = null;
            TrustManagerFactory tmf = null;
            try {
                cf = CertificateFactory.getInstance("X.509");
                //InputStream caInput = getAssets().open("tanker-cert.cer");
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
                tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
                tmf.init(keyStore);

                // Create an SSLContext that uses our TrustManager
                context = SSLContext.getInstance("TLS");
                context.init(null, tmf.getTrustManagers(), null);
                return new OkHttpClient.Builder()
                        .sslSocketFactory(context.getSocketFactory(), (X509TrustManager) tmf.getTrustManagers()[0])
                        .build();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (CertificateException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (KeyStoreException e) {
                e.printStackTrace();
            } catch (KeyManagementException e) {
                e.printStackTrace();
            }
            return null;
        }

        public RESTFuelService build() {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(RESTConfiguration.BASE_URL)
                    .client(this.getOkHttpClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            return retrofit.create(RESTFuelService.class);
        }

        public Factory setCaInput(InputStream caInput) {
            this.caInput = caInput;
            return this;
        }

        public InputStream getCaInput() {
            return caInput;
        }
    }
}
