package trplugins.menu.module.internal.hook.impl

import ink.ptms.um.Mythic
import org.bukkit.inventory.ItemStack
import taboolib.library.xseries.XMaterial
import taboolib.platform.util.buildItem
import trplugins.menu.module.internal.hook.HookAbstract

/**
 * @author Rubenicos
 * @date 2024/5/7 08:30
 */
class HookMythicMobs : HookAbstract() {

    private val empty = buildItem(XMaterial.BEDROCK) { name = "UNHOOKED_${super.name.uppercase()}" }

    fun getItem(material: String): ItemStack {
        return Mythic.API.getItemStack(material) ?: empty
    }

    fun getID(id: ItemStack): String {
        if (checkHooked()) {
            return Mythic.API.getItemId(id) ?: "UNKNOWN"
        }
        return "UNHOOKED"
    }

}