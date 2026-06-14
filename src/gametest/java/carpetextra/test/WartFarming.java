package carpetextra.test;

<<<<<<< HEAD
import static net.minecraft.world.level.block.NetherWartBlock.*;
=======
import static net.minecraft.block.NetherWartBlock.*;
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf

import java.util.Set;

import net.fabricmc.fabric.api.gametest.v1.GameTest;
<<<<<<< HEAD
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
=======
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.item.Items;
import net.minecraft.test.TestContext;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf

public class WartFarming {
    private static final String STRUCTURE = "carpet-extra:wartbase";
    private static final String WART_FARMING_ENABLED = "carpet-extra:warts";
    
    BlockPos soulSand = new BlockPos(0, 0, 0);
    BlockPos lapis = new BlockPos(3, 1, 0);
    
    @GameTest(environment = WART_FARMING_ENABLED, structure = STRUCTURE, maxTicks = 1500)
<<<<<<< HEAD
    public void placesWarts(GameTestHelper ctx) {
        ctx.spawnItem(Items.NETHER_WART, lapis);
        ctx.spawn(EntityType.VILLAGER, lapis);
        
        ctx.succeedWhen(() -> {
            ctx.assertBlockPresent(Blocks.NETHER_WART, soulSand.above());
=======
    public void placesWarts(TestContext ctx) {
        ctx.spawnItem(Items.NETHER_WART, lapis);
        ctx.spawnEntity(EntityType.VILLAGER, lapis);
        
        ctx.addInstantFinalTask(() -> {
            ctx.expectBlock(Blocks.NETHER_WART, soulSand.up());
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
        });
    }
    
    @GameTest(environment = WART_FARMING_ENABLED, structure = STRUCTURE, maxTicks = 1500)
<<<<<<< HEAD
    public void collectsWarts(GameTestHelper ctx) {
        ctx.setBlock(soulSand.above(), Blocks.NETHER_WART.defaultBlockState().setValue(AGE, MAX_AGE));
        Villager villager =  ctx.spawn(EntityType.VILLAGER, lapis);
        
        ctx.succeedWhen(() -> {
            ctx.assertBlockState(soulSand.above(),
                    state -> state.getBlock() != Blocks.NETHER_WART || state.getValue(AGE) != MAX_AGE, 
                    (st) -> Component.literal("Wart not collected"));
            ctx.assertTrue(villager.getInventory().hasAnyOf(Set.of(Items.NETHER_WART)), Component.literal("Villager didn't get warts"));
=======
    public void collectsWarts(TestContext ctx) {
        ctx.setBlockState(soulSand.up(), Blocks.NETHER_WART.getDefaultState().with(AGE, MAX_AGE));
        VillagerEntity villager =  ctx.spawnEntity(EntityType.VILLAGER, lapis);
        
        ctx.addInstantFinalTask(() -> {
            ctx.checkBlockState(soulSand.up(),
                    state -> state.getBlock() != Blocks.NETHER_WART || state.get(AGE) != MAX_AGE, 
                    (st) -> Text.literal("Wart not collected"));
            ctx.assertTrue(villager.getInventory().containsAny(Set.of(Items.NETHER_WART)), Text.literal("Villager didn't get warts"));
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
        });
    }
    
    @GameTest(/* no env */ structure = STRUCTURE, maxTicks = 200)
<<<<<<< HEAD
    public void doesntPickupWartsWithoutRule(GameTestHelper ctx) {
        ctx.spawnItem(Items.NETHER_WART, lapis);
        ctx.spawn(EntityType.VILLAGER, lapis);
        
        ctx.failIfEver(() -> {
            ctx.assertItemEntityPresent(Items.NETHER_WART);
        });
        ctx.runAtTickTime(200, ctx::succeed);
=======
    public void doesntPickupWartsWithoutRule(TestContext ctx) {
        ctx.spawnItem(Items.NETHER_WART, lapis);
        ctx.spawnEntity(EntityType.VILLAGER, lapis);
        
        ctx.runAtEveryTick(() -> {
            ctx.expectItem(Items.NETHER_WART);
        });
        ctx.runAtTick(200, ctx::complete);
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
    }
    
    /* Too slow
    @GameTest(environment = ENV, structure = STRUCTURE, maxTicks = 1500)
    public void doesntCollectNonGrown(TestContext ctx) {
        ctx.setBlockState(soulSand.up(), Blocks.NETHER_WART.getDefaultState().with(AGE, 0));
        ctx.spawnEntity(EntityType.VILLAGER, lapis);
        
        ctx.runAtEveryTick(() -> {
            ctx.dontExpectBlock(Blocks.AIR, soulSand.up());
        });
        ctx.runAtTick(1500, () -> ctx.complete());
    }
    */
}
