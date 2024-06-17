_BUILD_NUMBER = env.BUILD_NUMBER
_BRANCH_NAME = scm.branches[0].name

TIMEZONE = "GMT+7"

// SLACK_CHANNEL_NAME = "C0179LQ5N82"

HOST_SSH_PORT= 22

node{
    // on linux / mac
    env.PATH = "${tool 'maven3'}/bin:${env.PATH}"
    // sh 'npm --version'
    try {
            stage ("Checkout source") {
                checkout scm
            }

            stage ("Build source") {
                configFileProvider([configFile(fileId: ENV_FILE_ID, targetLocation : "./src/main/resources/application-${PROFILES_ACTIVE}.properties")]) {
                    sh "mvn clean package -P${PROFILES_ACTIVE} -DskipTests"
                }
            }

            stage("Deploy build to ${_BRANCH_NAME}") {
                   deploy adapters: [tomcat9(credentialsId: TOMCAT_USER, path: '/manager/text', url: TOMCAT_URL)], contextPath: 'esso', war: '**/*.war'
            }

        currentBuild.result = "SUCCESS"
    } catch (e) {
        currentBuild.result = "FAILURE"
        throw e
    } finally {
        def time = formatMilisecondTime(currentBuild.timeInMillis, TIMEZONE)
        def duration = durationFormat(currentBuild.duration)
        def buildDetail = "\n————————————————————" +
                            "\n*Build Time:* ${time}" +
                            "\n*Duration:* ${duration}" +
                            "\n*Change Log (DESC):*\n${getChangeLog()}"

        echo buildDetail
    }
}

def getChangeLog() {
    def changeLogSets = currentBuild.changeSets
    if (changeLogSets.isEmpty()) {
        return "    (No changes)"
    }

    def text = ""
    for (int i = changeLogSets.size() - 1; i >= 0; i--) {
        for (def entry in changeLogSets[i].items) {
            text += ":white_small_square: ${entry.author} - ${entry.msg}\n"
        }
    }
    return text
}

def formatMilisecondTime(timeInMillis, timeZone) {
    return new Date(timeInMillis).format("MMM dd, yyyy HH:mm:ss", TimeZone.getTimeZone(timeZone))
}

def durationFormat(long milisecond) {
    def min = milisecond.intdiv(1000).intdiv(60)
    def sec = milisecond.intdiv(1000) % 60
    def result = (min > 0 ? "${min}m " : "") + (sec > 0 ? "${sec}s" : "")
    return result
}
