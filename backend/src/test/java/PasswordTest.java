import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordTest {

    public static void main(String[] args) {

        BCryptPasswordEncoder encoder =
                new BCryptPasswordEncoder();

        String rawPassword = "Recruiter@123";

        String encodedPassword =
                encoder.encode(rawPassword);

        System.out.println("Encoded Password: " + encodedPassword);

        boolean matched =
                encoder.matches(rawPassword, encodedPassword);

        System.out.println("Password Matched: " + matched);
        String storedHash = "$2a$10$IANK7cTx49KejJ7Nt8HNAe43uJMuID9.6KrdWTjjHIdZHBrl6OCTu";

        System.out.println(
                encoder.matches("Recruiter@123", storedHash)
        );
    }
}