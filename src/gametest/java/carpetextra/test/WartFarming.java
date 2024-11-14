package carpetextra.test;

import static net.minecraft.block.NetherWartBlock.*;

import java.util.Set;

import carpetextra.CarpetExtraSettings;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.test.AfterBatch;
import net.minecraft.test.BeforeBatch;
import net.minecraft.test.GameTest;
import net.minecraft.test.TestContext;
import net.minecraft.util.math.BlockPos;

public class WartFarming {
    private static final String STRUCTURE = "carpet-extra:wartbase";
    private static final String BATCH = "wartfarming";

    BlockPos soulSand = new BlockPos(0, 0, 0);
    BlockPos lapis = new BlockPos(3, 1, 0);

    @BeforeBatch(batchId = BATCH)
    public void before(ServerWorld world) {
        CarpetExtraSettings.clericsFarmWarts = true;
        world.setTimeOfDay(2500);
    }
    @AfterBatch(batchId = BATCH)
    public void after(ServerWorld world) {
        CarpetExtraSettings.clericsFarmWarts = false;
    }
    
    @GameTest(batchId = BATCH, templateName = STRUCTURE, tickLimit = 1500)
    public void placesWarts(TestContext ctx) {
        ctx.spawnItem(Items.NETHER_WART, lapis);
        ctx.spawnEntity(EntityType.VILLAGER, lapis);
        
        ctx.addInstantFinalTask(() -> {
            ctx.expectBlock(Blocks.NETHER_WART, soulSand.up());
        });
    }
    
    @GameTest(batchId = BATCH, templateName = STRUCTURE, tickLimit = 1500)
    public void collectsWarts(TestContext ctx) {
        ctx.setBlockState(soulSand.up(), Blocks.NETHER_WART.getDefaultState().with(AGE, MAX_AGE));
        VillagerEntity villager =  ctx.spawnEntity(EntityType.VILLAGER, lapis);
        
        ctx.addInstantFinalTask(() -> {
            ctx.checkBlockState(soulSand.up(),
                    state -> state.getBlock() != Blocks.NETHER_WART || state.get(AGE) != MAX_AGE, 
                    () -> "Wart not collected");
            ctx.assertTrue(villager.getInventory().containsAny(Set.of(Items.NETHER_WART)), "Villager didn't get warts");
        });
    }
    
    /* Too slow
    @GameTest(batchId = BATCH, templateName = STRUCTURE, tickLimit = 1500)
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
