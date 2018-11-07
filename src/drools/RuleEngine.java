package drools;

import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.StatelessKieSession;

/***
 * 
 * @author sunpengwei
 *
 */

public interface RuleEngine {

	<R> R execute(RuleCommand command);

	static RuleEngine generate(String serviceName) {
		return generate(serviceName, RuleDynamic.Facotry.get().getLeasetId());
	}

	static RuleEngine generate(String serviceName, ReleaseId id) {

		KieContainer kieContainer = KieServices.Factory.get().newKieContainer(id);

		KieBase kBase = kieContainer.getKieBase(serviceName);

		return new StatelessImpl(kBase);
	}

	class StatelessImpl implements RuleEngine {

		final KieBase kBase;

		StatelessKieSession session;

		StatelessImpl(KieBase kBase) {
			this.kBase = kBase;
			this.session = kBase.newStatelessKieSession();
		}

		@SuppressWarnings("unchecked")
		@Override
		public <R> R execute(RuleCommand command) {
			try {
				return (R) session.execute(command.get());
			} catch (Exception ex) {
				return (R) ex;
			}
		}

	}

}
