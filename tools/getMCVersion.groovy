def mainDir = new File(getClass().protectionDomain.codeSource.location.path).parentFile.parentFile

def minetweakMain = new File(mainDir, 'src/main/java/org/minetweak/Minetweak.java')

minetweakMain.eachLine {
    if (!it.contains('String minecraftVersion =')) {
        return
    }
    def mcVersion = evaluate(it.substring(25))
    println('Minecraft Version: ' + mcVersion)
}
