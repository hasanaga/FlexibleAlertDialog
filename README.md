# FlexibleAlertDialog
FlexibleAlertDialog is an easily customizable, flexible Android Library, that helps you to show Alert Dialog inside any View (FrameLayout) **not just in fullscreen**.

## Getting Started
### Installing

Step 1. Add it in your root ```build.gradle``` at the end of repositories:
```gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```
OR
In Android Studio Bumblebee (2021.1.1) version add it ```settings.gradle```.
```gradle
pluginManagement {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
dependencyResolutionManagement {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

Step 2. Add the dependency
```gradle
dependencies {
    implementation 'com.github.hasanaga:FlexibleAlertDialog:0.0.2'
}
```

## Usage
After importing the library you can use FlexibleAlertDialog to show alert dialog.

Add the ```FrameLayout``` where you want to display the Alert Dialog.
```xml
<FrameLayout
    android:id="@+id/frame_layout"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />
```

**Sample 1.** Simple Alert Dialog with single message and buttons.
```kotlin
FlexibleAlertDialog.Builder(requireContext())
    .setTitle("Title")
    .setMessage("Message")
    .setParent(R.id.frame_layout)
    .setFragmentManager(childFragmentManager)
    .setNegativeButton {
        it.dismiss()
    }
    .setPositiveButton {
        it.dismiss()
    }
    .show()
```

**Sample 2.** Alert Dialog with custom view and operation.

Create  [```R.layout.uppercase_layout```](sample/src/main/res/layout/uppercase_layout.xml).


```kotlin
FlexibleAlertDialog.Builder(requireContext())
    .setTitle("Make Uppercase")
    .setParent(R.id.frame_layout)
    .setFragmentManager(childFragmentManager)
    .setView(R.layout.uppercase_layout)
    .setPositiveButton {
        it.dismiss()
    }
    .setEvent {
    
        onCreate = {
        }

        onCreateView = { view ->
        }

        onPause = {
        }
        onDestroy = {
        }

        onResume = {
        }
        
        onClickButton = { buttonType, view ->

            if(buttonType == FlexibleAlertDialog.ButtonType.PositiveButton){

                view?.findViewById<EditText>(R.id.edit_text)?.text?.let {
                    resultTextView.text = it.toString().uppercase()
                }
            }
        }

    }
    .show()
```

**Sample 3.** Simple Calculator with custom view and operation.
Create  [```R.layout.custom_layout```](sample/src/main/res/layout/custom_layout.xml).
```kotlin
FlexibleAlertDialog.Builder(requireContext())
            .setTitle("Calculator with custom View")
            .setParent(R.id.frame_layout)
            .setFragmentManager(childFragmentManager)
            .setPositiveButton("Close") {
                it.dismiss()
            }
            .setView(R.layout.custom_layout)
            .setEvent {

                var lastResult = ""

                fun calculate(param1: String, param2: String, operation: Char): String {

                    if (param1.isEmpty() || param2.isEmpty()) return "Invalid params";

                    val intParam1: Int = try {
                        param1.toInt()
                    } catch (e: NumberFormatException) {
                        return "Invalid Param1"
                    }
                    val intParam2: Int = try {
                        param2.toInt()
                    } catch (e: NumberFormatException) {
                        return "Invalid Param2"
                    }

                    return when (operation) {
                        '+' -> "${intParam1.toFloat() + intParam2}"
                        '-' -> "${intParam1.toFloat() - intParam2}"
                        '*' -> "${intParam1.toFloat() * intParam2}"
                        '/' -> "${if (intParam2 == 0) 0F else intParam1.toFloat() / intParam2}"
                        else -> ""
                    }
                }

                onCreateView = {

                    val num1: EditText = it.findViewById(R.id.number1);
                    val num2: EditText = it.findViewById(R.id.number2);
                    val result: TextView = it.findViewById(R.id.result);

                    it.findViewById<Button>(R.id.addition).setOnClickListener {
                        lastResult = calculate(num1.text.toString(), num2.text.toString(), '+')
                        result.text = lastResult
                    }

                    it.findViewById<Button>(R.id.subtraction).setOnClickListener {
                        lastResult = calculate(num1.text.toString(), num2.text.toString(), '-')
                        result.text = lastResult
                    }

                    it.findViewById<Button>(R.id.division).setOnClickListener {
                        lastResult = calculate(num1.text.toString(), num2.text.toString(), '/')
                        result.text = lastResult
                    }

                    it.findViewById<Button>(R.id.multiplication).setOnClickListener {
                        lastResult = calculate(num1.text.toString(), num2.text.toString(), '*')
                        result.text = lastResult
                    }

                }

                onClickButton = { buttonType, _ ->

                    if (buttonType == FlexibleAlertDialog.ButtonType.PositiveButton) {
                        resultTextView.text = lastResult
                    }
                }


            }
            .show()
```


## Screenshots
![Animation](/docs/animation.gif)
![Screenshot 1](/docs/screenshot_1.png) ![Screenshot 2](/docs/screenshot_2.png)

## Authors

* **Hasanaga Mammadov** -  [GitHub](https://github.com/hasanaga)


## License

```
Copyright 2022 Hasanaga Mammadov

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
