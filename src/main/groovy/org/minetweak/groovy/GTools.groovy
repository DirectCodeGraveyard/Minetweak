package org.minetweak.groovy

class GTools {
    static void write(File file, String line) {
        if (!file.exists()) file.createNewFile()
        file.append("${line}")
    }
}
