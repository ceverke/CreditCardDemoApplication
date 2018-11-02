package de.adesso.blog.cep.creditcarddemo.service;

import javax.annotation.PostConstruct;

import org.kie.api.KieBase;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.builder.KieScanner;
import org.kie.api.builder.ReleaseId;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import de.adesso.blog.cep.creditcarddemo.model.IncorrectPinEvent;
import de.adesso.blog.cep.creditcarddemo.model.Transaction;

@Service
public class TransactionService {

	Logger LOGGER = LoggerFactory.getLogger(TransactionService.class);

	private static final String GROUPID = "de.adesso.blog.ceprules";
	private static final String ARTIFACTID = "CreditCardDemo";
	private static final String VERSION = "1.0.0";
	private static final String ENTRYPOINT = "MonitoringStream";

	private KieSession kieSession;

	@PostConstruct
	public void initRuleEngine() {
		LOGGER.info("Starting KIE Session...");

		KieServices kieService = KieServices.Factory.get();

		ReleaseId releaseId = kieService.newReleaseId(GROUPID, ARTIFACTID, VERSION);
		LOGGER.info("Reading rules from maven repository: GroupId: " + GROUPID + ", ArtifactId: " + ARTIFACTID
				+ ", Version " + VERSION);

		KieContainer kieContainer = kieService.newKieContainer(releaseId, this.getClass().getClassLoader());
		KieScanner kieScanner = kieService.newKieScanner(kieContainer);
		kieScanner.start(1000L);

		KieBaseConfiguration kieBaseConfig = kieService.newKieBaseConfiguration();
		kieBaseConfig.setOption(EventProcessingOption.STREAM);

		KieBase kBase = kieContainer.newKieBase(kieBaseConfig);
		try {
			kieSession = kBase.newKieSession();
			LOGGER.info("Starting KIE-Session");
			startSession();
			LOGGER.info("Session started");
		} catch (Exception exception) {
			LOGGER.info("Session could not be started");
			exception.printStackTrace();
		}
	}

	public void addTransaction(Transaction transaction, String pin) {
		if (!transaction.getCreditCard().checkPin(pin)) {
			kieSession.getEntryPoint(ENTRYPOINT).insert(new IncorrectPinEvent(transaction.getCreditCard()));
		} else {
			if (transaction.getCreditCard().isActive()) {
				kieSession.getEntryPoint(ENTRYPOINT).insert(transaction);
			}
		}
	}

	private void startSession() {
		new Thread() {
			@Override
			public void run() {
				kieSession.fireUntilHalt();
			}
		}.start();
	}

}
