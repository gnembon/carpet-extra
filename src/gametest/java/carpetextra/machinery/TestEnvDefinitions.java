package carpetextra.machinery;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
<<<<<<< HEAD
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
=======

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
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
    }
    
    public record CarpetRule(String key, String value) implements TestEnvironmentDefinition {
        static final MapCodec<CarpetRule> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
                    Codec.STRING.fieldOf("key").forGetter(CarpetRule::key),
                    Codec.STRING.fieldOf("value").forGetter(CarpetRule::value)
                ).apply(i, CarpetRule::new));

        @Override
<<<<<<< HEAD
        public void setup(ServerLevel world) {
            try {
                CarpetServer.settingsManager.getCarpetRule(key).set(world.getServer().createCommandSourceStack(), value);
=======
        public void setup(ServerWorld world) {
            try {
                CarpetServer.settingsManager.getCarpetRule(key).set(world.getServer().getCommandSource(), value);
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
            } catch (InvalidRuleValueException e) {
                throw new IllegalArgumentException(e);
            }
        }

        @Override
<<<<<<< HEAD
        public MapCodec<? extends TestEnvironmentDefinition> codec() {
            return CODEC;
        }
        @Override
        public void teardown(ServerLevel world) {
            RuleHelper.resetToDefault(CarpetServer.settingsManager.getCarpetRule(key), world.getServer().createCommandSourceStack());
=======
        public MapCodec<? extends TestEnvironmentDefinition> getCodec() {
            return CODEC;
        }
        @Override
        public void teardown(ServerWorld world) {
            RuleHelper.resetToDefault(CarpetServer.settingsManager.getCarpetRule(key), world.getServer().getCommandSource());
>>>>>>> 2c8a9d9bb40e35f5b45335521c6557886b381adf
        }
    }
}
