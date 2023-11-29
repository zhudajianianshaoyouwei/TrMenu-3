package trplugins.menu.module.internal.hook.impl

import com.francobm.magicosmetics.api.MagicAPI
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import taboolib.library.xseries.XMaterial
import taboolib.platform.util.buildItem
import trplugins.menu.module.internal.hook.HookAbstract

class HookMagicCosmetics : HookAbstract() {

    private val empty = buildItem(XMaterial.BEDROCK) { name = "UNHOOKED_${super.name.uppercase()}" }

    private val notFound = buildItem(XMaterial.BEDROCK) { name = "NOT_FOUND_${super.name.uppercase()}" }

    override fun getPluginName(): String {
        return "MagicCosmetics"
    }

    /**
     * MagicAPI.hasCosmetic(Player player, String cosmeticId) //根据玩家是否拥有解锁的饰品返回true或false！
     * MagicAPI.hasEquipCosmetic(Player player, CosmeticType cosmeticType) //根据玩家是否装备了指定类型的饰品返回true或false！
     * MagicAPI.hasEquipCosmetic(Entity entity, CosmeticType cosmeticType) //根据实体是否装备了指定类型的饰品返回true或false！
     * MagicAPI.EquipCosmetic(Player player, String cosmeticId, String color/以十六进制表示的颜色，如果不想着色，请将其设置为null。/) //将饰品装备给玩家（玩家必须已解锁该饰品）。
     * MagicAPI.EquipCosmetic(Entity entity, String cosmeticId, String color/以十六进制表示的颜色，如果不想着色，请将其设置为null。/) //将饰品装备给实体。
     * MagicAPI.UnEquipCosmetic(Player player, CosmeticType cosmeticType) //解除玩家所拥有的指定类型的饰品装备！
     * MagicAPI.UnEquipCosmetic(Entity entity, CosmeticType cosmeticType) //解除实体所拥有的指定类型的饰品装备！
     * MagicAPI.getCosmeticItem(String id) //返回饰品物品。
     * MagicAPI.getCosmeticId(String playerName, String type) //返回饰品的ID。
     * MagicAPI.getEquipped(String playerName, String type) //返回指定已装备饰品的物品。
     * MagicAPI.getEquipped(OfflinePlayer offlinePlayer, CosmeticType cosmeticType) //返回指定已装备饰品的物品。
     * MagicAPI.tintItem(ItemStack item, String colorHex) //如果物品染色成功则返回true，否则返回false。
     * MagicAPI.spray(Player player) //如果玩家正在观察的方块可以涂鸦则返回true，否则返回false。
     */

    fun getEquipped(id: String, player: Player? = null): ItemStack {
        if (checkHooked()) {
            return MagicAPI.getEquipped(player?.name ?: return empty, id) ?: notFound
        }
        return empty
    }

    fun getCosmeticItem(id: String): ItemStack {
        if (checkHooked()) {
            return MagicAPI.getCosmeticItem(id) ?: notFound
        }
        return empty
    }

}
