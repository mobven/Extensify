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
	   implementation 'com.github.mobven:extensify:0.0.1-alpha'
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
* doOnTabSelected(onSelected: (TabLayout.Tab?) -> Unit)

## Context

* toast(text: CharSequence, duration: Int = Toast.LENGTH_SHORT)
* heightPixels()
* widthPixels()
* drawable(res: Int)
* color(color: Int)
* dpToPixels(dp: Float)

## Collection

* MutableList.moveAt(oldIndex: Int, newIndex: Int)
* MutableList.move(item: T, newIndex: Int)

## Date

* formatToViewTime(customFormat: String = "dd MMMM yyyy")

## Int

* orZero()

## Double

* orZero()

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

---

Developed with 🖤 at [Mobven](https://mobven.com/)


