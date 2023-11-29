package trplugins.menu.module.internal.hook.impl

import com.francobm.magicosmetics.api.CosmeticType
import com.francobm.magicosmetics.api.MagicAPI
import org.bukkit.Bukkit.getPlayer
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
     * MagicAPI.equipCosmetic(Player player, String cosmeticId, String color/以十六进制表示的颜色，如果不想着色，请将其设置为null。/) //将饰品装备给玩家（玩家必须已解锁该饰品）。
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

    fun hasCosmetic(player: Player, cosmeticId: String): Boolean {
        if (checkHooked()) {
            return MagicAPI.hasCosmetic(player, cosmeticId)
        }
        return false
    }

    fun hasEquipCosmetic(player: Player, cosmeticType: String): Boolean {
        if (checkHooked()) {
            return MagicAPI.hasEquipCosmetic(player, cosmeticType)
        }
        return false
    }

    fun equipCosmetic(player: Player, cosmeticId: String, color: String? = null): Boolean {
        if (checkHooked()) {
            MagicAPI.EquipCosmetic(player, cosmeticId, color)
            return true
        }
        return false
    }

    fun unEquipCosmetic(player: Player, cosmeticType: String): Boolean {
        if (checkHooked()) {
            MagicAPI.UnEquipCosmetic(player, CosmeticType.valueOf(cosmeticType))
            return true
        }
        return false
    }

    fun getCosmeticId(playerName: String, type: String): String {
        if (checkHooked()) {
            return MagicAPI.getCosmeticId(playerName, type)
        }
        return ""
    }

    fun getEquipped(player: Player, cosmeticType: String): ItemStack {
        if (checkHooked()) {
            return MagicAPI.getEquipped(player, CosmeticType.valueOf(cosmeticType)) ?: notFound
        }
        return empty
    }

    fun getEquipped(player: Player, cosmeticType: CosmeticType): ItemStack {
        if (checkHooked()) {
            return MagicAPI.getEquipped(player, cosmeticType) ?: notFound
        }
        return empty
    }

    fun tintItem(item: ItemStack, colorHex: String): Boolean {
        if (checkHooked()) {
            return MagicAPI.tintItem(item, colorHex)
        }
        return false
    }

    fun spray(player: Player): Boolean {
        if (checkHooked()) {
            return MagicAPI.spray(player)
        }
        return false
    }


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

    fun getPlayerAllCosmeticsAvailable(name: String): Int? {
        return getPlayer(name)?.run { MagicAPI.getPlayerAllCosmeticsAvailable(this) }
    }

    fun getServerAllCosmeticsAvailable(): Int {
        return MagicAPI.getServerAllCosmeticsAvailable()
    }

    /**
     *  用法:
     *  source:JS:magicApi.hasCosmetic(player,cosmeticId) 根据玩家是否拥有解锁的饰品返回true或false！
     *  source:JS:magicApi.hasEquipCosmetic(player,cosmeticType) 根据玩家是否装备了指定类型的饰品返回true或false！
     *  source:JS:magicApi.equipCosmetic(player,cosmeticId,color) 将饰品装备给玩家（玩家必须已解锁该饰品）。
     *  source:JS:magicApi.unEquipCosmetic(player,cosmeticType) 解除玩家所拥有的指定类型的饰品装备！
     *  source:JS:magicApi.getCosmeticItem(id) 返回饰品物品。
     *  source:JS:magicApi.getCosmeticId(playerName,type) 返回饰品的ID。
     *  source:JS:magicApi.getEquipped(player,cosmeticType) 返回指定已装备饰品的物品。
     *  source:JS:magicApi.tintItem(item,colorHex) 如果物品染色成功则返回true，否则返回false。
     *  source:JS:magicApi.spray(player) 如果玩家正在观察的方块可以涂鸦则返回true，否则返回false。
     *  source:JS:magicApi.getPlayerAllCosmeticsAvailable(name) 返回玩家解锁的饰品数量。
     *  source:JS:magicApi.getServerAllCosmeticsAvailable() 返回服务器解锁的饰品数量。
     *
     */

}
