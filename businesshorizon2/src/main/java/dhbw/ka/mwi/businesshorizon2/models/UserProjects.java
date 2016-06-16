package dhbw.ka.mwi.businesshorizon2.models;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
/**
 * @author Peter Kaisinger
 * Root Klasse für das Serialisieren der Projekte nach XML
 */
@Root
public class UserProjects {
	
	@ElementList(required=false)
	private List<Project> projects = new ArrayList<Project>();
	
	
	public List<Project> getProjects() {
		return projects;
	}
	
	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}
	
	public void addProject(Project project){
		projects.add(project);
	}
	
}
