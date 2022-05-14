package thederpgamer.eggstring.world.generator;

import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.structure.StructureSet;

/**
 * <Description>
 *
 * @author Garret Reichenbach
 * @version 1.0 - [05/14/2022]
 */
public interface WorldChunkGenerator {

	Registry<Biome> getBiomeRegistry();
	Registry<StructureSet> getStructureSetRegistry();
	TagKey<StructureSet> getStructureSet();
}
