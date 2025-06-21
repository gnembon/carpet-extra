package carpetextra.test;

import static net.minecraft.block.NetherWartBlock.*;

import java.util.Set;

import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.item.Items;
import net.minecraft.test.TestContext;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

public class WartFarming {
    private static final String STRUCTURE = "carpet-extra:wartbase";
    private static final String WART_FARMING_ENABLED = "carpet-extra:warts";
    
    BlockPos soulSand = new BlockPos(0, 0, 0);
    BlockPos lapis = new BlockPos(3, 1, 0);
    
    @GameTest(environment = WART_FARMING_ENABLED, structure = STRUCTURE, maxTicks = 1500)
    public void placesWarts(TestContext ctx) {
        ctx.spawnItem(Items.NETHER_WART, lapis);
        ctx.spawnEntity(EntityType.VILLAGER, lapis);
        
        ctx.addInstantFinalTask(() -> {
            ctx.expectBlock(Blocks.NETHER_WART, soulSand.up());
        });
    }
    
    @GameTest(environment = WART_FARMING_ENABLED, structure = STRUCTURE, maxTicks = 1500)
    public void collectsWarts(TestContext ctx) {
        ctx.setBlockState(soulSand.up(), Blocks.NETHER_WART.getDefaultState().with(AGE, MAX_AGE));
        VillagerEntity villager =  ctx.spawnEntity(EntityType.VILLAGER, lapis);
        
        ctx.addInstantFinalTask(() -> {
            ctx.checkBlockState(soulSand.up(),
                    state -> state.getBlock() != Blocks.NETHER_WART || state.get(AGE) != MAX_AGE, 
                    (st) -> Text.literal("Wart not collected"));
            ctx.assertTrue(villager.getInventory().containsAny(Set.of(Items.NETHER_WART)), Text.literal("Villager didn't get warts"));
        });
    }
    
    @GameTest(/* no env */ structure = STRUCTURE, maxTicks = 200)
    public void doesntPickupWartsWithoutRule(TestContext ctx) {
        ctx.spawnItem(Items.NETHER_WART, lapis);
        ctx.spawnEntity(EntityType.VILLAGER, lapis);
        
        ctx.runAtEveryTick(() -> {
            ctx.expectItem(Items.NETHER_WART);
        });
        ctx.runAtTick(200, ctx::complete);
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
