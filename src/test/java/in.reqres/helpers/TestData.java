package in.reqres.helpers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
public class TestData {
    protected static Properties PROPERTIES;
    static {
        try (FileInputStream fileInputStream = new FileInputStream("src/test/resources/properties/base.properties")) {
            PROPERTIES = new Properties();
            PROPERTIES.load(fileInputStream);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getData(String key) {
        return PROPERTIES.getProperty(key); } }