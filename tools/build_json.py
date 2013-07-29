__author__ = 'Kenneth Endfinger'

import json
import re
import os
from urllib.request import urlopen

gradleScript = open('build.gradle', 'r')
data = {}
builds = {}

print('Loading Previous Builds')
# Loads Builds from the Repo
builds = json.loads(urlopen('http://repo.minetweak.org/builds.json').read().decode('utf-8'))
build = 'unknown'
if os.path.exists('build-number.txt'):
    buildNumberFile = open('build-number.txt', 'r')
    for line in buildNumberFile:
        if re.search('build.number', line):
            build = line.split('=')[1].strip('\r\n \n')
            break
    buildNumberFile.close()

for line in gradleScript:
    if re.search('def version =', line):
        data['version'] = line.split(' ')[3].replace('\'', '').strip('\r\n \n')
        break
print('Minetweak Version: ' + data['version'])
print('Minetweak Build Number: ' + build)

# Adds the Build Data to the previous builds
builds[build] = data

# Saves the builds.json file
if os.path.exists('builds.json'):
    os.remove('builds.json')
buildsFile = open('builds.json', 'w+')
buildsFile.write(json.dumps(builds, sort_keys=True, indent=True))