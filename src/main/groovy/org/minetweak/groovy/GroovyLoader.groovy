package org.minetweak.groovy

import com.google.common.eventbus.Subscribe
import groovy.io.FileType
import org.codehaus.groovy.control.CompilerConfiguration
import org.codehaus.groovy.control.customizers.ImportCustomizer
import org.minetweak.Minetweak

class GroovyLoader {
    static scriptDir = new File("./plugins/")

    static def shell = new GroovyShell(prepare())

    static boolean loaded = {
        return load()
    }()

    static def load() {
        scriptDir.eachFileRecurse(FileType.FILES) {
            if (!it.name.endsWith(".groovy")) return
            def script = shell.parse(it.text)
            Minetweak.registerListener(script)
            script.run()
        }
        return true
    }

    private static prepare() {
        def config = new CompilerConfiguration()
        def imports = new ImportCustomizer()
        imports.addImports(Minetweak.class.name, Subscribe.class.name)
        config.addCompilationCustomizers(imports)
        return config
    }
}
