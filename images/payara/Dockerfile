FROM payara/server-full:5.183

MAINTAINER Nathan, Jimmy and Mika


ADD drivers/mysql-connector-java-8.0.12.jar glassfish/lib/

RUN echo "AS_ADMIN_PASSWORD=admin" > /tmp/paypw.txt && \
	./bin/asadmin start-domain domain1 && \
	./bin/asadmin --interactive=false --user admin --passwordfile=/tmp/paypw.txt create-jdbc-connection-pool --restype javax.sql.DataSource --datasourceclassname com.mysql.cj.jdbc.MysqlDataSource --property user=root:password=root:databaseName=amt_db:useSSL=false:serverName=db:port=3306:allowPublicKeyRetrieval=true mysql_db_pool && \
	./bin/asadmin --interactive=false --user admin --passwordfile=/tmp/paypw.txt create-jdbc-resource --connectionpoolid mysql_db_pool amt_db && \
	./bin/asadmin --interactive=false --user admin --passwordfile=/tmp/paypw.txt create-javamail-resource --mailhost smtp.gmail.com --mailuser adamtprojectmin --password adminadmina --auth true --fromaddress adamtprojectmin@gmail.com --storeprotocol imap --storeprotocolclass com.sun.mail.imap.IMAPStore --transprotocol smtp --transprotocolclass com.sun.mail.smtp.SMTPTransport --debug true --property mail.smtp.socketFactory.class=javax.net.ssl.SSLSocketFactory:mail.smtp.socketFactory.port=465:mail.smtp.port=465: java/mail/swhp