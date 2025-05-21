# Android Carrot Bindings

`Bindings` for [carrot](https://github.com/codeka/carrot) on Android.
 
## `AndroidBindings`

By adding `AndroidBindings` to your template `Bindings` you can access a bunch of app and system values.

Note you can use all these `Bindings` separately as well. When using `AndroidBindings` their keys are prefixed with `$xxx` as seen below.

### `AppBindings`

`AppBindings` provides a couple of values about the app itself:

```
{{ $app.package }}  : renders the package name of the current app 
{{ $app.title }}  : renders the title of the current app 
{{ $app.version }}  : renders the version name of the current app 
{{ $app.version_code }}  : renders the version code of the current app 
```


### `DeviceBindings`

`DeviceBindings` provides some of the values in `android.os.Build`:

```
{{ $device.manufacturer }}  : renders the manufacturer of the device
{{ $device.model }}  : renders the model of the device
{{ $device.device }}  : renders the device string
{{ $device.product }}  : renders the product name of the device
{{ $device.sdk_level }}  : renders the sdk level of the device
{{ $device.version }}  : renders the OS version of the device
{{ $device.build_id }}  : renders the build id of the device
```

### `StringResourceBindings`

`StringResourceBindings` provides access to the string resources of the app:

e.g.:
```
{{ $R.string.app_name }}  : renders the value of the resource `R.string.app_name`
```


### `IntegerResourceBindings`

`IntegerResourceBindings` provides access to the string resources of the app:

e.g.:
```
{{ $R.integer.some_int }}  : renders the integer value of the resource `R.integer.some_int`
```


### `AppPreferencesBindings`

`AppPreferencesBindings` provides access to `SharePreferences` of the app:

e.g.:
```
{{ $prefs.my_prefs.pref1 }}  : renders the value preference `pref1` in the shared preferences file `my_prefs`.
```

### `LocaleBindings`

`LocaleBindings` provides access `Locale`

```
{{ $locale.country }}  : renders the country name of the current locale in the current locale
{{ $locale.country_en }}  : renders the country name of the current locale in English
{{ $locale.country_code }}  : renders the country code of the current locale, e.g. `US`
{{ $locale.language }}  : renders the language name of the current locale in the current locale
{{ $locale.language_en }}  : renders the language name of the current locale in English
{{ $locale.language_code }}  : renders the language code of the current locale, e.g. `en`
{{ $locale.code }}  : renders the language tag of the current locale, e.g. `en-US`
```

## Additional Bindings

In addition this library provides a few more Bindings for other purposes.

### `BundleBindings`

`BundleBindings` provides access to the values of a `Bundle`:

```
{{ bundle.key }} : renders the value of the entry with the key `key` of the respective bundle.
{{ bundle.nested.key }} : renders the value of the entry with the key `key` of the nested bundle under the key `nested`.
```

`BundleBindings` is iterable and can be used in a `for` block to iterate over all values.


### `IntentBindings`

`IntentBindings` provides access to some of the values of an `Intent`:

```
{{ intent.action }} : renders the action of the intent
{{ intent.data }} : renders the data of the intent
{{ intent.extras.key }} : renders the value of the extra `key`. `intent.extras` contains all the extras of the intent and is iterable. 
{{ intent.extras["key"] }} : renders the value of the extra `key`. `intent.extras` contains all the extras of the intent and is iterable (alternate notation) 
{{ intent.categories }} : an iterable of all categories of the intent (in no particular order)
```

### `CursorRowBindings`

`CursorRowBindings` provides access to the current row of a `Cursor`

```
{{ cursor._id }} : renders the value of the `_id` column
{{ cursor["_id"] }} : renders the value of the `_id` column (alternate notation)
{{ cursor.displayname }} : renders the value of the `displayname` column
{{ cursor["displayname"] }} : renders the value of the `displayname` column (alternate notation)
```
