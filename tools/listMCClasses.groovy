import groovy.io.FileType

def mainDir = new File(getClass().protectionDomain.codeSource.location.path).parentFile.parentFile
def srcDir = new File(mainDir, 'src/main/java')
def mcClassDir = new File(srcDir, 'net/minecraft/')
mcClassDir.eachFileRecurse (FileType.FILES) {
    if (!it.getName().endsWith('.java')) {return}
    def className = it.getCanonicalPath().replace(srcDir.absolutePath, '').replace('/', '.').substring(1).replace('.java', '')
    println className
}
