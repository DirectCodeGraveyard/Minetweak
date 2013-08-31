def parseXML = { String url ->
    return new XmlParser().parse(url.toURL().openStream())
}

def latestVersion = parseXML('https://oss.sonatype.org/content/repositories/snapshots/org/minetweak/Minetweak/maven-metadata.xml')['versioning']['latest'].text()

println 'Latest Version: ' + latestVersion
