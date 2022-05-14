package thederpgamer.eggstring.world.generator;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.*;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.blending.Blender;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import thederpgamer.eggstring.world.WorldManager;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

/**
 * <Description>
 *
 * @author Garret Reichenbach
 * @version 1.0 - [05/14/2022]
 */
public class PlainsWorldChunkGenerator extends ChunkGenerator implements WorldChunkGenerator {

	private static final Codec<Settings> SETTINGS_CODEC = RecordCodecBuilder.create(instance ->
			instance.group(
					Codec.INT.fieldOf("base").forGetter(Settings::baseHeight),
					Codec.FLOAT.fieldOf("verticalvariance").forGetter(Settings::verticalVariance),
					Codec.FLOAT.fieldOf("horizontalvariance").forGetter(Settings::horizontalVariance)
			).apply(instance, Settings::new));

	public static final Codec<PlainsWorldChunkGenerator> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			RegistryOps.retrieveRegistry(Registry.STRUCTURE_SET_REGISTRY).forGetter(PlainsWorldChunkGenerator::getStructureSetRegistry),
			RegistryOps.retrieveRegistry(Registry.BIOME_REGISTRY).forGetter(PlainsWorldChunkGenerator::getBiomeRegistry),
			SETTINGS_CODEC.fieldOf("settings").forGetter(PlainsWorldChunkGenerator::getSettings)
	).apply(instance, PlainsWorldChunkGenerator::new));
	private final Settings settings;

	public PlainsWorldChunkGenerator(Registry<StructureSet> structureSetRegistry, Registry<Biome> registry, Settings settings) {
		super(structureSetRegistry, getSet(structureSetRegistry), new PlainsWorldBiomeProvider(registry));
		this.settings = settings;
	}

	private static Optional<HolderSet<StructureSet>> getSet(Registry<StructureSet> structureSetRegistry) {
		HolderSet.Named<StructureSet> structureSet = structureSetRegistry.getOrCreateTag(TagKey.create(Registry.STRUCTURE_SET_REGISTRY, WorldManager.PLAINS_WORLD_SET));
		return Optional.of(structureSet);
	}

	public Settings getSettings() {
		return settings;
	}

	@Override
	public Registry<Biome> getBiomeRegistry() {
		return ((PlainsWorldBiomeProvider) biomeSource).getBiomeRegistry();
	}

	@Override
	public Registry<StructureSet> getStructureSetRegistry() {
		return structureSets;
	}

	@Override
	public TagKey<StructureSet> getStructureSet() {
		return WorldManager.PLAINS_WORLD_STRUCTURE_SET;
	}

	@Override
	public void buildSurface(WorldGenRegion region, StructureFeatureManager featureManager, ChunkAccess chunk) {
		/*
		BlockState bedrock = Blocks.BEDROCK.defaultBlockState();
		BlockState stone = Blocks.STONE.defaultBlockState();
		ChunkPos chunkpos = chunk.getPos();

		BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();

		int x;
		int z;

		for(x = 0; x < 16; x ++) {
			for(z = 0; z < 16; z ++) {
				chunk.setBlockState(pos.set(x, 0, z), bedrock, false);
			}
		}

		int baseHeight = settings.baseHeight();
		float verticalVariance = settings.verticalVariance();
		float horizontalVariance = settings.horizontalVariance();
		for (x = 0; x < 16; x++) {
			for (z = 0; z < 16; z++) {
				int realx = chunkpos.x * 16 + x;
				int realz = chunkpos.z * 16 + z;
				int height = getHeightAt(baseHeight, verticalVariance, horizontalVariance, realx, realz);
				for (int y = 1 ; y < height ; y++) {
					chunk.setBlockState(pos.set(x, y, z), stone, false);
				}
			}
		}
		 */
	}

	private int getHeightAt(int baseHeight, float verticalVariance, float horizontalVariance, int x, int z) {
		return (int) (baseHeight + Math.sin(x / horizontalVariance) * verticalVariance + Math.cos(z / horizontalVariance) * verticalVariance);
	}

	@Override
	protected Codec<? extends ChunkGenerator> codec() {
		return CODEC;
	}

	@Override
	public ChunkGenerator withSeed(long seed) {
		return new PlainsWorldChunkGenerator(getStructureSetRegistry(), getBiomeRegistry(), settings);
	}

	@Override
	public CompletableFuture<ChunkAccess> fillFromNoise(Executor executor, Blender blender, StructureFeatureManager featureManager, ChunkAccess chunkAccess) {
		return CompletableFuture.completedFuture(chunkAccess);
	}

	// Make sure this is correctly implemented so that structures and features can use this
	@Override
	public int getBaseHeight(int x, int z, Heightmap.Types types, LevelHeightAccessor levelHeightAccessor) {
		int baseHeight = settings.baseHeight();
		float verticalVariance = settings.verticalVariance();
		float horizontalVariance = settings.horizontalVariance();
		return getHeightAt(baseHeight, verticalVariance, horizontalVariance, x, z);
	}

	// Make sure this is correctly implemented so that structures and features can use this
	@Override
	public NoiseColumn getBaseColumn(int x, int z, LevelHeightAccessor levelHeightAccessor) {
		int y = getBaseHeight(x, z, Heightmap.Types.WORLD_SURFACE_WG, levelHeightAccessor);
		BlockState stone = Blocks.STONE.defaultBlockState();
		BlockState[] states = new BlockState[y];
		states[0] = Blocks.BEDROCK.defaultBlockState();
		for(int i = 1 ; i < y ; i ++) states[i] = stone;
		return new NoiseColumn(levelHeightAccessor.getMinBuildHeight(), states);
	}

	@Override
	public void addDebugScreenInfo(List<String> p_208054_, BlockPos p_208055_) {

	}

	@Override
	public void applyCarvers(WorldGenRegion level, long seed, BiomeManager biomeManager, StructureFeatureManager featureManager, ChunkAccess chunkAccess, GenerationStep.Carving carving) {
	}

	@Override
	public Climate.Sampler climateSampler() {
		return new Climate.Sampler(DensityFunctions.constant(0.0), DensityFunctions.constant(0.0), DensityFunctions.constant(0.0), DensityFunctions.constant(0.0), DensityFunctions.constant(0.0), DensityFunctions.constant(0.0), Collections.emptyList());
	}

	@Override
	public void spawnOriginalMobs(WorldGenRegion level) {
		ChunkPos chunkpos = level.getCenter();
		Holder<Biome> biome = level.getBiome(chunkpos.getWorldPosition().atY(level.getMaxBuildHeight() - 1));
		WorldgenRandom worldgenrandom = new WorldgenRandom(new LegacyRandomSource(RandomSupport.seedUniquifier()));
		worldgenrandom.setDecorationSeed(level.getSeed(), chunkpos.getMinBlockX(), chunkpos.getMinBlockZ());
		NaturalSpawner.spawnMobsForChunkGeneration(level, biome, chunkpos, worldgenrandom);
	}

	@Override
	public int getMinY() {
		return 0;
	}

	@Override
	public int getGenDepth() {
		return 256;
	}

	@Override
	public int getSeaLevel() {
		return 63;
	}

	private record Settings(int baseHeight, float verticalVariance, float horizontalVariance) { }

	public static class PlainsWorldBiomeProvider extends BiomeSource {

		public static final Codec<PlainsWorldBiomeProvider> CODEC = RegistryOps.retrieveRegistry(Registry.BIOME_REGISTRY).xmap(PlainsWorldBiomeProvider ::new, PlainsWorldBiomeProvider::getBiomeRegistry).codec();
		private final Holder<Biome> biome;
		private final Registry<Biome> biomeRegistry;
		private static final List<ResourceKey<Biome>> SPAWN = Collections.singletonList(Biomes.PLAINS);

		public PlainsWorldBiomeProvider(Registry<Biome> biomeRegistry) {
			super(getStartBiomes(biomeRegistry));
			this.biomeRegistry = biomeRegistry;
			this.biome = biomeRegistry.getHolderOrThrow(Biomes.PLAINS);
		}

		private static List<Holder<Biome>> getStartBiomes(Registry<Biome> registry) {
			return SPAWN.stream().map(s -> registry.getHolderOrThrow(ResourceKey.create(BuiltinRegistries.BIOME.key(), s.location()))).collect(Collectors.toList());
		}

		public Registry<Biome> getBiomeRegistry() {
			return biomeRegistry;
		}

		@Override
		protected Codec<? extends BiomeSource> codec() {
			return CODEC;
		}

		@Override
		public BiomeSource withSeed(long seed) {
			return this;
		}

		@Override
		public Holder<Biome> getNoiseBiome(int x, int y, int z, Climate.Sampler sampler) {
			return biome;
		}
	}
}