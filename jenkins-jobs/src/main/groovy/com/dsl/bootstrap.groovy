package com.dsl

@Grab('org.yaml:snakeyaml:1.23')
import org.yaml.snakeyaml.Yaml

Yaml parser = new Yaml()
def jobsConfigFile = readFileFromWorkspace("jenkins-jobs/jobconfigs.yml")

Object jobConfigs = parser.load(jobsConfigFile)

def jobsList = []

jobConfigs["jenkinsJobs"].each {
    def jobDetails = it

    String appName = jobDetails.name
    String remoteRepo = jobDetails.repo
    String repoBranch = jobDetails.branch
    String jenkinsFilePath = jobDetails.jenkinsFilePath
    String folderName = jobDetails.folder
    boolean runImmediately = jobDetails.runImmediately

    println "config =>: JobName: $appName, Folder: $folderName, Repo: $remoteRepo, Branch: $repoBranch, JenkinsFilePath: $jenkinsFilePath"

    String jobName = appName
    String fullyQualifiedJobName;
    if(folderName) {
        folder(folderName)
        fullyQualifiedJobName = "$folderName/$jobName"
    } else {
        fullyQualifiedJobName = "$jobName"
    }

    jobsList.push(fullyQualifiedJobName)

    def jobToBeCreated = pipelineJob(fullyQualifiedJobName) {
        triggers {
            scm('H * * * *')
        }

        concurrentBuild(false)

        definition {
            cpsScm {
                scm {
                    git {
                        remote {
                            url "${remoteRepo}"
                            credentials "github-ssh-credentials"
                        }
                        branch "${repoBranch}"
                    }
                }
                scriptPath(jenkinsFilePath)
            }
        }
    }

    if (runImmediately) {
        queue(jobToBeCreated)
    }
}

buildMonitorView('All-generated-jobs') {
    description('All Generated Jobs')
    jobs {
        names(jobsList as String[])
    }
    recurse(true)
}
