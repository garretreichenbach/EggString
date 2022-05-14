package thederpgamer.eggstring.world;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.HashMap;

/**
 * <Description>
 *
 * @author Garret Reichenbach
 * @version 1.0 - [05/14/2022]
 */
public class EggGenSettings {

	public static final int THREADS_INDEX = 0;
	public static final int CATALYSTS_INDEX = 1;

	public static final int ELDER_THREAD = 0;
	public static final int FIRE_THREAD = 1;
	public static final int WATER_THREAD = 2;
	public static final int EARTH_THREAD = 3;
	public static final int ARCANE_THREAD = 4;
	public static final int STEEL_THREAD = 5;
	public static final int AIR_THREAD = 6;
	public static final int HOLY_THREAD = 7;

	private int[] threads;
	private final HashMap<Integer, Float> weights = new HashMap<>();

	public EggGenSettings(int[] threads) {
		generateFromThreads(threads);
	}

	public EggGenSettings(String settings) {
		JsonElement element = JsonParser.parseString(settings);
		JsonArray array = element.getAsJsonArray();
		JsonArray threads = array.get(THREADS_INDEX).getAsJsonArray();
		int[] threadArray = new int[threads.size()];
		for(int i = 0; i < threads.size(); i ++) threadArray[i] = threads.get(i).getAsInt();
		/*
		for(int i = 0; i < threads.size(); i ++) {
			switch(array.get(i).getAsInt()) {
				//case ELDER_THREAD -> threadList.add((CreationThreadInterface) ItemManager.ELDER_CREATION_THREAD.get());
				case FIRE_THREAD -> threadList.add((CreationThreadInterface) ItemManager.FIRE_CREATION_THREAD.get());
				case WATER_THREAD -> threadList.add((CreationThreadInterface) ItemManager.WATER_CREATION_THREAD.get());
				case EARTH_THREAD -> threadList.add((CreationThreadInterface) ItemManager.EARTH_CREATION_THREAD.get());
				case ARCANE_THREAD -> threadList.add((CreationThreadInterface) ItemManager.ARCANE_CREATION_THREAD.get());
				case STEEL_THREAD -> threadList.add((CreationThreadInterface) ItemManager.STEEL_CREATION_THREAD.get());
				case AIR_THREAD -> threadList.add((CreationThreadInterface) ItemManager.AIR_CREATION_THREAD.get());
				case HOLY_THREAD -> threadList.add((CreationThreadInterface) ItemManager.HOLY_CREATION_THREAD.get());
			}
		}
		JsonArray catalysts = array.get(CATALYSTS_INDEX).getAsJsonArray();
		for(int i = 0; i < threads.size(); i ++) {
			switch(array.get(i).getAsInt()) {

			}
		}
		 */
		generateFromThreads(threadArray);
	}

	@Override
	public String toString() {
		JsonArray element = new JsonArray();
		JsonArray threadArray = new JsonArray();
		for(int i : threads) threadArray.add(i);
		element.add(threadArray);
		return element.toString();
	}

	public void generateFromThreads(int[] threads) {
		this.threads = threads;
		for(int i : threads) {
			if(weights.containsKey(i)) {
				float j = weights.get(i);
				j += j * 0.65f;
				weights.put(i, j);
			} else weights.put(i, 1.25f);
		}
	}

	public HashMap<Integer, Float> getWeights() {
		return weights;
	}
}
