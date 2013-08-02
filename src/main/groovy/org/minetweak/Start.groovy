package org.minetweak

import org.codehaus.groovy.tools.shell.Main as GroovyShellMain

/**
 * Starts Minetweak
 */
class Start {
    static def main(String[] args) {
        if (args.contains('--dev-console')) {
            List<String> arguments = args.toList()
            arguments.remove('--dev-console')
            GroovyShellMain.main(arguments.toArray(new String[arguments.size()]))
            return
        }
        Minetweak.main(args)
    }
}
