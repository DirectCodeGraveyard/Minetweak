package org.minetweak.groovy

import com.google.common.eventbus.Subscribe
import groovy.io.FileType
import org.minetweak.Minetweak

class GroovyLoader {
    static scriptDir = new File("./plugins/")

    static def shell = new GroovyShell()

    static boolean loaded = {
        return load()
    }()

    static def load() {
        scriptDir.eachFileRecurse(FileType.FILES) {
            if (!it.name.endsWith(".groovy")) return
            def script = shell.parse(prepare(it.text))
            Minetweak.registerListener(script)
            script.run()
        }
        return true
    }

    static def prepare(String scriptText) {
        def s = System.lineSeparator()
        return "import ${Subscribe.class.name}${s}import ${Minetweak.class.name}${s}${scriptText}"
    }
}
