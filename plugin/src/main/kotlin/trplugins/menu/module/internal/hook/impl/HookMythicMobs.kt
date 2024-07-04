package trplugins.menu.module.internal.hook.impl

import ink.ptms.um.Mythic
import org.bukkit.inventory.ItemStack
import taboolib.library.xseries.XMaterial
import taboolib.platform.util.buildItem
import trplugins.menu.module.internal.hook.HookAbstract

/**
 * @author lilingfengdev
 * @date 2024/7/4 11:04
 */
class HookMythicMobs : HookAbstract() {

    private val empty = buildItem(XMaterial.BEDROCK) { name = "UNHOOKED_${super.name.uppercase()}" }

    fun getItem(material: String): ItemStack {
        if (checkHooked()) return Mythic.API.getItemStack(material) ?: empty
        return empty
    }

    fun getID(id: ItemStack): String {
        if (checkHooked()) {
            return Mythic.API.getItemId(id) ?: "UNKNOWN"
        }
        return "UNHOOKED"
    }

}