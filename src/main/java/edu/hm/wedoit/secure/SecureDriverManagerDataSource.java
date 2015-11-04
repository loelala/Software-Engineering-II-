package edu.hm.wedoit.secure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import sun.misc.BASE64Decoder;

import java.io.IOException;

/**
 * Created by B3rni on 04.11.2015.
 */
public class SecureDriverManagerDataSource extends DriverManagerDataSource
{
    private final static Logger logger = LoggerFactory.getLogger(SecureDriverManagerDataSource.class);

    @Override
    public String getPassword(){
        final String hashedPassword = super.getPassword();
        logger.debug("getPassword " + hashedPassword);
        return decode(hashedPassword);
    }

    private String decode(String hashedPassword)
    {
        String result = "";
        BASE64Decoder decoder = new BASE64Decoder();
        try
        {
            result = new String(decoder.decodeBuffer(hashedPassword));
            logger.debug("decode Result " + result);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return result;
    }
}
