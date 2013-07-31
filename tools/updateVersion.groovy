if (args.size() != 1) {
    println('Usage: updateVersion.groovy New.Version'); return
}

def mainDir = new File(getClass().protectionDomain.codeSource.location.path).parentFile.parentFile
def gradleScript = new File(mainDir, 'build.gradle')
def tmpFile = new File('build.gradle.tmp')
gradleScript.eachLine {
    if (it.startsWith('def tweakVersion')) {
        println 'Found Version: ' + evaluate(it)
        it = 'def tweakVersion = \'' + args[0] + '\''
    }
    tmpFile.append(it + '\n')
}
gradleScript.delete()
tmpFile.renameTo(gradleScript)
println('Set Version to ' + args[0])
