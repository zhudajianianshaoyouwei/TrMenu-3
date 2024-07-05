package trplugins.menu.module.internal.script

import trplugins.menu.TrMenu

object Bindings {
    var exportHook = true;
    var mozillaCompat = true
    var bootloaderCode="";
    fun load() {
        val bindings = TrMenu.SETTINGS.getConfigurationSection("Bindings")
        exportHook = bindings?.getBoolean("Export-Hook-Plugin", true) ?: true
        val builder = StringBuilder()
        mozillaCompat = bindings?.getBoolean("Mozilla-Compat", true) ?: true
        if (mozillaCompat) {
            builder.append("load(\"nashorn:mozilla_compat.js\");") // 加载 Nashorn 扩展
        }
        bindings?.getConfigurationSection("Binding-Map")?.let {
            it.getKeys(false).forEach { key ->
                val value = it.getString(key)
                builder.append("var $key = Java.type('$value');") // 生成 JS 引导代码
            }
        }
        bootloaderCode = builder.toString()
    }
}