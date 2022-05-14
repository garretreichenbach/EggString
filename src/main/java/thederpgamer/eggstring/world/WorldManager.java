package thederpgamer.eggstring.world;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraftforge.common.world.ForgeWorldPreset;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import thederpgamer.eggstring.EggString;
import thederpgamer.eggstring.world.generator.PlainsWorldChunkGenerator;

/**
 * <Description>
 *
 * @author Garret Reichenbach
 * @version 1.0 - [05/13/2022]
 */
public class WorldManager {

	public static final int SMALL = 0;
	public static final int MEDIUM = 1;
	public static final int LARGE = 2;
	public static final int HUGE = 3;
	public static final int COLOSSAL = 4;

	public static final DeferredRegister<ForgeWorldPreset> WORLDS = DeferredRegister.create(ForgeRegistries.WORLD_TYPES.get(), EggString.MODID);

	public static final Codec<PlainsWorldChunkGenerator> PLAINS_WORLD = Registry.register(Registry.CHUNK_GENERATOR, new ResourceLocation(EggString.MODID, "plains_world"), PlainsWorldChunkGenerator.CODEC);
	public static final ResourceLocation PLAINS_WORLD_SET = new ResourceLocation(EggString.MODID, "plains_world_structure_set");
	public static final TagKey<StructureSet> PLAINS_WORLD_STRUCTURE_SET = TagKey.create(Registry.STRUCTURE_SET_REGISTRY, PLAINS_WORLD_SET);

	public static void generateEggWorld(Player player, Level level, int size, EggGenSettings settings) {
		WorldGenRegion region = new WorldGenRegion();
	}
}
