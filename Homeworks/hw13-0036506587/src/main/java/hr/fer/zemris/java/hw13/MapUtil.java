package hr.fer.zemris.java.hw13;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Utility class which provides some functional methods when working with
 * {@link Map} objects.
 * 
 * @author dbrcina
 *
 */
public class MapUtil {

	/**
	 * Sorts <code>map</code> by values in <b>descending</b> order.
	 * 
	 * @param <K> key type.
	 * @param <V> value type.
	 * @param map map.
	 * @return sorted map.
	 */
	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValueDesc(Map<K, V> map) {
		Comparator<V> descending = (v1, v2) -> v2.compareTo(v1);
		List<Entry<K, V>> list = new ArrayList<>(map.entrySet());
		list.sort(Entry.comparingByValue(descending));

		Map<K, V> result = new LinkedHashMap<>();
		for (Entry<K, V> entry : list) {
			result.put(entry.getKey(), entry.getValue());
		}

		return result;
	}
}
