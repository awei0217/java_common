package digester;

import org.apache.commons.digester.Digester;
import org.xml.sax.SAXException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

/**
 * tomcat 配置文件的解析工具学习
 * @Author sunpengwei on 2016/11/1.
 */
public class DigesterTest {
	private Parent parent;

	public Parent getParent() {
		return parent;
	}

	public void setParent(Parent parent) {
		this.parent = parent;
	}

	public static void main(String[] args) throws URISyntaxException, IOException, SAXException {
		//栈顶对象的规则是每创建一个对象就把他放到栈顶
		Digester digester = new Digester();
		DigesterTest di = new DigesterTest();
		//方一个栈顶对象
		digester.push(di);
		//设置不验证DTD解析规则
		digester.setValidating(false);
		//在遇到parent标签时，创建一个Parent对象
		digester.addObjectCreate("parent",Parent.class);
		//在遇到parent的子元素children时，创建一个children对象
		digester.addObjectCreate("parent/children",Children.class);
		//在遇到parent的子元素children时，调用栈顶对象的setChildren方法，传递一个Children对象，此时栈顶对象是Parent
		digester.addSetNext("parent/children","setChildren","com.jd.practice.digester.Children");
		//在遇到parent元素时,调用栈顶对象的setParent方法，传递一个Parent对象，栈顶对象目前是DegisterTest对象
		digester.addSetNext("parent","setParent","com.jd.practice.digester.Parent");
		//在遇到parent元素时，对他所有的属性调用set属性方法
		digester.addSetProperties("parent");
		//在遇到parent元素的children子元素时，对他所有的属性调用set属性方法
		digester.addSetProperties("parent/children");

		URL url = DigesterTest.class.getClassLoader().getResource("degister.xml");
		File file = new File(url.toURI());

		InputStream inputStream  = new FileInputStream(file);
		//开始解析这个xml文件,返回的是最开始的栈顶对象
		di = (DigesterTest) digester.parse(inputStream);
		Parent parent = di.getParent();
		System.out.println(parent.getName());
		List<Children> lists = parent.getListChildren();
		for (Children children:lists){
			System.out.println(children.getName());
		}
	}
}
