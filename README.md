# Jenkins
Base image for Jenkins containing all common plugins and configuration.

#### Environment variables required:
- JENKINS_URL=`something like https://env.jenkins.base.domain`

#### Secrets required (available under /run/secrets)
- JENKINS_PASSWORD=`this will be the password of jenkins admin user - admin`
- JENKINS_READ_ONLY_PASSWORD=`this will be the password of jenkins read only user - read-only`
- GITHUB_USER_NAME
- SSH_PRIVATE_KEY=`ssh private key to clone the repos`

### How to run on local
```bash
$ docker build jenkins-base -t jenkins-casc/jenkins-base

$ echo -n "jenkins_password" > /tmp/jenkins_password
$ echo -n "jenkins_read_only_password" > /tmp/jenkins_read_only_password
$ echo -n "github_username" > /tmp/github_username

$ docker run -d -p 8080:8080 \
    -e "JENKINS_URL=http://localhost:8080" \
    -v /tmp/jenkins_password:/run/secrets/JENKINS_PASSWORD \
    -v /tmp/jenkins_read_only_password:/run/secrets/JENKINS_READ_ONLY_PASSWORD \
    -v /tmp/github_username:/run/secrets/GITHUB_USER_NAME \
    -v ~/.ssh/id_rsa:/run/secrets/SSH_PRIVATE_KEY \
    --name jenkins jenkins-casc/jenkins-base
```

### Push image to ECR
```bash
# tag the docker image
$ shaId=$(git rev-parse HEAD)
$ docker tag jenkins-casc/jenkins-base 038062473746.dkr.ecr.us-east-1.amazonaws.com/bootcamp-2021-ee-pune-ecr/jenkins:${shaId}
$ docker tag jenkins-casc/jenkins-base 038062473746.dkr.ecr.us-east-1.amazonaws.com/bootcamp-2021-ee-pune-ecr/jenkins:latest

# docker login to ECR 
$ $(aws ecr get-login --no-include-email --region us-east-1)

# docker push to ECR
$ docker push 038062473746.dkr.ecr.us-east-1.amazonaws.com/bootcamp-2021-ee-pune-ecr/jenkins:${shaId}
$ docker push 038062473746.dkr.ecr.us-east-1.amazonaws.com/bootcamp-2021-ee-pune-ecr/jenkins:latest
```