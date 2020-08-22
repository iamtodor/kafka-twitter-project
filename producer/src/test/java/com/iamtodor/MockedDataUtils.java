package com.iamtodor;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DataUtils {

    public static final String EXPECTED_USER_ID = "16259258";

    public static String loadJsonRecord() {
        byte[] encoded = new byte[0];
        try {
            encoded = Files.readAllBytes(Paths.get("src/test/resources/record.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String(encoded, StandardCharsets.UTF_8);
    }

}
