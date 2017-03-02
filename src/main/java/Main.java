import com.google.maps.ElevationApi;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApiRequest;
import com.google.maps.model.ElevationResult;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import com.google.maps.GeocodingApi;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;




public class Main {

    public static Scanner stringScanner = new Scanner(System.in);
    public static Scanner numberScanner = new Scanner(System.in);

    public static void main(String[] args) throws Exception{

        String key = null;

        try(BufferedReader reader = new BufferedReader(new FileReader("key.txt"))){
            key = reader.readLine();
            System.out.println(key);
        } catch (Exception ioe) {
            System.out.println("No key file found, or could not read key. Please verify key.txt is present");
            System.exit(-1);
        }


        System.out.println("Where are you?");
        String userLocation = stringScanner.nextLine();


        GeoApiContext context1 = new GeoApiContext().setApiKey(key);
        GeocodingResult[] geoResult = GeocodingApi.geocode(context1, userLocation).await();

        if (geoResult.length >= 1){
            for (int i = 0; i <geoResult.length; i++){
                System.out.println("The result of what you entered " + geoResult[i].formattedAddress);
            }

            System.out.println("Choose a location result number");
            int userNumber = numberScanner.nextInt();


            LatLng userPick = geoResult[userNumber].geometry.location;

            ElevationResult[] userResults = ElevationApi.getByPoints(context1, userPick).await();

            if(userResults.length >= 1){

                ElevationResult userElevation = userResults[0];
                System.out.println("Elevation of " + geoResult[0].formattedAddress + " above sea level is " + userElevation.elevation + " meters.");
                System.out.println(String.format("The elevation of " + geoResult[0].formattedAddress + " above sea level is %.2f meters.", userElevation.elevation));
            }



        }



    }
}
