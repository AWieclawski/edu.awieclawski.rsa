package edu.awieclawski.web.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Singleton;
import javax.ws.rs.core.Response;

import edu.awieclawski.cmd.utils.Calculator;
import edu.awieclawski.cmd.utils.DeEncoder;
import edu.awieclawski.web.models.Cluster;
import edu.awieclawski.web.models.CoPrimes;
import edu.awieclawski.web.models.Prime;
//import edu.awieclawski.web.models.Prime;
import edu.awieclawski.web.service.MessageService;
import edu.awieclawski.web.utils.NumberUtil;
import edu.awieclawski.web.utils.TimeUtils;

@Singleton
public class ApiUtils {

	private final static Logger LOGGER = Logger.getLogger(ApiUtils.class.getName());

	private TimeUtils tUtils = new TimeUtils();
	private Calculator calc = new Calculator();
	private NumberUtil nUtil = new NumberUtil();

	public Integer isPrime(String number) {
		MessageService thisMsgServ = MessageService.getNewMessageService();
		int numberInt = -1;
		try {
			thisMsgServ = nUtil.getIntFromStringAndMsg(number);
		} catch (NumberFormatException ex) {
			LOGGER.log(Level.SEVERE, "get Integer from Input failed. Received: " + number);
		}
		if (thisMsgServ != null) {
			numberInt = thisMsgServ.getIntResult();
			if (numberInt < 0) { // not integer
				LOGGER.log(Level.SEVERE, Response.Status.NOT_ACCEPTABLE.toString() + "| Received:" + number);
			} else { // not prime
				if (!calc.isPrimeNumber(numberInt)) {
					LOGGER.log(Level.SEVERE, Response.Status.EXPECTATION_FAILED.toString() + "| Received:" + number);
					numberInt = 0;
				}
			}
		}
		return numberInt;
	}

	public Prime isPrimeJ(String number) {
		int numberInt = isPrime(number);
		Prime prime = new Prime(numberInt, tUtils.nowString());
		return prime;
	}

	public CoPrimes isCoPrimeJ(CoPrimes coprimes) {
		Calculator calc = new Calculator();
		NumberUtil nUtil = new NumberUtil();
		MessageService thisMsgServ = MessageService.getNewMessageService();
		int modN = -1;
		int pubKey = -1;
		int phiN = -1;
		int numberInt = -1;

//		LOGGER.log(Level.WARNING, "coprimes.getModulusn()=" + coprimes.getModulusn() + ",coprimes.getPubkey()="
//				+ coprimes.getPubkey() + ",coprimes.getPhin()=" + coprimes.getPhin());

		if ((Long) coprimes.getModulusn() > 0)
			modN = (int) coprimes.getModulusn();
		if ((Long) coprimes.getPubkey() > 0)
			pubKey = (int) coprimes.getPubkey();
		if ((Long) coprimes.getPhin() > 0)
			phiN = (int) coprimes.getPhin();
		if ((Long) coprimes.getPhin() == 0 && (Long) coprimes.getModulusn() > 0)
			phiN = (int) calc.phiEuler(modN);

//		LOGGER.log(Level.WARNING, "modN=" + modN + ",pubKey=" + pubKey + ",phiN=" + phiN);

		thisMsgServ = nUtil.getCoPrimeAndMsg(modN, pubKey, phiN);
		if (thisMsgServ != null) {
			numberInt = thisMsgServ.getIntResult();
			if (numberInt < 0) { // not coprime
				LOGGER.log(Level.SEVERE,
						Response.Status.EXPECTATION_FAILED.toString() + "| Received:" + coprimes.toString());
				coprimes.setPubkey(numberInt);
			}
		}
		coprimes.setReqid(tUtils.nowString());
		if (phiN > 0)
			coprimes.setPhin(phiN);
		return coprimes;
	}

	public Cluster getEncodeJ(Cluster cluster) {
		Cluster encoded = new Cluster();
		DeEncoder enc = new DeEncoder();
		Long modN = -1L;
		Long key = -1L;
		String msg = "";
		List<Integer> asciiList = new ArrayList<>();
		List<Integer> encodedList = new ArrayList<>();

		if ((Long) cluster.getModulusn() > 0)
			modN = (Long) cluster.getModulusn();

		if ((Long) cluster.getKey() > 0)
			key = (Long) cluster.getKey();

		CoPrimes tmpCoP = new CoPrimes((long) modN, (long) key, (long) calc.phiEuler(modN.intValue()));
		key = isCoPrimeJ(tmpCoP).getPubkey();
		if (key < 0) {
			cluster.setKey(key);
			return cluster;
		}

		if (cluster.getMessage() != null)
			msg = cluster.getMessage();

		if (msg != null)
			asciiList = enc.getAsciiFromString(msg);

		if (asciiList != null) {
			encodedList = enc.getRSAfromInt(asciiList, key.intValue(), modN.intValue());
			encoded = cluster;
			encoded.setEncoded(encodedList);
			encoded.setReqid(tUtils.nowString());
		} else {
			LOGGER.log(Level.SEVERE,
					Response.Status.EXPECTATION_FAILED.toString() + "| Received:" + cluster.toString());
		}
		return encoded;
	}

}
