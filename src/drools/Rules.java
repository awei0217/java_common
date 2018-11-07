package drools;

import org.kie.api.io.Resource;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.function.Tuples;

import java.util.List;

/***
 * 
 * @author gaoqi3
 *
 */

public final class Rules {


	final Flux<RuleEntity> entities;

	String moduelContent;

	public Rules(RuleEntity... entities) {
		this.entities = Flux.just(entities);
		init();
	}

	public void init() {

		moduelContent = entities.groupBy(RuleEntity::getServiceName)
				.flatMap(g -> g.collectList().publishOn(Schedulers.newParallel("par-grp")).flatMap(lss -> {
					return call(g.key(), lss);
				})).reduce("\n", (value, key) -> {
					return value + key + "\n";
				}).map(value -> {
					StringBuffer sb = new StringBuffer();
					sb.append("<kmodule xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" \n  ")
							.append("       xmlns=\"http://www.drools.org/xsd/kmodule\">").append(value)
							.append("</kmodule>");
					return sb.toString();
				})
				.block();
	}

	public String getModuleContent() {
		return this.moduelContent;
	}

	public Resource[] getResources() {
		List<Resource> resources = entities.map(value -> value.getResource()).collectList().block();
		return resources.toArray(new Resource[resources.size()]);
	}



	public Mono<String> call(String key, List<RuleEntity> values) {
		return Flux.fromIterable(values).doOnNext(this::toResource)
				.reduce(Tuples.of("", ""), (tp, r) -> {
			return Tuples.of(tp.getT1() + r.toModuleContent(), tp.getT2() + "," + r.toPackageName());
		}).map(value -> {
			String group = value.getT2().startsWith(",") ? value.getT2().substring(1, value.getT2().length())
					: value.getT2();
			StringBuffer defSession = new StringBuffer();
			defSession.append("<ksession name=\"").append(key).append("\" type=\"stateless\" default=\"true\"")
					.append("/>\n");
			return "<kbase " + "name=\"" + key + "\" packages= \"" + group + "\">\n" + defSession.toString()
					+ "</kbase>";
		});
	}

	public void toResource(RuleEntity entity) {

		System.out.println("规则："+"serviceName:"+entity.toPackageName()+ " ruleName:"+entity.getRuleName()+ "version:"+entity.getRuleVersion()+"初始化");

	}

}
