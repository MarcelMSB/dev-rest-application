@echo off

rem finishes the previous process if necessary 
for /f "tokens=5" %%a in ('netstat -aon ^| find ":13005" ^| find "LISTENING"') do taskkill /f /pid %%a
:exit

set JAVA_OPTS=-Xms256m -Xmx768m -XX:MaxMetaspaceSize=256M -Djava.net.preferIPv4Stack=true -Dswarm.http.port=10000 -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=10001 -Djava.io.tmpdir=tmpdir
rem call mvn package wildfly-swarm:package -Plocal -DskipTests=true 
call cd target & mkdir tmpdir & java %JAVA_OPTS% -jar rest-application-swarm.jar & cd ..