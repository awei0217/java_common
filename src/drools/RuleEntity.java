package drools;

import org.apache.commons.lang3.StringUtils;
import org.kie.api.io.Resource;
import org.kie.api.io.ResourceType;
import org.kie.internal.io.ResourceFactory;

import java.io.File;
import java.io.Serializable;

/***
 * 
 * @author gaoqi3
 *
 */

public class RuleEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3055616204178264223L;

	/**
	 *  请填写规则文件里 package 的名称
	 */
	String serviceName;
	/**
	 *  请填写规则文件的名称 ，不加后缀名
	 */
	String ruleName;
	/**
	 * 优先级，目前没用
	 */
	int priority = -1;
	/**
	 * 请写规则文件的文件名称加后缀名
	 */
	String ruleDefinition;
	/**
	 * 版本号，目前没用
	 */
	String ruleVersion = "1.0.0";

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getRuleDefinition() {
		return ruleDefinition;
	}

	public void setRuleDefinition(String ruleDefinition) {
		this.ruleDefinition = ruleDefinition;
	}

	public String getRuleVersion() {
		return ruleVersion;
	}

	public void setRuleVersion(String ruleVersion) {
		this.ruleVersion = ruleVersion;
	}

	@Override
	public String toString() {
		return "{ serviceName : " + serviceName + ",ruleName:" + ruleName + ",ruleVersion:" + ruleVersion
				+ ",ruleDefinition:"
				+ (StringUtils.isEmpty(ruleDefinition) ? "" : ruleDefinition) + "}";
	}

	public String toModuleContent() {
		StringBuffer sb = new StringBuffer();
		sb.append("<ksession name=\"").append(ruleName).append("\" type=\"stateless\" default=\"true\"").append("/>\n");
		return sb.toString();
	}

	public String toPackageName() {
		return this.serviceName + "." + this.ruleName;
	}

	public String toPackagePath() {
		return slice2Path(toPackageName());
	}

	public Resource getResource() {
		if (StringUtils.isEmpty(getRuleDefinition())){
			throw new RuntimeException("规则不能为空");
		}
		if(getRuleDefinition().split("\\.").length == 2 && getRuleDefinition().endsWith("drl")){
			File file = new File(this.getClass().getResource("/drools/"+getRuleDefinition()).getPath());
			return ResourceFactory.newFileResource(file)
					.setResourceType(ResourceType.DRL)
					.setSourcePath(toPackagePath() + "/" + "drl-" + ruleName + ".drl");
		}
		return ResourceFactory.newByteArrayResource(ruleDefinition.getBytes())
				.setResourceType(ResourceType.DRL)
				.setSourcePath(toPackagePath() + "/" + "drl-" + ruleName + ".drl");
	}

	String slice2Path(String name) {
		return new String(name).replace(".", "/");
	}

	static public RuleEntity generate(String serviceName, String ruleName, String drlOrFileName) {
		RuleEntity entity = new RuleEntity();
		entity.ruleName = ruleName;
		entity.ruleDefinition = drlOrFileName;
		entity.serviceName = serviceName;
		return entity;
	}


}
