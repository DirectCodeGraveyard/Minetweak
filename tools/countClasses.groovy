def mainDirectory = new File(getClass().protectionDomain.codeSource.location.path).parentFile.parentFile
def sourceDirectory = new File(mainDirectory, 'src/main/java/org/minetweak')
def classCount = 0
sourceDirectory.eachFileRecurse {if (it.getName().endsWith('java')) classCount++}
println(String.format('Found %s Minetweak classes.', classCount))