package trplugins.menu.module.internal.hook.impl

import org.bukkit.inventory.ItemStack
import com.willfp.ecoitems.items.EcoItems
import taboolib.library.xseries.XMaterial
import taboolib.platform.util.buildItem
import trplugins.menu.module.internal.hook.HookAbstract

/**
 * @author lilingfengdev
 * @date 2024/7/3 10:30
 */
class HookEcoItems : HookAbstract() {

    private val empty = buildItem(XMaterial.BEDROCK) { name = "UNHOOKED_${super.name.uppercase()}" }

    fun getItem(id: String): ItemStack {
        if (checkHooked()) {
            return EcoItems.getByID(id)?.itemStack ?:empty
        }
        return empty
    }

    fun getId(itemStack: ItemStack): String {
        if (checkHooked()) {
            for (item in EcoItems.values()) {
                if (item.itemStack == itemStack) {
                    return item.id.key
                }
            }
            return "UNKNOWN"
        }
        return "UNHOOKED"
    }

}