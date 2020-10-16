package io.imylam.deliverytest.thirdpartyservice;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.internal.StringJoin;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.DistanceMatrixElementStatus;
import io.imylam.deliverytest.config.ApiConfig;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GoogleMapClient {
    private final ApiConfig apiConfig;

    public long getDistance (String[] origin, String[] destination) throws Exception{
        String[] origins = new String[1];
        String[] destinations = new String[1];
        origins[0] = StringJoin.join(',', origin);
        destinations[0] = StringJoin.join(',', destination);

        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(apiConfig.getGoogleKey())
                .build();
        DistanceMatrixApiRequest req =  DistanceMatrixApi.getDistanceMatrix(
                context, origins, destinations);
        try {
            DistanceMatrix dMatrix = req.await();
            if (dMatrix == null || dMatrix.rows[0].elements[0].status != DistanceMatrixElementStatus.OK) {
                throw new Exception("Error");
            }
            return  dMatrix.rows[0].elements[0].distance.inMeters;
        } catch (Exception e) {
            System.out.println(e.toString());
            throw e;
        }
    }
}
