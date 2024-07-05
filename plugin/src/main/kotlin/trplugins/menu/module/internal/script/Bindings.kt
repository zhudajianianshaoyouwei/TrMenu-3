package trplugins.menu.module.internal.script

import trplugins.menu.TrMenu
import trplugins.menu.module.internal.script.jexl.JexlAgent
import trplugins.menu.module.internal.script.js.JavaScriptAgent

object Bindings {
    var exportHook = true;
    fun load() {
        val bindings = TrMenu.SETTINGS.getConfigurationSection("Bindings")
        exportHook = bindings?.getBoolean("Export-Hook-Plugin", true) ?: true

        val bindingMap = bindings?.getConfigurationSection("Binding-Map") ?: return
        bindingMap.getKeys(false).forEach { key ->
            val value = bindingMap.getString(key)
            value?.let {
                val clazz = Class.forName(value)
                JavaScriptAgent.putBinding(key, clazz)
                JexlAgent.putBinding(key, clazz)
            }
        }
    }
}