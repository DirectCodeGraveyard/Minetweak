def mainDir = new File(getClass().protectionDomain.codeSource.location.path).parentFile.parentFile

def osName = System.properties['os.name'] as String
def cmd
if (osName.contains('windows')) {
    windows = true
    cmd = 'gradlew jar'
} else {
    cmd = './gradlew jar'
}
def process = cmd.execute([], mainDir)

def reader = process.inputStream.newReader()

reader.eachLine {
    println it
}