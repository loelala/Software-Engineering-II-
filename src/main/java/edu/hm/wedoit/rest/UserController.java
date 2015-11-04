package edu.hm.wedoit.rest;

import edu.hm.wedoit.model.User;
import edu.hm.wedoit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;

/**
 * Created by B3rni on 02.11.2015.
 */

@RestController
@RequestMapping("/api/user")
public class UserController
{
    @Autowired
    UserService userService;

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public User getExample()
    {
        return new User("example","example");
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password)
    {
        if(userService.loginOK(username,password))
        {

            return new ResponseEntity<String>("Login success", HttpStatus.OK);

        }
        else
        {
            return new ResponseEntity<String>("Login failed", HttpStatus.FORBIDDEN);
        }
    }


}
