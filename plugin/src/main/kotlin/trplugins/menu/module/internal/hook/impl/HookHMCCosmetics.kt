package trplugins.menu.module.internal.hook.impl

import com.hibiscusmc.hmccosmetics.api.HMCCosmeticsAPI
import org.bukkit.inventory.ItemStack
import taboolib.library.xseries.XMaterial
import taboolib.platform.util.buildItem
import trplugins.menu.module.internal.hook.HookAbstract


/**
 * @author lilingfengdev
 * @date 2024/7/4 14:39
 */
class HookHMCCosmetics : HookAbstract() {

    private val empty = buildItem(XMaterial.BEDROCK) { name = "UNHOOKED_${super.name.uppercase()}" }

    fun getItem(id: String): ItemStack {
        if (checkHooked()) {
            return HMCCosmeticsAPI.getCosmetic(id)?.item ?: empty
        }
        return empty
    }

    fun getId(itemStack: ItemStack): String {
        if (checkHooked()) {
            return HMCCosmeticsAPI.getAllCosmetics().firstOrNull { it.item == itemStack }?.id ?: "UNKNOWN"
        }
        return "UNHOOKED"
    }

}