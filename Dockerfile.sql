FROM liquibase/liquibase:4.1
LABEL maintainer=cristhianp00@gmail.com

ENV DOCKERIZE_VERSION v0.6.1
#requerid
ENV username ""
ENV password ""
ENV urldriver "jdbc:oracle:thin"
ENV urldatabase ""
ENV portdatabase ""
ENV service ""
ENV changelogfile ""
ENV classpath "lib/ojdbc6.jar"
USER root

RUN apt install -y wget

RUN wget https://github.com/jwilder/dockerize/releases/download/$DOCKERIZE_VERSION/dockerize-linux-amd64-$DOCKERIZE_VERSION.tar.gz \
    && tar -C /usr/local/bin -xzvf dockerize-linux-amd64-$DOCKERIZE_VERSION.tar.gz \
    && rm dockerize-linux-amd64-$DOCKERIZE_VERSION.tar.gz

USER liquibase

COPY /demo-proj/demo-sql/target/scripts-sql/script/                 /liquibase/script/
COPY /docker-resources/liquibase/oracle-driver/ojdbc6.jar          /liquibase/lib/ojdbc6.jar

ENTRYPOINT dockerize -wait tcp://${urldatabase}:${portdatabase} -timeout 10m sh liquibase --driver=${driver} --classpath=${classpath} --url="${urldriver}:${urldatabase}:${portdatabase}:${service}" --changeLogFile="${changelogfile}" --username=${username} --password=${password} update