package com.roquahacks.refuel;

import android.content.res.AssetManager;

import com.roquahacks.model.rest.RESTConfiguration;
import com.roquahacks.service.rest.RESTFuelService;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import retrofit2.Retrofit;

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
    }
}