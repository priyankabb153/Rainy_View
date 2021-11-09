# RainyView

A HMOS library which provides rainy view animation

## Source
Inspired by [samlss/RainyView](https://github.com/samlss/RainyView) - version 1.0

## Feature
This library provides an animation of rainy view feature.

<img src="https://github.com/priyankabb153/RainyView/blob/master/screenshots/raniyview1.gif" width="256">

<img src="https://github.com/priyankabb153/RainyView/blob/master/screenshots/raniyview2.gif" width="256">


## Dependency
1. For using rainyview module in sample app, include the source code and add the below dependencies in entry/build.gradle to generate hap/support.har.
```groovy
	dependencies {
		implementation project(':rainyview')
                implementation fileTree(dir: 'libs', include: ['*.har'])
                testImplementation 'junit:junit:4.13'
	}
```
2. For using rainyview in separate application using har file, add the har file in the entry/libs folder and add the dependencies in entry/build.gradle file.
```groovy
	dependencies {
		implementation fileTree(dir: 'libs', include: ['*.har'])
		testImplementation 'junit:junit:4.13'
	}
```

## Usage

#### In layout.xml
```xml
<me.samlss.view.RainyView
    ohos:id="$+id:rv"
    ohos:height="300vp"
    ohos:width="150vp"
    ohos:top_padding="15vp"
    ohos:scale_mode="stretch"
    ohos:top_margin="80vp"
    ohos:center_in_parent="true"
    custom:left_cloud_color="#B7AC8D"
    custom:right_cloud_color="#9b8f84"
    custom:raindrop_color="#9aa9bb"
    custom:raindrop_creation_interval="10"
    custom:raindrop_max_number="50"
    custom:raindrop_max_length="50"
    custom:raindrop_min_length="20"
    custom:raindrop_min_speed="1"
    custom:raindrop_max_speed="3"
    custom:raindrop_size="15"
    custom:raindrop_slope="-4"  />
```

#### In code

```java
RainyView rainyView = (RainyView) findComponentById(ResourceTable.Id_rv);
rainyView.setLeftCloudColor(Color.getIntColor("#B7AC8D")); //Set the color of the left cloud
rainyView.setRightCloudColor(Color.getIntColor("#9b8f84")); //Set the color of the right cloud
rainyView.setRainDropColor(Color.getIntColor("#9aa9bb")); //Set the color of the raindrop
rainyView.setRainDropMaxNumber(50); //Set the max number of the raindrop
rainyView.setRainDropMaxLength(50); //Set the max length of the raindrop
rainyView.setRainDropMinLength(20); //Set the min length of the raindrop
rainyView.setRainDropMaxSpeed(3); //Set the max speed of the raindrop
rainyView.setRainDropMinSpeed(1); //Set the min speed of the raindrop
rainyView.setRainDropSlope(-4); //Set the slope of the raindrop
rainyView.setRainDropCreationInterval(10); //Set the creation interval of the raindrop

rainyView.start(); //Start animation
rainyView.stop(); //Stop animation
```

## License
```
Copyright 2018 samlss

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
