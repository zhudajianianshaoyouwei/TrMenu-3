package trplugins.menu.module.internal.hook.impl

import org.bukkit.inventory.ItemStack
import pku.yim.magicgem.gem.GemManager
import taboolib.library.xseries.XMaterial
import taboolib.platform.util.buildItem
import trplugins.menu.module.internal.hook.HookAbstract

/**
 * @author lilingfengdev
 * @date 2024/7/2 9:17
 */
class HookMagicGem : HookAbstract() {

    private val empty = buildItem(XMaterial.BEDROCK) { name = "UNHOOKED_${super.name.uppercase()}" }

    fun getItem(id: String): ItemStack {
        if (checkHooked()) {
            return GemManager.getGemByName(id)?.realGem?:empty
        }
        return empty
    }

    fun getId(itemStack: ItemStack): String {
        if (checkHooked()) {
            return GemManager.getGem(itemStack).name ?: "UNKNOWN"
        }
        return "UNHOOKED"
    }

}