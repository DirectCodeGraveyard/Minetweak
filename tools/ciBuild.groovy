def mainDir = new File(getClass().protectionDomain.codeSource.location.path).parentFile.parentFile
def targetList = new File(mainDir, 'tools/ci.targets')
def targets = []

def osName = System.properties['os.name'] as String
def wrapper = './gradlew'

if (osName.contains('windows')) {
    wrapper = 'gradlew'
}

targetList.eachLine {
    targets.add(it.trim())
}

def cmd = wrapper + ' ' + targets.join(' ')
def env = System.getenv()

println 'Starting Build.'

def process = cmd.execute(["JAVA_HOME=${env['JAVA_HOME']}"], mainDir)

System.out << process.inputStream

println 'Build Completed.'
