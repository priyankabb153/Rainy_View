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

package me.samlss.rainyviewdemo.slice;

import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.utils.Color;
import me.samlss.rainyviewdemo.ResourceTable;
import me.samlss.view.RainyView;

/**
 * Sample app to test the RainyView library functionality.
 */
public class MoreAbilitySlice extends AbilitySlice {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_more);
        RainyView rainyView1;
        rainyView1 = (RainyView) findComponentById(ResourceTable.Id_fv1);
        RainyView rainyView2;
        rainyView2 = (RainyView) findComponentById(ResourceTable.Id_fv2);
        rainyView1.setLeftCloudColor(Color.getIntColor("#B7AC8D"));
        rainyView1.setRightCloudColor(Color.getIntColor("#9b8f84"));
        rainyView1.setRainDropColor(Color.getIntColor("#9aa9bb"));
        rainyView1.setRainDropMaxNumber(50);
        rainyView1.setRainDropMaxLength(50);
        rainyView1.setRainDropMinLength(20);
        rainyView1.setRainDropMaxSpeed(3);
        rainyView1.setRainDropMinSpeed(1);
        rainyView1.setRainDropSlope(-4);
        rainyView1.setRainDropCreationInterval(10);
        Button start = (Button) findComponentById(ResourceTable.Id_next_start_button);
        Button stop = (Button) findComponentById(ResourceTable.Id_next_stop_button);
        start.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                rainyView1.start();
                rainyView2.start();
            }
        });
        stop.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                rainyView1.stop();
                rainyView2.stop();
            }
        });
    }

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }
}
