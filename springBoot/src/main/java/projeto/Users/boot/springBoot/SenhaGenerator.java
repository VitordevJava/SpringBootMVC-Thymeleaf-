package projeto.Users.boot.springBoot;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class SenhaGenerator {

	public static void main(String[] args) {
		System.out.println(new BCryptPasswordEncoder().encode("123456"));
	}
}
