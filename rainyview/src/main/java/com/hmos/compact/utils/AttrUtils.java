/*
 * Copyright (C) 2020-21 Application Library Engineering Group
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hmos.compact.utils;

import ohos.agp.components.AttrSet;

/**
 * Attrutils class.
 */
public class AttrUtils {

    /**
     * Function to get int value from attribute.
     *
     * @param attrs Attribute set
     * @param name String name
     * @param defaultValue default value
     * @return int value
     *
     */
    public static int getIntFromAttr(AttrSet attrs, String name, int defaultValue) {
        int value = defaultValue;
        try {
            if (attrs.getAttr(name).isPresent() && attrs.getAttr(name).get() != null) {
                value = attrs.getAttr(name).get().getIntegerValue();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * Function to get float value from attribute.
     *
     * @param attrs Attribute set
     * @param name String name
     * @param defaultValue default value
     * @return float value
     *
     */
    public static float getFloatFromAttr(AttrSet attrs, String name, float defaultValue) {
        float value = defaultValue;
        try {
            if (attrs.getAttr(name).isPresent() && attrs.getAttr(name).get() != null) {
                value = attrs.getAttr(name).get().getFloatValue();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * Function to get boolean value from attribute.
     *
     * @param attrs Attribute set
     * @param name String name
     * @param defaultValue default value
     * @return boolean value
     *
     */
    public static Boolean getBooleanFromAttr(AttrSet attrs, String name, boolean defaultValue) {
        boolean value = defaultValue;
        try {
            if (attrs.getAttr(name).isPresent() && attrs.getAttr(name).get() != null) {
                value = attrs.getAttr(name).get().getBoolValue();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * Function to get long value from attribute.
     *
     * @param attrs Attribute set
     * @param name String name
     * @param defaultValue default value
     * @return long value
     *
     */
    public static long getLongFromAttr(AttrSet attrs, String name, long defaultValue) {
        long value = defaultValue;
        try {
            if (attrs.getAttr(name).isPresent() && attrs.getAttr(name).get() != null) {
                value = attrs.getAttr(name).get().getLongValue();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * Function to get colour value from attribute.
     *
     * @param attrs Attribute set
     * @param name String name
     * @param defaultValue default value
     * @return colour value
     *
     */
    public static int getColorFromAttr(AttrSet attrs, String name, int defaultValue) {
        int value = defaultValue;
        try {
            if (attrs.getAttr(name).isPresent() && attrs.getAttr(name).get() != null) {
                value = attrs.getAttr(name).get().getColorValue().getValue();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * Function to get dimension value from attribute.
     *
     * @param attrs Attribute set
     * @param name String name
     * @param defaultValue default value
     * @return value
     *
     */
    public static int getDimensionFromAttr(AttrSet attrs, String name, int defaultValue) {
        int value = defaultValue;
        try {
            if (attrs.getAttr(name).isPresent() && attrs.getAttr(name).get() != null) {
                value = attrs.getAttr(name).get().getDimensionValue();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * Function to get string value from attribute.
     *
     * @param attrs Attribute set
     * @param name String name
     * @param defaultValue default value
     * @return String value
     *
     */
    public static String getStringFromAttr(AttrSet attrs, String name, String defaultValue) {
        String value = defaultValue;
        try {
            if (attrs.getAttr(name).isPresent() && attrs.getAttr(name).get() != null) {
                value = attrs.getAttr(name).get().getStringValue();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

}
