package com.pup.cea.iodfs.test;
 
import org.springframework.stereotype.Service;
 
import javax.annotation.PostConstruct;
 
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
 
@Service
public class UserService {
 
    private List<User> users;
 
    public List<User> login(LoginForm loginForm) {
 
        //do stuffs
    	//dump user data
    	User user = new User(loginForm.getUsername(), loginForm.getPassword(), "email@javabycode.com");
    	
        return new ArrayList<User>(Arrays.asList(user));
 
    }
 
}