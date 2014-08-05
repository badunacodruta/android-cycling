package org.collaborative.cycling;

import org.collaborative.cycling.records.UserRecord;
import org.collaborative.cycling.repositories.UserRepository;
import org.junit.AfterClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.*;

import static org.testng.AssertJUnit.assertEquals;

@ContextConfiguration(locations = { "classpath:repository-context-test.xml" })
public class UserRepositoryTest extends AbstractTestNGSpringContextTests {

    private static final String testEmail = "codruta@mail.com";

    @Autowired
    private UserRepository userRepository;

    @BeforeSuite
    public void setup () {
    }

    @Test
    public void testWriteReadDb() {
        UserRecord codrutaBaduna = new UserRecord(testEmail);
        userRepository.save (codrutaBaduna);
        assertEquals(userRepository.count(), 1);
        assertEquals(userRepository.findAll().get(0).getEmail(), testEmail);
    }

    @AfterSuite
    public void tearDown() {
        userRepository.deleteAll();
    }
}
