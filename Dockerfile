FROM jboss/wildfly:19.0.0.Final
LABEL maintainer=cristhianp00@gmail.com

ENV DOCKERIZE_VERSION v0.6.1
ENV PORT_APP 8080
ENV spring.profiles.active prod

#requerid
ENV username_db ""
ENV password_db ""
ENV url_db ""
ENV port_db ""
ENV service_db ""
ENV urldriver_db ""
ENV driver_class_db oracle.jdbc.driver.OracleDriver
ENV driver_name_db ""

USER root

RUN yum -y install wget

RUN wget https://github.com/jwilder/dockerize/releases/download/$DOCKERIZE_VERSION/dockerize-linux-amd64-$DOCKERIZE_VERSION.tar.gz \
    && tar -C /usr/local/bin -xzvf dockerize-linux-amd64-$DOCKERIZE_VERSION.tar.gz \
    && rm dockerize-linux-amd64-$DOCKERIZE_VERSION.tar.gz

COPY /demo-proj/demo-ear/target/demo.ear     /opt/jboss/wildfly/standalone/deployments/demo.ear
COPY /docker-resources/wildfly/oracle              /opt/jboss/wildfly/modules/system/layers/base/com/oracle/
COPY /docker-resources/wildfly/standalone.xml     /opt/jboss/wildfly/standalone/configuration/standalone.xml

EXPOSE $PORT_APP
CMD dockerize -wait tcp://${url_db}:${port_db} -timeout 10m /opt/jboss/wildfly/bin/standalone.sh -b 0.0.0.0