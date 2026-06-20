package carpetextra.machinery;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import carpet.CarpetServer;
import carpet.api.settings.InvalidRuleValueException;
import carpet.api.settings.RuleHelper;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.test.TestEnvironmentDefinition;

class TestEnvDefinitions {
    public static void runRegistration() {
        Registry.register(Registries.TEST_ENVIRONMENT_DEFINITION_TYPE, "carpet:rule", CarpetRule.CODEC);
    }
    // TODO maybe do some push/pop thing with the TestData (here just Void)? if it's even for that
    public record CarpetRule(String key, String value) implements TestEnvironmentDefinition<Void> {
        static final MapCodec<CarpetRule> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
                    Codec.STRING.fieldOf("key").forGetter(CarpetRule::key),
                    Codec.STRING.fieldOf("value").forGetter(CarpetRule::value)
                ).apply(i, CarpetRule::new));

        @Override
        public Void setup(ServerWorld world) {
            try {
                CarpetServer.settingsManager.getCarpetRule(key).set(world.getServer().getCommandSource(), value);
                return null;
            } catch (InvalidRuleValueException e) {
                throw new IllegalArgumentException(e);
            }
        }

        @Override
        public MapCodec<? extends TestEnvironmentDefinition<Void>> getCodec() {
            return CODEC;
        }
        @Override
        public void teardown(ServerWorld world, Void v) {
            RuleHelper.resetToDefault(CarpetServer.settingsManager.getCarpetRule(key), world.getServer().getCommandSource());
        }
    }
}
