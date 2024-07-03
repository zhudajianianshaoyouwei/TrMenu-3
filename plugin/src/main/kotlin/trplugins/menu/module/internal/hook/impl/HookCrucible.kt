package trplugins.menu.module.internal.hook.impl

import io.lumine.mythic.bukkit.BukkitAdapter
import io.lumine.mythiccrucible.MythicCrucible
import io.lumine.mythiccrucible.items.CrucibleItem
import org.bukkit.inventory.ItemStack
import taboolib.library.xseries.XMaterial
import taboolib.platform.util.buildItem
import trplugins.menu.module.internal.hook.HookAbstract


/**
 * @author lilingfengdev
 * @date 2024/7/3 10:30
 */
class HookCrucible : HookAbstract() {

    private val empty = buildItem(XMaterial.BEDROCK) { name = "UNHOOKED_${super.name.uppercase()}" }
    private val itemManager = MythicCrucible.inst().itemManager;

    fun getItem(id: String): ItemStack {
        if (checkHooked()) {
            return itemManager.getItem(id)?.map { crucibleItem ->
                BukkitAdapter.adapt(
                    crucibleItem.mythicItem.generateItemStack(1)
                )
            }?.orElse(null) ?: empty;
        }
        return empty
    }

    fun getId(itemStack: ItemStack): String {
        if (checkHooked()) {
            return itemManager.getItem(itemStack).map(CrucibleItem::getInternalName).orElse(null)
        }
        return "UNHOOKED"
    }

}