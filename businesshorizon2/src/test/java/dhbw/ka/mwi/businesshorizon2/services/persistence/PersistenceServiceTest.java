package dhbw.ka.mwi.businesshorizon2.services.persistence;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import dhbw.ka.mwi.businesshorizon2.models.Project;
import dhbw.ka.mwi.businesshorizon2.models.User;

public class PersistenceServiceTest {

	@Test
	public void testLoadProjects() throws Exception {
		User dummyUser = new User();
		dummyUser.setEmailAdress("dummy@dummy.com");

		PersistenceService service = new PersistenceService();

		List<Project> dummyProjects = new ArrayList<Project>();
		Project firstProject = new Project();
		firstProject.setCreatedFrom(dummyUser);
		dummyProjects.add(firstProject);
		
		service.setAllProjects(dummyProjects);

		service.loadProjects(dummyUser);

		Assert.assertEquals(dummyProjects, dummyUser.getProjects());
	}

}
