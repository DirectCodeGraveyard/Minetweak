def mainDir = new File(getClass().protectionDomain.codeSource.location.path).parentFile.parentFile

def osName = System.properties['os.name'] as String
def wrapper
if (osName.contains('windows')) {
    wrapper = 'gradlew'
} else {
    wrapper = './gradlew'
}
def cmd = wrapper + ' ' + args.join(' ')
def process = cmd.execute([], mainDir)

System.out << process.inputStream