package io.renren.common.cache;

import io.renren.modules.generator.entity.YmRemarkEntity;

import java.util.HashMap;
import java.util.List;

public class LocalCache {

    public static HashMap<Long, HashMap<String, YmRemarkEntity>> USER_YM_REMARKS_MAP = new HashMap<>();
    public static HashMap<Long, Boolean> USER_HAS_YM_REMARKS_FLAG = new HashMap<>();

}
