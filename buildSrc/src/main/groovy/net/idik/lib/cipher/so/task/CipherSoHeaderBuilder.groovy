package net.idik.lib.cipher.so.task

import net.idik.lib.cipher.so.extension.KeyExt
import net.idik.lib.cipher.so.utils.StringUtils

class CipherSoHeaderBuilder {
    private List<KeyExt> keys

    private String fileName

    CipherSoHeaderBuilder(String fileName, List<KeyExt> keys) {
        this.fileName = fileName
        this.keys = keys
    }

    List<String> build() {
        List<String> lines = new ArrayList<>()
        def headerName = fileName.replaceAll("\\.", "_").replaceAll("-", "_").toUpperCase(Locale.US)
        lines.add("// Auto-Generated By Cipher.so - 林帅斌(me@idik.net)\n\n")
        lines.add("#ifndef $headerName\n")
        lines.add("#define $headerName\n\n")
        lines.add("#define LOAD_MAP(_map) \\\n")
        keys.each {
            lines.add("    _map[\"${StringUtils.md5(it.name)}\"] = \"${new String(Base64.encoder.encode(it.value.bytes))}\"; \\\n")
        }
        lines.add("\n")
        lines.add("#endif //$headerName\n\n")
        lines
    }


}