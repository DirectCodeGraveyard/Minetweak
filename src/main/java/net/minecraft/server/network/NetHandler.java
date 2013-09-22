package net.minecraft.server.network;

import net.minecraft.server.network.packet.*;
import org.minetweak.network.INetworkHandler;

public abstract class NetHandler implements INetworkHandler {
    /**
     * determine if it is a server handler
     */
    @Override
    public abstract boolean isServerHandler();

    /**
     * Handle Packet51MapChunk (full chunk update of blocks, metadata, light levels, and optionally biome data)
     */
    @Override
    public void handleMapChunk(Packet51MapChunk par1Packet51MapChunk) {
    }

    /**
     * Default handler called for packets that don't have their own handlers in NetServerHandler; kicks player from the
     * server.
     */
    @Override
    public void unexpectedPacket(Packet par1Packet) {
    }

    @Override
    public void handleErrorMessage(String par1Str, Object[] par2ArrayOfObj) {
    }

    @Override
    public void handleKickDisconnect(Packet255KickDisconnect par1Packet255KickDisconnect) {
        this.unexpectedPacket(par1Packet255KickDisconnect);
    }

    @Override
    public void handleLogin(Packet1Login par1Packet1Login) {
        this.unexpectedPacket(par1Packet1Login);
    }

    @Override
    public void handleFlying(Packet10Flying par1Packet10Flying) {
        this.unexpectedPacket(par1Packet10Flying);
    }

    @Override
    public void handleMultiBlockChange(Packet52MultiBlockChange par1Packet52MultiBlockChange) {
        this.unexpectedPacket(par1Packet52MultiBlockChange);
    }

    @Override
    public void handleBlockDig(Packet14BlockDig par1Packet14BlockDig) {
        this.unexpectedPacket(par1Packet14BlockDig);
    }

    @Override
    public void handleBlockChange(Packet53BlockChange par1Packet53BlockChange) {
        this.unexpectedPacket(par1Packet53BlockChange);
    }

    @Override
    public void handleNamedEntitySpawn(Packet20NamedEntitySpawn par1Packet20NamedEntitySpawn) {
        this.unexpectedPacket(par1Packet20NamedEntitySpawn);
    }

    @Override
    public void handleEntity(Packet30Entity par1Packet30Entity) {
        this.unexpectedPacket(par1Packet30Entity);
    }

    @Override
    public void handleEntityTeleport(Packet34EntityTeleport par1Packet34EntityTeleport) {
        this.unexpectedPacket(par1Packet34EntityTeleport);
    }

    @Override
    public void handlePlace(Packet15Place par1Packet15Place) {
        this.unexpectedPacket(par1Packet15Place);
    }

    @Override
    public void handleBlockItemSwitch(Packet16BlockItemSwitch par1Packet16BlockItemSwitch) {
        this.unexpectedPacket(par1Packet16BlockItemSwitch);
    }

    @Override
    public void handleDestroyEntity(Packet29DestroyEntity par1Packet29DestroyEntity) {
        this.unexpectedPacket(par1Packet29DestroyEntity);
    }

    @Override
    public void handleCollect(Packet22Collect par1Packet22Collect) {
        this.unexpectedPacket(par1Packet22Collect);
    }

    @Override
    public void handleChat(Packet3Chat par1Packet3Chat) {
        this.unexpectedPacket(par1Packet3Chat);
    }

    @Override
    public void handleVehicleSpawn(Packet23VehicleSpawn par1Packet23VehicleSpawn) {
        this.unexpectedPacket(par1Packet23VehicleSpawn);
    }

    @Override
    public void handleAnimation(Packet18Animation par1Packet18Animation) {
        this.unexpectedPacket(par1Packet18Animation);
    }

    /**
     * runs registerPacket on the given Packet19EntityAction
     */
    @Override
    public void handleEntityAction(Packet19EntityAction par1Packet19EntityAction) {
        this.unexpectedPacket(par1Packet19EntityAction);
    }

    @Override
    public void handleClientProtocol(Packet2ClientProtocol par1Packet2ClientProtocol) {
        this.unexpectedPacket(par1Packet2ClientProtocol);
    }

    @Override
    public void handleServerAuthData(Packet253ServerAuthData par1Packet253ServerAuthData) {
        this.unexpectedPacket(par1Packet253ServerAuthData);
    }

