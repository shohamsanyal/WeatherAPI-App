//importing all of the necessary libaries

import java.io.*;
import java.net.*;
import java.util.*;

import com.google.gson.*;
import com.google.gson.reflect.*;

public class WeatherAPI 
{
    //creating a function that will find the temperature from the json string

    public static String findTemp(String str)
    {
        Map<String, Object> fullMap = new Gson().fromJson(
            str, new TypeToken<HashMap<String,Object>>() {}.getType());

        Map<String, Object> mainMap = new Gson().fromJson(fullMap.get("main").toString(),
         new TypeToken<HashMap<String,Object>>() {}.getType());

        String temperature = mainMap.get("temp").toString();

        return temperature;
    }

    public static String findMainWeather(String str)
    {
        Map<String, Object> fullMap = new Gson().fromJson(
            str, new TypeToken<HashMap<String,Object>>() {}.getType());

        ArrayList<Map<String, Object>> weathers = (ArrayList<Map<String, Object>>) fullMap.get("weather");

        Map<String, Object> weatherMap = weathers.get(0);

        String mainWeather = weatherMap.get("main").toString();

        return mainWeather;
    }

    public static String findMainWeatherDescription(String str)
    {
        Map<String, Object> fullMap = new Gson().fromJson(
            str, new TypeToken<HashMap<String,Object>>() {}.getType());

        ArrayList<Map<String, Object>> weathers = (ArrayList<Map<String, Object>>) fullMap.get("weather");

        Map<String, Object> weatherMap = weathers.get(0);
    
        String mainWeatherDescription = weatherMap.get("description").toString();
    
        return mainWeatherDescription;
    }

    public static void main(String[] args)
    {
        //variables for my unique key and location/zip
        String KEY = "a0f207b0c416c104dde56de413bc2153";
        String LOCATION_ZIPCODE;
        String UNITS;
        String LANGUAGE;

        //getting the input from the user

        Scanner input = new Scanner(System.in);

        System.out.print("Enter a City or ZIP Code: ");
        LOCATION_ZIPCODE = input.nextLine();

        System.out.print("Enter the unit type: ");
        UNITS = input.nextLine();

        System.out.print("Enter the language code: ");
        LANGUAGE = input.nextLine();

        //creating the link to the json object

        String link = "http://api.openweathermap.org/data/2.5/weather?q=" + LOCATION_ZIPCODE + "&appid="
               + KEY + "&units=" + UNITS + "&lang=" + LANGUAGE;
        
        try
        {

            //creating the necessary objects to decipher the json object

            StringBuilder result = new StringBuilder();
            URL url = new URL(link);
            URLConnection conn = url.openConnection();
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;

            while ((line = rd.readLine()) != null)
            {
                result.append(line);
            }
            rd.close();

            String resultString = result.toString();

            System.out.print("\nMain Weather: " + findMainWeather(resultString));
            System.out.println(" with " + findMainWeatherDescription(resultString));

            System.out.println("\nCurrent Temperature: " + findTemp(resultString));


        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }
}
