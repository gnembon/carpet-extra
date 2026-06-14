package carpetextra.mixins.griefing;

import carpetextra.CarpetExtraSettings;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FarmlandBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FarmlandBlock.class)
public abstract class FarmlandBlockMixin extends Block {
    public FarmlandBlockMixin(Properties properties) {
        super(properties);
    }

    @Redirect(method = "fallOn", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/gamerules/GameRules;get(Lnet/minecraft/world/level/gamerules/GameRule;)Ljava/lang/Object;"))
    private Object livingEntity(GameRules instance, GameRule gameRule) {
        return instance.get(GameRules.MOB_GRIEFING) && !CarpetExtraSettings.disableFarmlandMobTrampling;
    }

    @Inject(method = "fallOn", at = @At(value = "HEAD"), cancellable = true)
    private void player(Level level, BlockState state, BlockPos pos, Entity entity, double fallDistance, CallbackInfo ci) {
        if (level instanceof ServerLevel && entity instanceof Player && CarpetExtraSettings.disableFarmlandPlayerTrampling) {
            super.fallOn(level, state, pos, entity, fallDistance);
            ci.cancel();
        }
    }
}