    @Override
    public void handleSharedKey(Packet252SharedKey par1Packet252SharedKey) {
        this.unexpectedPacket(par1Packet252SharedKey);
    }

    @Override
    public void handleMobSpawn(Packet24MobSpawn par1Packet24MobSpawn) {
        this.unexpectedPacket(par1Packet24MobSpawn);
    }

    @Override
    public void handleUpdateTime(Packet4UpdateTime par1Packet4UpdateTime) {
        this.unexpectedPacket(par1Packet4UpdateTime);
    }

    @Override
    public void handleSpawnPosition(Packet6SpawnPosition par1Packet6SpawnPosition) {
        this.unexpectedPacket(par1Packet6SpawnPosition);
    }

    /**
     * Packet handler
     */
    @Override
    public void handleEntityVelocity(Packet28EntityVelocity par1Packet28EntityVelocity) {
        this.unexpectedPacket(par1Packet28EntityVelocity);
    }

    /**
     * Packet handler
     */
    @Override
    public void handleEntityMetadata(Packet40EntityMetadata par1Packet40EntityMetadata) {
        this.unexpectedPacket(par1Packet40EntityMetadata);
    }

    /**
     * Packet handler
     */
    @Override
    public void handleAttachEntity(Packet39AttachEntity par1Packet39AttachEntity) {
        this.unexpectedPacket(par1Packet39AttachEntity);
    }

    @Override
    public void handleUseEntity(Packet7UseEntity par1Packet7UseEntity) {
        this.unexpectedPacket(par1Packet7UseEntity);
    }

    /**
     * Packet handler
     */
    @Override
    public void handleEntityStatus(Packet38EntityStatus par1Packet38EntityStatus) {
        this.unexpectedPacket(par1Packet38EntityStatus);
    }

    /**
     * Recieves player health from the server and then proceeds to set it locally on the client.
     */
    @Override
    public void handleUpdateHealth(Packet8UpdateHealth par1Packet8UpdateHealth) {
        this.unexpectedPacket(par1Packet8UpdateHealth);
    }

    /**
     * respawns the player
     */
    @Override
    public void handleRespawn(Packet9Respawn par1Packet9Respawn) {
        this.unexpectedPacket(par1Packet9Respawn);
    }

    @Override
    public void handleExplosion(Packet60Explosion par1Packet60Explosion) {
        this.unexpectedPacket(par1Packet60Explosion);
    }

    @Override
    public void handleOpenWindow(Packet100OpenWindow par1Packet100OpenWindow) {
        this.unexpectedPacket(par1Packet100OpenWindow);
    }

    @Override
    public void handleCloseWindow(Packet101CloseWindow par1Packet101CloseWindow) {
        this.unexpectedPacket(par1Packet101CloseWindow);
    }

    @Override
    public void handleWindowClick(Packet102WindowClick par1Packet102WindowClick) {
        this.unexpectedPacket(par1Packet102WindowClick);
    }

    @Override
    public void handleSetSlot(Packet103SetSlot par1Packet103SetSlot) {
        this.unexpectedPacket(par1Packet103SetSlot);
    }

    @Override
    public void handleWindowItems(Packet104WindowItems par1Packet104WindowItems) {
        this.unexpectedPacket(par1Packet104WindowItems);
    }

    /**
     * Updates Client side signs
     */
    @Override
    public void handleUpdateSign(Packet130UpdateSign par1Packet130UpdateSign) {
        this.unexpectedPacket(par1Packet130UpdateSign);
    }

    @Override
    public void handleUpdateProgressbar(Packet105UpdateProgressbar par1Packet105UpdateProgressbar) {
        this.unexpectedPacket(par1Packet105UpdateProgressbar);
    }

    @Override
    public void handlePlayerInventory(Packet5PlayerInventory par1Packet5PlayerInventory) {
        this.unexpectedPacket(par1Packet5PlayerInventory);
    }

    @Override
    public void handleTransaction(Packet106Transaction par1Packet106Transaction) {
        this.unexpectedPacket(par1Packet106Transaction);
    }

    @Override
    public void handleEntityPainting(Packet25EntityPainting par1Packet25EntityPainting) {

    }

    @Override
    public void handleBlockEvent(Packet54PlayNoteBlock par1Packet54PlayNoteBlock) {

    }

