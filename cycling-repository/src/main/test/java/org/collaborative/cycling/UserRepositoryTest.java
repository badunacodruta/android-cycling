package org.collaborative.cycling;

import org.collaborative.cycling.records.UserRecord;
import org.collaborative.cycling.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;

@ContextConfiguration(locations = { "classpath:repository-context-test.xml" })
public class UserRepositoryTest extends AbstractTestNGSpringContextTests {

    private static final String testEmail = "codruta@mail.com";

    @Autowired
    private UserRepository userRepository;

    @BeforeSuite
    public void setup () {
    }

    @AfterSuite
    public void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    public void testWriteReadDb() {
        UserRecord codrutaBaduna = new UserRecord(testEmail, untitledActivitiesIndex);
        userRepository.save (codrutaBaduna);
        assertEquals(userRepository.count(), 1);
        assertEquals(userRepository.findAll().get(0).getEmail(), testEmail);
    }



//    @Test
//    public void avdbaskj() {
//
//
//        List<Coordinate> coordinates = new ArrayList<>();
//        coordinates.add(new Coordinate(1.2345, 2.3456));
//        coordinates.add(new Coordinate(3.2345, 4.3456));
//        coordinates.add(new Coordinate(5.2345, 6.3456));
//
//        String s = Utilities.serialize(coordinates);
//        List<Coordinate> des = Utilities.deserialize(s, new ArrayList<Coordinate>().getClass());
//
//        System.out.println(s);
//        System.out.println(des);
//
//}


}
