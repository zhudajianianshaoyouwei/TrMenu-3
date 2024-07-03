package trplugins.menu.module.internal.script

import com.francobm.magicosmetics.api.CosmeticType
import com.francobm.magicosmetics.api.MagicAPI
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.common.platform.function.console
import taboolib.library.xseries.XMaterial
import taboolib.module.lang.sendLang
import taboolib.platform.util.buildItem
import trplugins.menu.module.internal.script.jexl.JexlAgent
import trplugins.menu.module.internal.script.js.JavaScriptAgent

object MagicAPI {

    private val empty = buildItem(XMaterial.BEDROCK) { name = "UNHOOKED_Magic" }

    private val notFound = buildItem(XMaterial.BEDROCK) { name = "NOT_FOUND_Magic" }

    @Awake(LifeCycle.ACTIVE)
    fun init() {
        if (Bukkit.getPluginManager().getPlugin("MagicCosmetics") != null) {
            JavaScriptAgent.putBinding("magicApi", this)
            JexlAgent.putBinding("magicApi", this)
            console().sendLang("Plugin-Dependency-Hooked", "MagicCosmetics")
        }
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
        return MagicAPI.hasCosmetic(player, cosmeticId)
    }

    fun hasEquipCosmetic(player: Player, cosmeticType: String): Boolean {
        return MagicAPI.hasEquipCosmetic(player, cosmeticType)
    }

    fun equipCosmetic(player: Player, cosmeticId: String, color: String? = null): Boolean {
        MagicAPI.EquipCosmetic(player, cosmeticId, color)
        return true
    }

    fun unEquipCosmetic(player: Player, cosmeticType: String): Boolean {
        MagicAPI.UnEquipCosmetic(player, CosmeticType.valueOf(cosmeticType))
        return true
    }

    fun getCosmeticId(playerName: String, type: String): String {
        return MagicAPI.getCosmeticId(playerName, type)
    }

    fun getEquipped(player: Player, cosmeticType: String): ItemStack {
        return MagicAPI.getEquipped(player, CosmeticType.valueOf(cosmeticType)) ?: notFound
    }

    fun getEquipped(player: Player, cosmeticType: CosmeticType): ItemStack {
        return MagicAPI.getEquipped(player, cosmeticType) ?: notFound
    }

    fun tintItem(item: ItemStack, colorHex: String): Boolean {
        return MagicAPI.tintItem(item, colorHex)
    }

    fun spray(player: Player): Boolean {
        return MagicAPI.spray(player)
    }


    fun getEquipped(id: String, player: Player? = null): ItemStack {
        return MagicAPI.getEquipped(player?.name ?: return empty, id) ?: notFound
    }

    fun getCosmeticItem(id: String): ItemStack {
        return MagicAPI.getCosmeticItem(id) ?: notFound
    }

    fun getPlayerAllCosmeticsAvailable(name: String): Int? {
        return Bukkit.getPlayer(name)?.run { MagicAPI.getPlayerAllCosmeticsAvailable(this) }
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