    @Override
    public void handleStatistic(Packet200Statistic par1Packet200Statistic) {

    }

    @Override
    public void handleSleep(Packet17Sleep par1Packet17Sleep) {

    }

    @Override
    public void handlePlayerInput(Packet27PlayerInput par1Packet27PlayerInput) {

    }

    @Override
    public void handleGameEvent(Packet70GameEvent par1Packet70GameEvent) {

    }

    @Override
    public void handleWeather(Packet71Weather par1Packet71Weather) {

    }

    @Override
    public void handleMapData(Packet131MapData par1Packet131MapData) {

    }

    @Override
    public void handleDoorChange(Packet61DoorChange par1Packet61DoorChange) {

    }

    @Override
    public void handleServerPing(Packet254ServerPing par1Packet254ServerPing) {

    }

    @Override
    public void handleEntityEffect(Packet41EntityEffect par1Packet41EntityEffect) {

    }

    @Override
    public void handleRemoveEntityEffect(Packet42RemoveEntityEffect par1Packet42RemoveEntityEffect) {

    }

    @Override
    public void handlePlayerInfo(Packet201PlayerInfo par1Packet201PlayerInfo) {

    }

    @Override
    public void handleKeepAlive(Packet0KeepAlive par1Packet0KeepAlive) {

    }

    @Override
    public void handleExperience(Packet43Experience par1Packet43Experience) {

    }

    @Override
    public void handleCreativeSetSlot(Packet107CreativeSetSlot par1Packet107CreativeSetSlot) {

    }

    @Override
    public void handleEntityExpOrb(Packet26EntityExpOrb par1Packet26EntityExpOrb) {

    }

    @Override
    public void handleEnchantItem(Packet108EnchantItem par1Packet108EnchantItem) {

    }

    @Override
    public void handleCustomPayload(Packet250CustomPayload par1Packet250CustomPayload) {

    }

    @Override
    public void handleEntityHeadRotation(Packet35EntityHeadRotation par1Packet35EntityHeadRotation) {

    }

    @Override
    public void handleTileEntityData(Packet132TileEntityData par1Packet132TileEntityData) {

    }

    @Override
    public void handlePlayerAbilities(Packet202PlayerAbilities par1Packet202PlayerAbilities) {

    }

    @Override
    public void handleAutoComplete(Packet203AutoComplete par1Packet203AutoComplete) {

    }

    @Override
    public void handleClientInfo(Packet204ClientInfo par1Packet204ClientInfo) {

    }

    @Override
    public void handleLevelSound(Packet62LevelSound par1Packet62LevelSound) {

    }

    @Override
    public void handleBlockDestroy(Packet55BlockDestroy par1Packet55BlockDestroy) {

    }

    @Override
    public void handleClientCommand(Packet205ClientCommand par1Packet205ClientCommand) {

    }

    @Override
    public void handleMapChunks(Packet56MapChunks par1Packet56MapChunks) {

    }

    @Override
    public boolean canProcessPacketsAsync() {
        return false;
    }

    @Override
    public void handleSetObjective(Packet206SetObjective par1Packet206SetObjective) {

    }

    @Override
    public void handleSetScore(Packet207SetScore par1Packet207SetScore) {

    }

    @Override
    public void handleSetDisplayObjective(Packet208SetDisplayObjective par1Packet208SetDisplayObjective) {

    }

    @Override
    public void handleSetPlayerTeam(Packet209SetPlayerTeam par1Packet209SetPlayerTeam) {

    }

    @Override
    public void handleWorldParticles(Packet63WorldParticles par1Packet63WorldParticles) {

    }

    @Override
    public void handleUpdateAttributes(Packet44UpdateAttributes par1Packet44UpdateAttributes) {

    }

    @Override
    public void handleTileEditorOpen(Packet133TileEditorOpen par1Packet133TileEditorOpen) {

    }

    @Override
    public boolean func_142032_c() {
        return false;
    }

    public void func_110773_a(Packet44UpdateAttributes par1Packet44UpdateAttributes) {
        this.unexpectedPacket(par1Packet44UpdateAttributes);
    }

    public void func_142031_a(Packet133TileEditorOpen par1Packet133TileEditorOpen) {
        this.unexpectedPacket(par1Packet133TileEditorOpen);
    }
}
