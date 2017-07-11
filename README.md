# ConstraintSet-UpdateWith
This test project features a few extension methods for kotlin to add some flexibility to the ConstraintSets.
Consider a situation where you have a base layout and you want the ability to switch (and animate) between multiple states.
Each state has only a few views that are visible, but sometimes features different views than the other states. This will require multiple xml files that have all the views, which can result in big and confusing files.
To solve this, the 'updateWith(set: ConstraintSet)' extension method let's you have one default ConstraintSet and update it with a new ConstraintSet, that only contains the views that have changed.

## example
We start off with one base layout that contains all the views. This (default) state has all the views set to 'gone', except for the button:
```xml
<?xml version="1.0" encoding="utf-8"?>
<android.support.constraint.ConstraintLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:id="@+id/parent"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:padding="8dp">

    <Button
        android:id="@+id/switch_button"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Switch state"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"/>

    <EditText
        android:id="@+id/input_edit_text"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:visibility="gone"/>

    <Switch
        android:id="@+id/input_switch"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:visibility="gone"/>

    <CheckBox
        android:id="@+id/input_checkBox"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:visibility="gone"/>
    
</android.support.constraint.ConstraintLayout>
```

The first state will only show the EditText (in the middle), together with the switch. As you can see, only these views are mentioned in the xml file:
```xml
<?xml version="1.0" encoding="utf-8"?>
<android.support.constraint.ConstraintLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <EditText
        android:id="@+id/input_edit_text"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:visibility="visible"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toLeftOf="@+id/input_switch"
        app:layout_constraintTop_toTopOf="parent"/>

    <Switch
        android:id="@+id/input_switch"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:padding="4dp"
        android:visibility="visible"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent"/>
    
</android.support.constraint.ConstraintLayout>
```

The second state will only show the EditText (at the top), together with the checkbox. Again, only these views are mentioned in the xml file:
```xml
<?xml version="1.0" encoding="utf-8"?>
<android.support.constraint.ConstraintLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <EditText
        android:id="@+id/input_edit_text"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:visibility="visible"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toLeftOf="@+id/input_checkBox"
        app:layout_constraintTop_toTopOf="parent"/>

    <CheckBox
        android:id="@+id/input_checkBox"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:padding="4dp"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        android:visibility="visible"/>


</android.support.constraint.ConstraintLayout>
```
Finally, to create the constraintSets, the base set is created first. 
Now, to create the first and second state, the base is copied and updated with the 'delta' ConstraintSets. Like so: 

```Kotlin
        constraintLayout = findViewById(R.id.parent) as ConstraintLayout

        //Create an original ConstraintSet that has the default state
        originalConstraintSet = ConstraintSet()
        originalConstraintSet.clone(constraintLayout)

        //Create the 2 states by cloning the original set and updating it with the constraints of the states
        constraintSet1 = originalConstraintSet.copy().apply { updateWith(this@MainActivity, R.layout.main_state_1) }
        constraintSet2 = originalConstraintSet.copy().apply { updateWith(this@MainActivity, R.layout.main_state_2) }
 ```
 
When pressing the button, we simply rotate between originalConstraintSet, constraintSet1 and constraintSet2 to get this result:

<img src="https://raw.githubusercontent.com/tristanvda/ConstraintSet-UpdateWith/master/resources/demo.gif">

## References
https://developer.android.com/reference/android/support/constraint/ConstraintSet.html

## License
```
MIT License

Copyright (c) 2017 Tristan Vanderaerden

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```