package edu.hm.wedoit.usermanagement.impl;

import edu.hm.wedoit.config.Constants;
import edu.hm.wedoit.model.User;
import edu.hm.wedoit.usermanagement.UserManagement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import sun.awt.image.BufferedImageDevice;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by B3rni on 31.10.2015.
 */
public class UserManagementImpl implements UserManagement
{

    private final static Logger logger = LoggerFactory.getLogger(UserManagementImpl.class);
    private final String rootDir;
    private final File userdbFile;
    //private final File keyFile;
    private final static String USER_DB_FILE="wedoitusers.dat";
//    private final static String KEY_FILE = "key.dat";
//    private final Cipher encryptionCipher;
//    private final Cipher decryptionCipher;
//    private SecretKey secret;
    private Map<String,User> users;

//    private static final byte[] SALT = {
//            (byte) 0xA9, (byte) 0x9B, (byte) 0xC8, (byte) 0x32,
//            (byte) 0x56, (byte) 0x35, (byte) 0xE3, (byte) 0x03
//    };


    private Environment springenv;

    public UserManagementImpl(String rootDir, Environment springenv) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, InvalidParameterSpecException, InvalidAlgorithmParameterException, FileNotFoundException
    {
        this.rootDir = rootDir;
        this.springenv = springenv;
        userdbFile = new File(this.rootDir + File.separator+ USER_DB_FILE);
//        keyFile = new File(this.rootDir + File.separator + KEY_FILE);

        if(userdbFile.exists())
        {
            users = loadUsers();
        }
        else
        {
            createDefault();
        }
//        byte[] secretBytes;
//        if(keyFile.exists())
//        {
//            secretBytes = loadKey();
//            System.out.println("loaded");
//            secret = new SecretKeySpec(secretBytes, "AES");
//        }
//        else
//        {
//            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
//            KeySpec spec = new PBEKeySpec("wedoit".toCharArray(), SALT, 65536, 128);
//
//            SecretKey tmp = factory.generateSecret(spec);
//
//            secretBytes = tmp.getEncoded();
//            for(int i = 0 ; i < secretBytes.length ; i++)
//            {
//                System.out.print(secretBytes[i]);
//            }
//            secret = new SecretKeySpec(secretBytes, "AES");
//            saveSecret(secret);
//        }




//        if(secret == null)
//        {
//            throw new RuntimeException();
//        }
//
//        encryptionCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
//        byte[] ivbyte = encryptionCipher.getParameters().getParameterSpec(IvParameterSpec.class).getIV();
//
//        encryptionCipher.init(Cipher.ENCRYPT_MODE, secret, new IvParameterSpec(ivbyte));
//
//        decryptionCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
//
//        decryptionCipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(ivbyte));

        if(userdbFile.exists())
        {
            users = loadUsers();
            if(users.size() == 0)
            {
                createDefault();
            }
        }
        else
        {
            createDefault();
        }
        //loadUsers();

    }

//    private void saveSecret(SecretKey secret)
//    {
//        try(BufferedOutputStream bos = new BufferedOutputStream( new FileOutputStream(keyFile)))
//        {
//            byte[] secretBytes = secret.getEncoded();
//            for(int i = 0 ; i < secretBytes.length ; i++)
//            {
//                System.out.print(secretBytes[i]);
//            }
//            bos.write(secretBytes);
//            System.out.println("saving " + secret.getEncoded());
//
//        }
//        catch(IOException e)
//        {
//            e.printStackTrace();
//        }
//    }

//    private byte[] loadKey()
//    {
//        try(BufferedInputStream bis = new BufferedInputStream(new FileInputStream(keyFile)))
//        {
//            byte[] secretBytes = new byte[16];
//            bis.read(secretBytes);
//            System.out.println(secretBytes.length);
//            for(int i = 0 ; i < secretBytes.length ; i++)
//            {
//                System.out.print(secretBytes[i]);
//            }
//            System.out.println();
//            return secretBytes;
//        }
//        catch (IOException e)
//        {
//            e.printStackTrace();
//        }
//        return null;
//    }

    @Override
    public boolean loginOK(String username, String passwordHash)
    {
        System.out.println("login ok - usermanagementimpl");
        if(users.containsKey(username))
        {
            if(users.get(username).getPassword().equals(passwordHash))
            {
                return true;
            }
            else
            {
                return false; //wrong password
            }
        }
        else
        {
            return false; //user does not exist
        }
    }

    @Override
    public boolean createUser(String username, String password)
    {
        if(users.containsKey(username))
        {
            return false; //user already exists
        }
        else
        {
            if(password.length() != 0)
            {
                User newUser = new User(username,password);
                users.put(username,newUser);
                try
                {
                    boolean saveStatus = saveUsers();
                    if(saveStatus)
                    {
                        return true;
                    }
                    else
                    {
                        return false;
                    }

                }
                catch (FileNotFoundException e)
                {
                    e.printStackTrace();
                    return false;
                }
            }
            else
            {
                return false;
            }
        }
    }

    @Override
    public boolean removeUser(String username)
    {
        boolean result = false;
        if(users.containsKey(username))
        {
            if(users.remove(username) != null)
            {
                result = true;
            }
            else
            {
                result = false;
            }
        }
        else
        {
            result = false;
        }
        return result;

    }

    @Override
    public User getUser(String username)
    {
        return null;
    }

    @Override
    public List<User> getAllUser()
    {
        List<User> result = new ArrayList<>();
        for(Map.Entry<String, User> entry : users.entrySet())
        {
            result.add(entry.getValue());
        }
        return result;
    }


    private void createDefault() throws FileNotFoundException
    {
        users = new HashMap<>();
        String defaultAdmin = springenv.getProperty(Constants.DEFAULT_ADMIN_PROP,"admin");
        String defaultAdminPW = springenv.getProperty(Constants.DEFAULT_ADMIN_PW_PROP,"nokloo");

        User defaultAdminUser = new User(defaultAdmin,defaultAdminPW);
        users.put(defaultAdmin,defaultAdminUser);
        saveUsers();

    }


    private Map<String,User> loadUsers()
    {
        Map<String,User> resultMap = new HashMap<>();


        try(ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(userdbFile))))
        {
            resultMap = (Map<String,User>)ois.readObject();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
//        try(ObjectInputStream ois = new ObjectInputStream(
//                new CipherInputStream(new BufferedInputStream(new FileInputStream(userdbFile)),decryptionCipher)))
//        {
//
//             resultMap = (Map<String,User>)ois.readObject();
//        }
//        catch(IOException e)
//        {
//            e.printStackTrace();
//        }
//        catch (ClassNotFoundException e)
//        {
//            e.printStackTrace();
//        }
        return resultMap;
    }

    private boolean saveUsers() throws FileNotFoundException
    {

        try(ObjectOutputStream oos = new ObjectOutputStream(
                new BufferedOutputStream (new FileOutputStream(userdbFile))))
        {
            if(users.size() == 0 )
            {
                createDefault();
            }
            oos.writeObject(users);
            return true;
        }
        catch(IOException e)
        {
            e.printStackTrace();
            return false;
        }
//         try(ObjectOutputStream oos = new ObjectOutputStream(
//                new CipherOutputStream(new BufferedOutputStream (new FileOutputStream(userdbFile)),encryptionCipher)))
//        {
//            if(users.size() == 0 )
//            {
//                createDefault();
//            }
//            oos.writeObject(users);
//            return true;
//        }
//        catch(IOException e)
//        {
//            e.printStackTrace();
//            return false;
//        }
    }

}
