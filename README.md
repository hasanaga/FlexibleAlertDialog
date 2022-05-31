# FlexibleAlertDialog
FlexibleAlertDialog is an easy customizable, flexible Android Library, helps you to show Alert Dialog inside any View (FrameLayout)

## Getting Started
### Installing

Step 1. Add it in your root build.gradle at the end of repositories:
```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

Step 2. Add the dependency
```
dependencies {
    implementation 'com.github.hasanaga:FlexibleAlertDialog:0.0.1'
}
```

## Usage
After importing the library you can use FlexibleAlertDialog to show alert dialog.

Add the ```FrameLayout``` where you want to display the Alert Dialog.
```
<FrameLayout
    android:id="@+id/frame_layout"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />
```

**Sample 1.** Simple Alert Dialog with single message and buttons.
```
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

1. Create ```R.layout.uppercase_layout```.

2. 
```
FlexibleAlertDialog.Builder(requireContext())
    .setTitle("Make Uppercase")
    .setParent(R.id.frame_layout)
    .setFragmentManager(childFragmentManager)
    .setView(R.layout.uppercase_layout)
    .setPositiveButton {
        it.dismiss()
    }
    .setEvent {

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
## Screenshots


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
