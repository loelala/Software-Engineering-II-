package edu.hm.wedoit.usermanagement.impl;

import edu.hm.wedoit.config.Constants;
import edu.hm.wedoit.model.User;
import edu.hm.wedoit.usermanagement.UserManagement;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;

import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implements the UserManagement
 * see: {@link edu.hm.wedoit.usermanagement.UserManagement}
 */
public class UserManagementImpl implements UserManagement
{
    private final static Logger logger = LoggerFactory.getLogger(UserManagementImpl.class);

    private final String rootDir;
    private final File userdbFile;
    private final static String USER_DB_FILE="wedoitusers.dat";
    private Map<String,User> users;



    private Environment springEnv;

    /**
     * {@inheritDoc}
     */
    public UserManagementImpl(String rootDir, Environment springEnv) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, InvalidParameterSpecException, InvalidAlgorithmParameterException, FileNotFoundException
    {
        logger.debug("UserManagementImpl({}, {})", rootDir, springEnv);
        this.rootDir = rootDir;
        this.springEnv = springEnv;
        userdbFile = new File(this.rootDir + File.separator+ USER_DB_FILE);

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
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean loginOK(String username, String password)
    {
        logger.debug("loginOK({}, {})", username, password);
        if(users.containsKey(username))
        {
            String passwordHash = DigestUtils.sha256Hex(password);
            logger.debug("comapring hash " + passwordHash);
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

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean createUser(String username, String password, String email, String surname)
    {
        logger.debug("createUser({}, {})", username, password);
        if(users.containsKey(username))
        {
            return false; //user already exists
        }
        else
        {
            if(password.length() != 0)
            {

                String passwordHash = DigestUtils.sha256Hex(password);
                logger.debug("passwordHash " + passwordHash);
                User newUser = new User(username,passwordHash, email, surname);
                users.put(username,newUser);

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
            else
            {
                return false;
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeUser(String username)
    {
        logger.debug("removeUser({})", username);
        boolean result = false;
        if(users.containsKey(username))
        {
            if(users.remove(username) != null)
            {
                result = true;

                    saveUsers();

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
        logger.debug("getAllUser()");
        List<User> result = new ArrayList<>();
        for(Map.Entry<String, User> entry : users.entrySet())
        {
            result.add(entry.getValue());
        }
        return result;
    }

    @Override
    public boolean editUser(User user) {
        logger.debug("=========================  User: " +  user.getName());
        String unique = user.getName();

        if (!users.containsKey(unique)){
            return false;
        }

        User current = users.get(unique);

        String currentUsername = current.getName();
        String currentEmail = current.getEmail();
        String currentSurname = current.getSurname();

        if (user.getPassword() != null) {
            String passwordHash = DigestUtils.sha256Hex(user.getPassword());
            users.get(unique).setPassword(passwordHash);
        }
        if (!user.getName().equals(currentUsername)) {
            users.get(unique).setName(user.getName());
        }
        if (!user.getEmail().equals(currentEmail)) {
            users.get(unique).setEmail(user.getEmail());
        }
        if (!user.getSurname().equals(currentSurname)) {
            users.get(unique).setSurname(user.getSurname());
        }
        saveUsers();
        return true;
    }

    private void createDefault() throws FileNotFoundException
    {
        logger.debug("createDefault()");
        users = new HashMap<>();
        String defaultAdmin = springEnv.getProperty(Constants.DEFAULT_ADMIN_PROP,"admin");
        String defaultAdminPW = springEnv.getProperty(Constants.DEFAULT_ADMIN_PW_PROP,"nokloo");

        createUser(defaultAdmin,defaultAdminPW, "admin@wedoit.com", "Admin");

        saveUsers();

    }


    private Map<String,User> loadUsers()
    {
        logger.debug("loadUsers()");
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
        return resultMap;
    }

    private boolean saveUsers()
    {
        logger.debug("saveUsers()");
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
    }
}
