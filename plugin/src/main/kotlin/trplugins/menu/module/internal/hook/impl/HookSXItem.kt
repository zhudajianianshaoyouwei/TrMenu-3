package trplugins.menu.module.internal.hook.impl

import github.saukiya.sxitem.SXItem
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import taboolib.library.xseries.XMaterial
import taboolib.platform.util.buildItem
import trplugins.menu.module.internal.hook.HookAbstract

class HookSXItem : HookAbstract() {

    private val empty = buildItem(XMaterial.BEDROCK) { name = "UNHOOKED_${super.name.uppercase()}" }

    private val notFound = buildItem(XMaterial.BEDROCK) { name = "NOT_FOUND_${super.name.uppercase()}" }

    override fun getPluginName(): String {
        return "SX-Item"
    }

    fun getItem(id: String, player: Player? = null): ItemStack {
        if (checkHooked()) {
            return player?.let {
                SXItem.getItemManager().getItem(id, it) ?: notFound
            } ?: notFound
        }
        return empty
    }

}