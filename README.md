# edu.awieclawski.rsa
RSA key generator, use greatest common divisor, Euler's phi, coprime numbers
1. Comand-Line manager in edu.awieclawski.cmd.rsa.MainManager;
2. Web-App in  edu.awieclawski.web.* (servlets,jsp,css,jstl)
3. API Rest in edu.awieclawski.web.rest



Web-App
==
Instruction:
1. Install Tomcat 8.0 in <$ECLIPSE_WORKSSPACE>/tomcat80
2. Add <$ECLIPSE_WORKSSPACE>/tomcat80/lib/servlet-api.jar 
to the Project Java BuildPath libraries
3. Add the Project to the Server in the Server Eclipse tab
4. Deploy & visit at http://localhost:8080/edu.awieclawski.rsa/welcome
Examples:
1. p=11,q=23,e=29->d=129
3. p=113,q=241,e=827->d=5363



Rest API 
==
1. Download from:
https://eclipse-ee4j.github.io/jersey/download.html
the archive 'jaxrs-ri-2.25.1.zip' described as Jersey JAX-RS 2.0 RI bundle bundle contains the JAX-RS 2.0 API jar, all the core Jersey module jars as well as all the required 3rd-party dependencies.
2. Extract the archive and add (by right click on the Project): 
 - all the jars to the 'BuildPath' of the Project 
 - all the jars to the 'Properties' > 'Deployment Assembly'
 --
3. Deploy, by 'Run on Server' in Eclipse.
- Hello 
 Visit at: http://localhost:8080/edu.awieclawski.rsa/rest/api
- get client Agent 
 http://localhost:8080/edu.awieclawski.rsa/rest/api/agent
- is prime? 
 -
 http://localhost:8080/edu.awieclawski.rsa/rest/api/isprime/29
  -> returns '29'
 -  
 http://localhost:8080/edu.awieclawski.rsa/rest/api/isprime/22
  -> returns 'HTTP Status 417 - Expectation Failed'
 - 
 http://localhost:8080/edu.awieclawski.rsa/rest/api/isprime/aa
  -> returns 'HTTP Status 406 - Not Acceptable'
 
