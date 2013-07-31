def mainDirectory = new File(getClass().protectionDomain.codeSource.location.path).getParentFile().getParentFile()
def sourceDirectory = new File(mainDirectory, 'src/main/java/org/minetweak')
int classCount = 0
sourceDirectory.eachFileRecurse {
    if (it.getName().endsWith('java')) classCount++
}
println(String.format('Found %s Minetweak classes.', classCount))