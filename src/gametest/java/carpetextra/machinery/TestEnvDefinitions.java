package carpetextra.machinery;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.gametest.framework.TestEnvironmentDefinition;
import net.minecraft.server.level.ServerLevel;
import carpet.CarpetServer;
import carpet.api.settings.InvalidRuleValueException;
import carpet.api.settings.RuleHelper;

class TestEnvDefinitions {
    public static void runRegistration() {
        Registry.register(BuiltInRegistries.TEST_ENVIRONMENT_DEFINITION_TYPE, "carpet:rule", CarpetRule.CODEC);
    }
    
    public record CarpetRule(String key, String value) implements TestEnvironmentDefinition {
        static final MapCodec<CarpetRule> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
                    Codec.STRING.fieldOf("key").forGetter(CarpetRule::key),
                    Codec.STRING.fieldOf("value").forGetter(CarpetRule::value)
                ).apply(i, CarpetRule::new));

        @Override
        public void setup(ServerLevel world) {
            try {
                CarpetServer.settingsManager.getCarpetRule(key).set(world.getServer().createCommandSourceStack(), value);
            } catch (InvalidRuleValueException e) {
                throw new IllegalArgumentException(e);
            }
        }

        @Override
        public MapCodec<? extends TestEnvironmentDefinition> codec() {
            return CODEC;
        }
        @Override
        public void teardown(ServerLevel world) {
            RuleHelper.resetToDefault(CarpetServer.settingsManager.getCarpetRule(key), world.getServer().createCommandSourceStack());
        }
    }
}
