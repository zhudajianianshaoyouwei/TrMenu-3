package trplugins.menu.module.internal.hook.impl

import de.tr7zw.nbtapi.NBT
import org.bukkit.inventory.ItemStack

object HookNBTAPI{

    fun toJson(item: ItemStack): String {
        return NBT.itemStackToNBT(item).toString()
    }

    fun fromJson(json: String): ItemStack {
        return NBT.itemStackFromNBT(NBT.parseNBT(json)) ?: error("Failed to parse item from json: $json")
    }

}