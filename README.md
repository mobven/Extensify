# Extensify
Most used extension methods for Kotlin

## View

1. click()
2. show()
3. gone()
4. hideKeyboard()
5. showKeyboard()
6. showIf(condition: () -> Boolean)
7. hideIf(predicate: () -> Boolean)
8. removeIf(predicate: () -> Boolean)
9. multipleOnClick(vararg view: View, onClick: () -> Unit)
10. doOnTabSelected(onSelected: (TabLayout.Tab?) -> Unit)
11. NestedScrollView.setInsetListener()
## Context

1. toast(text: CharSequence, duration: Int = Toast.LENGTH_SHORT)
2. heightPixels()
3. widthPixels()
4. drawable(res: Int)
5. color(color: Int)
6. dpToPixels(dp: Float)

## Collection

1. MutableList.moveAt(oldIndex: Int, newIndex: Int)
2. MutableList.move(item: T, newIndex: Int)

## Date

1. formatToViewTime(customFormat: String = "dd MMMM yyyy")

## Int

1. orZero()

## Double

1. orZero()

## Long

1. orZero()

## String

1. SpannableString.setColor(color: Int, start: Int, end: Int)
2. SpannableString.bold(start: Int, end: Int)
3. SpannableString.underline(start: Int, end: Int)
4. SpannableString.italic(start: Int, end: Int)

## Boolean

1. orFalse()

## Lifecycle

1. LifecycleOwner.observe(liveData: LiveData<T>?, observer: (T) -> Unit)


