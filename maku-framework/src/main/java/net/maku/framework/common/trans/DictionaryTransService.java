package net.maku.framework.common.trans;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Dictionary translation service
 * <p>
 * Maintain the dictionary translation cache, providing forward translation (value -> label) and reverse translation (label -> value)
 *
 * @author maku
 */
@Slf4j
@Service
public class DictionaryTransService {
    /**
     * Dictionary translation cache
     * key Format: dictType_dictValue -> dictLabel (Forward)
     * key Format: un_trans:dictType_dictLabel -> dictValue (Reverse)
     */
    private final Map<String, String> dictionaryTransMap = new ConcurrentHashMap<>();

    /**
     * Get dictionary translation Map
     *
     * @return Translation cache
     */
    public Map<String, String> getDictionaryTransMap() {
        return dictionaryTransMap;
    }

    /**
     * Refresh dictionary cache
     *
     * @param dictType Dictionary type
     * @param dataMap  Dictionary data key: dictValue, value: dictLabel
     */
    public void refreshCache(String dictType, Map<String, String> dataMap) {
        if (dictType == null || dataMap == null) {
            return;
        }
        // Clear the old cache of this dictionary type first
        dictionaryTransMap.entrySet().removeIf(entry ->
                entry.getKey().startsWith(dictType + "_") ||
                        entry.getKey().startsWith("un_trans:" + dictType + "_"));
        // Add new cache
        dataMap.forEach((value, label) -> {
            // Forward translation: dictType_value -> label
            dictionaryTransMap.put(dictType + "_" + value, label);
            // Reverse translation: un_trans:dictType_label -> value
            dictionaryTransMap.put("un_trans:" + dictType + "_" + label, value);
        });
    }

    /**
     * Forward translation: get the dictionary label by dictionary type and dictionary value
     *
     * @param dictType  Dictionary type
     * @param dictValue Dictionary value
     * @return Dictionary label
     */
    public String translate(String dictType, Object dictValue) {
        if (dictType == null || dictValue == null) {
            return null;
        }
        return dictionaryTransMap.get(dictType + "_" + dictValue);
    }

    /**
     * Reverse translation: get the dictionary value by dictionary type and dictionary label
     *
     * @param dictType  Dictionary type
     * @param dictLabel Dictionary label
     * @return Dictionary value
     */
    public String reverseTranslate(String dictType, String dictLabel) {
        if (dictType == null || dictLabel == null) {
            return null;
        }
        return dictionaryTransMap.get("un_trans:" + dictType + "_" + dictLabel);
    }
}
