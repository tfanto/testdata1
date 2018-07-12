package com;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import com.fnt.entity.Role;
import com.fnt.entity.User;

public class CreateTestDataService {

	RoleRepository roleRepository = new RoleRepository();
	UserRepository userRepository = new UserRepository();

	private Random rnd = new Random();
	
	String efternamn[] = {"Svensson","Andersson","Johnsson","Eriksson","Lundberg","Knutsson","Ward","Zawinul","Hult","Clapton","Bruce","Baker","Pastorius","Kahn","Kuhn","Gates","Trump","Klant","Klint","Schwaber"};
	String fornamn[] = {"Thomas","Anna","Sture","Stina","Bengt","Lisa","Allan","Madeleine","Thore","Anna-Lena","Peter","Laila","Martin","Erika","Sten","Maria"};
	String ext[] = {"se","com","net","dk"};
	
	public void createTestData() {
		for (long i = 1; i <= 4; i++) {
			Role role = new Role();
			role.setName("Role " + i);
			role.setModule1authorized(true);
			role.setAccessmodule2(false);
			roleRepository.save(role);
		}

		Set<Role> roles = new HashSet<>();
		roles.addAll(roleRepository.findAll());
		

		for (long i = 1; i <= 20; i++) {
			
			String f = fornamn[rnd.nextInt(fornamn.length)];
			String e = efternamn[rnd.nextInt(efternamn.length)];
			String m = ext[rnd.nextInt(ext.length)];
			
			
			User user = new User();
			user.setFirstname(f);
			user.setLastname(e);
			user.setEmail(f + "@" + e + "." + m);
			String p = UUID.randomUUID().toString();
			p = p.substring(0, 10);
			user.setPassword(p);
			user.setRoles(roles);
			user.setMainrole(roles.stream().findFirst().get());
			userRepository.save(user);
		}

	}

}
