# Jenkins Job DSL

Bootstrapping Jenkins with all the needed jobs.

---

Jenkins is enabled with job dsl plugin and all jobs are provisioned using the [seed-job](https://jenkins.bootcamp2021.online/view/all/job/_seed-job/).
All the jobs must be specified in [jobConfigs.yml](./jobconfigs.yml)

The seed-job when ran is expected to provision all the desired jobs. 

Note: We strongly prefer to not provision a job on Jenkins manually since the job will
be lost when we re-provision Jenkins.

### Understanding Job DSL Terminology

```yaml
    name: provision-ecs-cluster
    repo: git@github.com:aditya-mittal/bootcamp-infrastructure.git
    branch: master
    jenkinsFilePath: ecs-cluster/Jenkinsfile
    folder: environments
    runImmediately: false
```

###### name
Name of the Jenkins job

###### repo
Github repo hosting the relevant code for job

###### branch
Branch of Github repository to be tracked by the job (only needed in single branch pipelines)

###### jenkinsFilePath
Path of Jenkinsfile relative to the respective Github repository

###### folder
Folder within Jenkins to organise the job in

###### runImmediately
To queue up the job for run immediately on creation. Enable/disable Github webhook trigger for the job. Could be `true` or `false`. Defaults to `false`

###### githubHookTrigger
Enable/disable Github webhook trigger for the job. Could be `true` or `false`. Defaults to `false`.

###### buildSchedule
Cron expression to trigger the job at specified schedules.

