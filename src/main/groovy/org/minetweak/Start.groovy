package org.minetweak

import groovy.grape.Grape
import org.codehaus.groovy.tools.shell.Main as GroovyShellMain
/**
 * Starts Minetweak
 */
class Start {
    static def main(String[] args) {
        // Checks args
        if (args.contains('--dev-console')) {
            def arguments = args.toList()
            arguments.remove('--dev-console')
            // Puts the User in a Development Console
            GroovyShellMain.main(arguments.toArray(new String[arguments.size()]))
        } else {
            // Launches Minetweak
            Minetweak.main(args)
        }
    }
}
