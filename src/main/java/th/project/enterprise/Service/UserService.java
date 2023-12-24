package th.project.enterprise.Service;

import th.project.enterprise.Entity.*;
import th.project.enterprise.Repository.UserRepoistory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepoistory userRepoistory;


    public void creatUser(Customer user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        userRepoistory.save(user);
    }
    public void creatUser(User user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        userRepoistory.save(user);
    }

    public Customer findByEmail(String email) {
        return userRepoistory.getUserByEmail(email);
    }
    public User findUserByEmail(String email) {
        return userRepoistory.getUserByEmail(email);
    }

    public boolean isCustomerPresent(String email) {
        Customer user = userRepoistory.getUserByEmail(email);
        if (user != null) {
            return true;
        }
        return false;
    }
    public boolean isUserPresent(String email) {
        User user = userRepoistory.getUserByEmail(email);
        if (user != null) {
            return true;
        }
        return false;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Customer user = userRepoistory.getUserByEmail(s);
        Employee emp = userRepoistory.getEmpByEmail(s);
        UserDetail userDetails;
        if (user == null && emp == null) {
            throw new UsernameNotFoundException("user not exits with this name");
        }
        
        if(emp != null){
            return new UserDetail(emp);
            
        }else  {
            return new UserDetail(user);
    
    
        }
        
        
    }


    public void updateUserAdreesID(Adress adress, long uid) {
        userRepoistory.updateUserAdreesID(adress, uid);
    }

    public List<Customer> getAllCustomer() {
        return userRepoistory.getAllCustomer("ADMIN");
    }
    
    public List<Employee> getAllEmployees() {

      return  userRepoistory.getAllEmployees();
    }
    
    public void removeEmployee(long id) {
        
        userRepoistory.deleteEmployeeById(id);
    }
}

