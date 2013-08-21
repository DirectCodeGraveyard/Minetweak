import groovy.json.JsonSlurper

def url = 'https://api.github.com/repos/Minetweak/Minetweak/branches'
def loadJSON = { String jsonURL -> return new JsonSlurper().parse(url.toURL().openStream().newReader()) as List<Map<String, String>>}
def branchInfo = loadJSON url
def branches = []
branchInfo.each {branches.add(it.get('name'))}
println "Branches:\n\t${branches.join('\n\t')}"