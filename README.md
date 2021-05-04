====== edu.awieclawski.rsa ======

===== Description =====

RSA key generator, use greatest common divisor, Euler's phi, coprime numbers
(no Maven etc.)
1. Comand-Line manager in edu.awieclawski.cmd.rsa.MainManager;
2. Web-App in  edu.awieclawski.web.* (servlets,jsp,css,jstl);
3. API Rest in edu.awieclawski.web.rest;

===== Web-App =====

==== instruction: ====
1. Install Tomcat 8.0 in <$ECLIPSE_WORKSSPACE>/tomcat80
2. Add <$ECLIPSE_WORKSSPACE>/tomcat80/lib/servlet-api.jar 
to the Project Java BuildPath libraries
3. Add the Project to the Server in the Server Eclipse tab
4. Deploy & visit at http://localhost:8080/edu.awieclawski.rsa/welcome

==== examples: ====
* p=11,q=23,e=29->d=129
* p=113,q=241,e=827->d=5363

===== Rest API =====

==== requirements: ====
1. Download from: https://eclipse-ee4j.github.io/jersey/download.html
the archive 'jaxrs-ri-2.25.1.zip' described as 'Jersey JAX-RS 2.0 RI bundle bundle contains the JAX-RS 2.0 API jar, all the core Jersey module jars as well as all the required 3rd-party dependencies.'
2. Extract the archive and add all the jars (by mouse right click on the Project): 
 - to the 'BuildPath' > 'Libraries' tab  
 - to the 'Properties' > 'Deployment Assembly'
3. Deploy, by 'Run on Server' in Eclipse.

==== examples: ====
* visit Hello page at: 
	http://localhost:8080/edu.awieclawski.rsa/rest/api
* get client Agent: 
	 http://localhost:8080/edu.awieclawski.rsa/rest/api/agent
* check if prime number: 

	http://localhost:8080/edu.awieclawski.rsa/rest/api/isprime/29
	  -> returns '29';
	  
	http://localhost:8080/edu.awieclawski.rsa/rest/api/isprime/22
	  -> returns 'HTTP Status 417 - Expectation Failed';
	  
	http://localhost:8080/edu.awieclawski.rsa/rest/api/isprime/aa
	  -> returns 'HTTP Status 406 - Not Acceptable';


==== JSON requirements: ====	
1. Download jars from Maven repos:
* jackson-annotations-2.2.3.jar
* jackson-core-2.2.3.jar
* jackson-databind-2.2.3.jar
* jackson-jaxrs-base-2.2.3.jar
* jackson-jaxrs-json-provider-2.2.3.jar
* jackson-jaxrs-xml-provider-2.2.3.jar
* jackson-module-jaxb-annotations-2.2.3.jar  
2. add mentioned above jars (by right click on the Project)  
 - to the 'BuildPath' > 'Libraries' of the Project 
 - to the 'Properties' > 'Deployment Assembly'
===== examples =====
	   
	http://localhost:8080/edu.awieclawski.rsa/rest/api/isprime_rj/29
	  -> returns {"value":29,"reqid":"20210504182903794"} as JSON;	 