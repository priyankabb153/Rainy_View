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
import me.samlss.rainyviewdemo.ResourceTable;
import me.samlss.view.RainyView;

/**
 * Sample app to test the RainyView library functionality.
 */
public class MainAbilitySlice extends AbilitySlice {

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);
        RainyView rainyView = (RainyView) findComponentById(ResourceTable.Id_rv);
        Button start = (Button) findComponentById(ResourceTable.Id_start_button);
        Button stop = (Button) findComponentById(ResourceTable.Id_stop_button);
        Button next = (Button) findComponentById(ResourceTable.Id_next_button);
        start.setClickedListener(component -> rainyView.start());
        stop.setClickedListener(component -> rainyView.stop());
        next.setClickedListener(list -> present(new MoreAbilitySlice(), new Intent()));
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
