package user;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {
        Jdbi jdbi = Jdbi.create("jdbc:h2:mem:test");
        jdbi.installPlugin(new SqlObjectPlugin());
        try (Handle handle = jdbi.open()) {
            UserDao dao = handle.attach(UserDao.class);
            dao.createTable();

            User user1 = User.builder()
                    .username("007")
                    .password("jms7bnd")
                    .name("James Bond")
                    .email("bond007@mail.com")
                    .gender(User.Gender.MALE)
                    .dob(LocalDate.parse("1920-11-11"))
                    .enabled(true)
                    .build();

            User user2 = User.builder()
                    .username("croft68")
                    .password("l02cr14")
                    .name("Lara Croft")
                    .email("laracroft@mail.com")
                    .gender(User.Gender.FEMALE)
                    .dob(LocalDate.parse("1968-02-14"))
                    .enabled(true)
                    .build();

            User user3 = User.builder()
                    .username("superman")
                    .password("krypton77")
                    .name("Clark Kent")
                    .email("c-kent@mail.com")
                    .gender(User.Gender.MALE)
                    .dob(LocalDate.parse("1977-04-18"))
                    .enabled(true)
                    .build();

            dao.insert(user1);
            dao.insert(user2);
            dao.insert(user3);

            System.out.println("The users:");
            dao.list().stream().forEach(System.out::println);

            System.out.println("The user whose id is 2:");
            dao.findById((long)2).stream().forEach(System.out::println);

            System.out.println("The user whose username is 007:");
            dao.findByUsername("007").stream().forEach(System.out::println);

            System.out.println("After deleting user3:");

            dao.delete(user3);
            dao.list().stream().forEach(System.out::println);
        }
    }
}
