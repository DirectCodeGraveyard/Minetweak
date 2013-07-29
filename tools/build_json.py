__author__ = 'Kenneth Endfinger'

import json
import re
import os

gradleScript = open('build.gradle', 'r')
data = {}
build = 'unknown'
if os.path.exists('build-number.txt'):
    buildNumberFile = open('build-number.txt', 'r')
    build = buildNumberFile.read().strip('\r\n \n')
    buildNumberFile.close()

for line in gradleScript:
    if re.search('def version =', line):
        data['version'] = line.split(' ')[3].replace('\'', '').strip('\r\n \n')
        break
print('Minetweak Version: ' + data['version'])
print('Minetweak Build Number: ' + build)
jsonData = json.dumps(data)
if build != 'unknown':
    buildsFile = open('builds.json', 'w+')
    builds = {}
    try:
        builds = json.loads(buildsFile.read())
    except ValueError:
        # Do Nothing
        pass
    builds[build] = data
    buildsFile.write(json.dumps(builds, indent=True))