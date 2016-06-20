package com.roquahacks.refuel;

import android.content.res.AssetManager;

import com.roquahacks.service.RESTConfiguration;
import com.roquahacks.service.RESTFuelService;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class RESTConfigurationTest {

    private RESTConfiguration restConfig;
    private Retrofit retrofit;
    @Mock
    private AssetManager assetManager;

    @Before
    public void init() {
        final double LAT = 49.488520;
        final double LNG = 8.462758;
        final int RADIAN = 1;
        final RESTConfiguration.FuelType FUEL_TYPE = RESTConfiguration.FuelType.DIESEL;
        final RESTConfiguration.SortPolicy SORT_POLICY = RESTConfiguration.SortPolicy.PRICE;
        System.out.println(FUEL_TYPE.toString());
        this.restConfig = new RESTConfiguration()
                                .setLat(LAT)
                                .setLng(LNG)
                                .setRadian(RADIAN)
                                .setFuelType(FUEL_TYPE)
                                .setSortPolicy(SORT_POLICY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .sslSocketFactory(RESTConfiguration.getSSLSocketFactory(this.assetManager))
                .build();
        this.retrofit = new Retrofit.Builder()
                .baseUrl(this.restConfig.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


    }
    @Test
    public void fetchNearbyGasStations() throws IOException, KeyStoreException, CertificateException, NoSuchAlgorithmException, KeyManagementException {
        RESTFuelService fuelService = retrofit.create(RESTFuelService.class);
//        Observable<List<Station>> callGasStations = fuelService.fetchListStations(this.restConfig.getLat(),
//                this.restConfig.getLng(), this.restConfig.getRadian(), this.restConfig.getSortPolicy(),
//                this.restConfig.getFuelType(), RESTConfiguration.API_KEY);
//            Response<List<Station>> responseGasStations = callGasStations.execute();
//            List<Station> stations = responseGasStations.body();
//            for (Station gs: stations) {
//                   System.out.println(gs.toString());
//            }


        // Load CAs from an InputStream
// (could be from a resource or ByteArrayInputStream or ...)
//        CertificateFactory cf = CertificateFactory.getInstance("X.509");
//// From https://www.washington.edu/itconnect/security/ca/load-der.crt
//        InputStream caInput = new BufferedInputStream(new FileInputStream("/assets/tanker-cert.cer"));
//        Certificate ca;
//        try {
//            ca = cf.generateCertificate(caInput);
//            System.out.println("ca=" + ((X509Certificate) ca).getSubjectDN());
//        } finally {
//            caInput.close();
//        }
//
//// Create a KeyStore containing our trusted CAs
//        String keyStoreType = KeyStore.getDefaultType();
//        KeyStore keyStore = KeyStore.getInstance(keyStoreType);
//        keyStore.load(null, null);
//        keyStore.setCertificateEntry("ca", ca);
//
//// Create a TrustManager that trusts the CAs in our KeyStore
//        String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
//        TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
//        tmf.init(keyStore);
//
//// Create an SSLContext that uses our TrustManager
//        SSLContext context = SSLContext.getInstance("TLS");
//        context.init(null, tmf.getTrustManagers(), null);
//
//// Tell the URLConnection to use a SocketFactory from our SSLContext
//        URL url = new URL("https://creativecommons.tankerkoenig.de/json/list.php?lat=49.488520&lng=8.462758&rad=1&type=diesel&apikey=a7a9d59e-f515-05f0-3ca0-b2e3ed9b6412&sort=price");
//        HttpsURLConnection urlConnection =
//                (HttpsURLConnection) url.openConnection();
//        urlConnection.setSSLSocketFactory(context.getSocketFactory());
//        InputStream instream = urlConnection.getInputStream();
//        BufferedReader in = new BufferedReader(
//                new InputStreamReader(instream));
//        String inputLine;
//        while ((inputLine = in.readLine()) != null) {
//            Log.d("test", (inputLine));
//        }
//        instream.close();


//        URL oracle = null;
//        oracle = new URL("https://creativecommons.tankerkoenig.de/json/list.php?lat=49.488520&lng=8.462758&rad=1&type=diesel&apikey=a7a9d59e-f515-05f0-3ca0-b2e3ed9b6412&sort=price");
//        BufferedReader in = new BufferedReader(
//                new InputStreamReader(oracle.openStream()));
//
//        String inputLine;
//        while ((inputLine = in.readLine()) != null)
//            Log.d("test", (inputLine));
//        in.close();

//        GithubService service = new GithubService.Factory().create();
//        List<Repository> repos = service.publicRepositories("kristiankolthoff").execute().body();
//        for(Repository r : repos) {
//            System.out.println(r.toString());
//        }
    }
}