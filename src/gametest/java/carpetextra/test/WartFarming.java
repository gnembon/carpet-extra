package carpetextra.test;


import java.util.Set;

import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.NetherWartBlock;

public class WartFarming {
    private static final String STRUCTURE = "carpet-extra:wartbase";
    private static final String WART_FARMING_ENABLED = "carpet-extra:warts";
    
    BlockPos soulSand = new BlockPos(0, 0, 0);
    BlockPos lapis = new BlockPos(3, 1, 0);
    
    @GameTest(environment = WART_FARMING_ENABLED, structure = STRUCTURE, maxTicks = 1500)
    public void placesWarts(GameTestHelper ctx) {
        ctx.spawnItem(Items.NETHER_WART, lapis);
        ctx.spawn(EntityType.VILLAGER, lapis);
        
        ctx.succeedWhen(() -> {
            ctx.assertBlockPresent(Blocks.NETHER_WART, soulSand.above());
        });
    }
    
    @GameTest(environment = WART_FARMING_ENABLED, structure = STRUCTURE, maxTicks = 1500)
    public void collectsWarts(GameTestHelper ctx) {
        ctx.setBlock(soulSand.above(), Blocks.NETHER_WART.defaultBlockState().setValue(NetherWartBlock.AGE, NetherWartBlock.MAX_AGE));
        Villager villager =  ctx.spawn(EntityType.VILLAGER, lapis);
        
        ctx.succeedWhen(() -> {
            ctx.assertBlockState(soulSand.above(),
                    state -> state.getBlock() != Blocks.NETHER_WART || state.getValue(NetherWartBlock.AGE) != NetherWartBlock.MAX_AGE,
                    (st) -> Component.literal("Wart not collected"));
            ctx.assertTrue(villager.getInventory().hasAnyOf(Set.of(Items.NETHER_WART)), Component.literal("Villager didn't get warts"));
        });
    }
    
    @GameTest(/* no env */ structure = STRUCTURE, maxTicks = 200)
    public void doesntPickupWartsWithoutRule(GameTestHelper ctx) {
        ctx.spawnItem(Items.NETHER_WART, lapis);
        ctx.spawn(EntityType.VILLAGER, lapis);
        
        ctx.onEachTick(() -> {
            ctx.assertItemEntityPresent(Items.NETHER_WART);
        });
        ctx.runAtTickTime(200, ctx::succeed);
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
