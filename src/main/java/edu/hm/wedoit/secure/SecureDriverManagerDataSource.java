package edu.hm.wedoit.secure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import sun.misc.BASE64Decoder;

import java.io.IOException;

/**
 * This class extends the DriverManagerDataSource to decrypt the password. which is stored securely
 */
public class SecureDriverManagerDataSource extends DriverManagerDataSource
{
    private final static Logger logger = LoggerFactory.getLogger(SecureDriverManagerDataSource.class);

    @Override
    public String getPassword()
    {
        logger.debug("getPassword()");
        final String hashedPassword = super.getPassword();
        return decode(hashedPassword);
    }

    private String decode(String hashedPassword)
    {
        logger.debug("decode({})", hashedPassword);
        String result = "";
        BASE64Decoder decoder = new BASE64Decoder();
        try
        {
            result = new String(decoder.decodeBuffer(hashedPassword));
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return result;
    }
}
