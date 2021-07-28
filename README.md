# Extensify
Most used extension methods for Kotlin


# Download

### Step 1. Add the JitPack repository to your build file

```
allprojects {
    repositories {
	    ...
	    maven { url 'https://jitpack.io' }
	}
}

```

### Step 2. Add the dependency

```
dependencies {
	   implementation 'com.github.mobven:extensify:0.0.2'
	}
```

## View

* click()
* show()
* gone()
* hideKeyboard()
* showKeyboard()
* showIf(condition: () -> Boolean)
* hideIf(predicate: () -> Boolean)
* removeIf(predicate: () -> Boolean)
* multipleOnClick(vararg view: View, onClick: () -> Unit)
* onTabSelectedListener(onSelected: (TabLayout.Tab?) -> Unit, onReselected: (TabLayout.Tab?) -> Unit, onUnselected: (TabLayout.Tab?) -> Unit)


## Context

* toast(text: CharSequence, duration: Int = Toast.LENGTH_SHORT)
* heightPixels()
* widthPixels()
* drawable(res: Int)
* color(color: Int)
* dpToPixels(dp: Float)
* startActivityWithExtras
* showUrlOnCustomTabs(url: String, 
                      shareState: Int = CustomTabsIntent.SHARE_STATE_OFF, 
                      navigationColor: Int = android.R.color.holo_green_dark, 
                      toolbarColor: Int = android.R.color.holo_blue_bright
                     )
* chooseFromGallery(callback: ActivityResultCallback<List<Uri?>>): ActivityResultLauncher<String>

## Collection

* MutableList.moveAt(oldIndex: Int, newIndex: Int)
* MutableList.move(item: T, newIndex: Int)

## Date

* formatToViewTime(customFormat: String = "dd MMMM yyyy")

## Int

* orZero()

## Double

* orZero()
* localizedNumberFormat()

## Long

* orZero()

## String

* SpannableString.setColor(color: Int, start: Int, end: Int)
* SpannableString.bold(start: Int, end: Int)
* SpannableString.underline(start: Int, end: Int)
* SpannableString.italic(start: Int, end: Int)
* String.capitalizeWords(locale: Locale, delimiter: String)

## Boolean

* orFalse()

## Lifecycle

* LifecycleOwner.observe(liveData: LiveData<T>?, observer: (T) -> Unit)

## Additional Features

* [Event Wrapper](https://medium.com/androiddevelopers/livedata-with-snackbar-navigation-and-other-events-the-singleliveevent-case-ac2622673150)

---

Developed with 🖤 at [Mobven](https://mobven.com/)


