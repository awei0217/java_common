package digester;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunpengwei on 2016/11/1.
 */
public class Parent {

	public Parent() {

	}

	public String getName() {
		return name;
	}
	public void setChildren(Children children) {
		listChildren.add(children);
	}
	public void setName(String name) {
		this.name = name;
	}
	private String name;

	public List<Children> getListChildren() {
		return listChildren;
	}

	private List<Children> listChildren = new ArrayList<Children>();
	private Children children;
}
