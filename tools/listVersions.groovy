def parseXML = { String url ->
    return new XmlParser().parse(url.toURL().newReader())
}

def list = parseXML('https://oss.sonatype.org/content/repositories/snapshots/org/minetweak/Minetweak/maven-metadata.xml')['versioning']['versions']['version']

println 'Versions:'
list.each {
  println "\t${it.text()}"
}
