package edu.hm.wedoit.rest;

import edu.hm.wedoit.model.User;
import edu.hm.wedoit.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Response;
import java.util.List;

/**
 * this class handles user requests from the frontend
 */
@RestController
@RequestMapping("/api/user")
public class UserController
{
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    /**
     * service passing the request to the actual business logic
     */
    @Autowired
    UserService userService;

    /**
     *
     * @return a example user
     */
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public User getExample()
    {
        logger.debug("received a request to get a example user");
        return new User("example","example");
    }

    /**
     *
     * @return returns all users in the system.
     */
    @RequestMapping(value = "/all",method = RequestMethod.GET)
    public List<User> getAllUser()
    {
        logger.debug("received a request to get all users");
        return userService.getAllUser();
    }

    /**
     * checks if the login is valid
     * @param username
     * @param password
     * @return the http code wether the login is ok (200) or not (forbidden)
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password)
    {
        logger.debug("received a login request with username: {} and password: {}",username,password );
        if(userService.loginOK(username,password))
        {
            ResponseEntity<String> responseEntity;
            HttpHeaders responseHeaders = new HttpHeaders();
            if(username.equals("admin"))
            {
                logger.debug("is admin");

                responseHeaders.set("wedoit-admin", "true");
                responseEntity = new ResponseEntity<String>(null,responseHeaders, HttpStatus.OK);

            }
            else
            {
                logger.debug("is not admin");
                responseHeaders.set("wedoit-admin", "false");
                responseEntity = new ResponseEntity<String>(HttpStatus.OK);
            }
            return responseEntity;
        }
        else
        {
            return new ResponseEntity<String>(HttpStatus.FORBIDDEN);
        }
    }

    /**
     * passes the user creation to the service
     * @param username
     * @param password
     * @param request
     * @param response
     * @return wether the user was created or not
     */
    @RequestMapping(value = "/new",method = RequestMethod.POST)
    public ResponseEntity<String> newUser(@RequestParam String username, @RequestParam String password,HttpServletRequest request, HttpServletResponse response)
    {
        logger.debug("received a create user request with username: {} and password: {}",username,password );
        if (userService.createUser(username, password))
        {
            logger.debug("created {}",username);
            ResponseEntity<String> responseEntity = new ResponseEntity<String>(HttpStatus.CREATED);
            return responseEntity;
        }
        else
        {
            logger.debug("did not create new user {}", username);
            ResponseEntity<String> responseEntity = new ResponseEntity<String>(HttpStatus.CONFLICT);
            return responseEntity;
        }
    }
}
