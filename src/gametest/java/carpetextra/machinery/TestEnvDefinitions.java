package carpetextra.machinery;

import carpet.api.settings.CarpetRule;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import carpet.CarpetServer;
import carpet.api.settings.InvalidRuleValueException;
import carpet.api.settings.RuleHelper;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Registry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.gametest.framework.TestEnvironmentDefinition;

// @TODO Need review v26.1.2: only makes the original code compatible with the new TestEnvironmentDefinition interface
class TestEnvDefinitions {
    public static void runRegistration() {
        Registry.register(BuiltInRegistries.TEST_ENVIRONMENT_DEFINITION_TYPE, "carpet:rule", CarpetRule.CODEC);
    }
    
    public record CarpetRule(String key, String value) implements TestEnvironmentDefinition<Object> {
        static final MapCodec<CarpetRule> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
                    Codec.STRING.fieldOf("key").forGetter(CarpetRule::key),
                    Codec.STRING.fieldOf("value").forGetter(CarpetRule::value)
                ).apply(i, CarpetRule::new));

        @Override
        public Object setup(ServerLevel world) {
            try {
                CarpetServer.settingsManager.getCarpetRule(key).set(world.getServer().createCommandSourceStack(), value);
            } catch (InvalidRuleValueException e) {
                throw new IllegalArgumentException(e);
            }
            return null;
        }
        @Override
        public MapCodec<? extends TestEnvironmentDefinition<Object>> codec() {
            return CODEC;
        }

        @Override
        public void teardown(ServerLevel world, Object saveData) {
            RuleHelper.resetToDefault(CarpetServer.settingsManager.getCarpetRule(key), world.getServer().createCommandSourceStack());
        }
    }
}
