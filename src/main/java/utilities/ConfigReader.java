package utilities;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;

public final  class ConfigReader {

    private static final Properties properties = new Properties();

    private ConfigReader(){

    }

    static{
        String FileName = "config.properties";

        try(InputStream inputStream = ConfigReader.class.getClassLoader().getResourceAsStream(FileName)){
            if(inputStream == null){
                throw new RuntimeException("Could not find" + FileName + "in the class pathResourcess");
            }
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("unable to load properties configaration from " + FileName, e);
        }
    }

    public static String  getProperty(String key){
        String value = properties.getProperty(key);
        if(value == null || value.trim().isEmpty()){
            throw new RuntimeException("Property '" + key + "' was not found or is empty in config.properties");
        }

        return value.trim();
    }
}
