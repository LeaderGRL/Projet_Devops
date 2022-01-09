#!groovy
println('------------------------------------------------------------------Import Job CI/Job1')
def pipelineScript = new File('/var/jenkins_config/jobs/job1-pipeline.groovy').getText("UTF-8")

pipelineJob('CI/Job1') {
    description("Job Pipline 1")
    parameters {
        stringParam {
            name('BRANCH')
            defaultValue('master')
            description("PARAM1 Desc")
            trim(false)
        }
        booleanParam{
            defaultValue(false)
            description('true to skip tests')
            name('SKIP_TESTS')
        }
        choiceParam{
            name('VERSION_TYPE')
            choices(['SNAPSHOT', 'RELEASE'])
            description('Select type of version')
        }
        stringParam {
            name('VERSION')
            defaultValue('SB3T-1.0-SNAPSHOT')
            description("PARAM4 Desc")
            trim(false)
        }
    }
    definition {
        cps {
            script(pipelineScript)
            sandbox()
        }
    }
}