package com.sokmo.sokmoapp.connect;

import java.util.regex.Pattern;
//To validate ip address i.e  check if the input string is a valid IPv4 address
public class ValidateIP {
	private static final Pattern PATTERN = Pattern.compile(
            "^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
	public static boolean validateIP(final String ip) {
        return PATTERN.matcher(ip).matches();
    }
	public static boolean validatePort(String portNumber) {
        if ((portNumber != null) && (portNumber.length() == 4) && (portNumber.matches(".*\\d.*"))) {
            if( (Integer.parseInt(portNumber) > 1023))
                return true;
            else
                return false;
        }
        else 
            return false;
    }
}
