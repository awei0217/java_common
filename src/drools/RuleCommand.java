package drools;

import org.kie.api.command.Command;
import org.kie.internal.command.CommandFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/***
 * 
 * @author gaoqi3
 *
 */

public interface RuleCommand {

	<G> RuleCommand global(String key, G global);

	<F> RuleCommand accept(String key, F fact);

	RuleCommand accept(Object... facts);

	<T> RuleCommand accept(List<T> facts);

	RuleCommand query(String key, String functionName);

	RuleCommand recovery();

	Command<?> get();

	static RuleCommand generate() {

		return new RuleCommand() {

			final List<Command<?>> cmds = new ArrayList<>();

			@Override
			public <G> RuleCommand global(String key, G global) {
				cmds.add(CommandFactory.newSetGlobal(key, global, true));
				return this;
			}

			@Override
			public <F> RuleCommand accept(String key, F fact) {
				cmds.add(CommandFactory.newInsert(fact, key));
				return this;
			}

			@Override
			public RuleCommand query(String key, String functionName) {
				cmds.add(CommandFactory.newQuery(key, functionName));
				return this;
			}

			@Override
			public RuleCommand recovery() {
				this.cmds.clear();
				return this;
			}

			@Override
			public Command<?> get() {
				return cmds.size() > 1 ? CommandFactory.newBatchExecution(cmds) : cmds.get(0);
			}

			@Override
			public RuleCommand accept(Object... facts) {
				return accept(Arrays.asList(facts));
			}

			@Override
			public <T> RuleCommand accept(List<T> facts) {
				cmds.add(CommandFactory.newInsertElements(facts));
				return this;
			}

		};
	}

}
