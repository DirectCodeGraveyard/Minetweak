def mainDir = new File(getClass().protectionDomain.codeSource.location.path).parentFile.parentFile
def targetList = new File(mainDir, 'tools/ci.targets')
def cmd = []

def osName = System.properties['os.name'] as String
def wrapper = './gradlew'

if (osName.contains('windows')) {
    wrapper = 'gradlew'
}

cmd.add(wrapper)

targetList.eachLine {
    cmd.add(it.trim())
}

def env = System.getenv()

println 'Starting Build.'

def builder = new ProcessBuilder(cmd as String[])

builder.directory(mainDir)

builder.redirectErrorStream(true)

def process = builder.start()

System.out << process.inputStream

println 'Build Completed.'
