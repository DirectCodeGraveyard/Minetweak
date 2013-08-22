def mainDir = new File(getClass().protectionDomain.codeSource.location.path).parentFile.parentFile
def cmd = 'git pull --all'

def proc = cmd.execute([], mainDir)
System.out << proc.in