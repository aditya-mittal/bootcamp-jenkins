FROM jenkins/jenkins:2.289.2-lts-jdk11

USER root

#Install docker
RUN apt-get update && \
    apt-get install --no-install-recommends -y apt-transport-https \
        ca-certificates curl gnupg-agent software-properties-common lsb-release locales build-essential && \
    curl -fsSL https://download.docker.com/linux/debian/gpg | apt-key add - && \
    add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/debian $(lsb_release -cs) stable" && \
    apt-get update && \
    apt-get install --no-install-recommends -y docker-ce && \
    apt-get -y autoremove && \
    $(dpkg -l | grep '^rc' | awk '{print $2}') | xargs apt-get purge && \
    rm -rf /var/lib/apt/lists/* && \
    usermod -aG docker jenkins

#Install aws-cli
RUN apt-get update \
    && apt-get install -y python3-pip \
    && pip3 install boto3 \
    && pip3 install awscli --upgrade

#Install tfenv
WORKDIR /var/lib/jenkins
RUN git clone https://github.com/tfutils/tfenv.git /var/lib/jenkins/.tfenv && \
    ln -s /var/lib/jenkins/.tfenv/bin/* /usr/local/bin
RUN chown -R jenkins:jenkins /var/lib/jenkins && \
    chmod 755 /var/lib/jenkins

USER jenkins

#Delay for CasC + disable install wizard
ENV JAVA_OPTS="-Dio.jenkins.plugins.casc.ConfigurationAsCode.initialDelay=9000 -Djenkins.install.runSetupWizard=false"

#Install plugins on Jenkins
COPY plugins.txt /usr/share/jenkins/ref/plugins.txt
RUN /usr/local/bin/install-plugins.sh < /usr/share/jenkins/ref/plugins.txt

COPY casc-configs /usr/share/jenkins/ref/casc-configs
ENV CASC_JENKINS_CONFIG="/usr/share/jenkins/ref/casc-configs"