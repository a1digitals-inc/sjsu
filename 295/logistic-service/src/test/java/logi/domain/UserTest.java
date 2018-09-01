package logi.domain;

import logi.domain.model.Address;
import logi.domain.model.User;
import logi.domain.repository.AddressRepository;
import logi.domain.repository.UserRepository;
import logi.domain.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {

    @Autowired
    private UserService us;
    @Autowired
    private UserRepository ur;
    @Autowired
    private AddressRepository ar;
    @Autowired
    ApplicationContext ctx;

    @Test
    public void keyGen() throws Exception {
        us.user(ur.findByName("aaa")).keygen(User.Role.Driver);
        us.user(ur.findByName("bbb")).keygen(User.Role.Driver);
        us.user(ur.findByName("ccc")).keygen(User.Role.Driver);
    }

    @Test
    public void addUser() throws Exception {
        User u = new User();
        u.setName("ccc");
        u.setPwd("ppp");

        Address a2 = new Address();
        a2.setCity("Santa Clara");
        a2.setZip("8888");
        List<Address> as = new ArrayList<>();
        a2.setUser(u);
        as.add(a2);

        u.setAddresses(as);
        u = ur.save(u);
        u = ur.findById(u.getId()).get();
        u = u;
    }

    @Test
    public void finaUser() throws Exception {
        User u = ur.findById(86L).get();
        u.toString();
    }
}
