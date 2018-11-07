package drools;

import org.drools.compiler.kie.builder.impl.InternalKieModule;
import org.kie.api.KieServices;
import org.kie.api.builder.*;
import org.kie.api.builder.Message.Level;
import org.kie.api.io.Resource;

/***
 * 
 * @author sunpengwei
 *
 */

public interface RuleDynamic {

	ReleaseId getLeasetId();

	KieModule getModuleById(ReleaseId id);

	default KieModule getModule() {
		return getModuleById(null);
	}

	KieModule refresh(ReleaseId id, Resource[] resource, String kmoduleContent);

	default KieModule refresh(ReleaseId id, Resource[] resource) {
		return refresh(id, resource, "");
	};
	//接口中默认静态常量
	Factory Facotry = new Factory();

	final class Factory {

		Factory() {

		}

		private final RuleDynamic impl = new Impl();

		public RuleDynamic get() {
			return impl;
		}
	}

	class Impl implements RuleDynamic {

		KieServices ks = KieServices.Factory.get();

		KieModule kieMoudle;

		ReleaseId latestId;

		Impl() {

		}

		@Override
		public KieModule refresh(ReleaseId id, Resource[] resources, String kModuleContent) {
			byte[] jar = createJar(id, resources, kModuleContent);
			Resource jarRes = ks.getResources().newByteArrayResource(jar);
			kieMoudle = ks.getRepository().addKieModule(jarRes);
			latestId = id;
			return kieMoudle;
		}

		private byte[] createJar(ReleaseId id, Resource[] resources, String kModuleContent) {
			KieFileSystem kfs = org.apache.commons.lang3.StringUtils.isEmpty(kModuleContent)
					? ks.newKieFileSystem().generateAndWritePomXML(id)
					: ks.newKieFileSystem().generateAndWritePomXML(id).writeKModuleXML(kModuleContent);

			for (int i = 0; i < resources.length; i++) {
				if (resources[i] != null) {
					kfs.write(resources[i]);
				}
			}
			KieBuilder kieBuilder = ks.newKieBuilder(kfs);

			Results results = kieBuilder.getResults();
			if (results.hasMessages(Level.ERROR)) {
				throw new IllegalStateException(results.getMessages(Level.ERROR).toString());
			}
			InternalKieModule kieModule = (InternalKieModule) ks.getRepository().getKieModule(id);
			return kieModule.getBytes();
		}

		@Override
		public KieModule getModuleById(ReleaseId id) {
			if (id == null) {
				return kieMoudle;
			}
			if (!id.equals(latestId)) {
				return ks.getRepository().getKieModule(id);
			}
			return kieMoudle;
		}

		@Override
		public ReleaseId getLeasetId() {

			return latestId;
		}
	}

}
