# DrawablePagerTitleStrip

__A PagerTitleStrip for Images__

This is a version of a PagerTitleStrip that shows images instead of text. See [color-picker](https://github.com/dmfs/color-picker) for screenshots and an implementation example.

## Requirements

* Android support library 19 or newer

## Example code

The `ViewPager` in this library is just a copy of the original `ViewPager` from the support library, but since the pager title strip classes depend on a
few non-public members you'll we had to include it. That means you'll need to use the `ViewPager` provided by this library.

In your layout file replace `android.support.v4.view.ViewPager` by `org.dmfs.android.view.ViewPager` and `android.support.v4.view.PagerTitleStrip` by `org.dmfs.android.view.DrawablePagerTitleStrip` (or `org.dmfs.android.view.DrawablePagerTabStrip`) like so:


		<org.dmfs.android.view.ViewPager xmlns:android="http://schemas.android.com/apk/res/android"
		    android:id="@+id/pager"
		    android:layout_width="wrap_content"
		    android:layout_height="wrap_content" >

		    <org.dmfs.android.view.DrawablePagerTabStrip
			android:id="@+id/pager_title_strip"
			android:layout_width="match_parent"
			android:layout_height="wrap_content"
			android:layout_gravity="bottom"
			android:paddingBottom="4dip"
			android:paddingLeft="8dip"
			android:paddingRight="8dip"
			android:paddingTop="0dip" />

		</org.dmfs.android.view.ViewPager>

Don't forget to update any imports and types.

Now let your `FragmentStatePagerAdapter` or `FragmentPagerAdapter` implement `IDrawableTitlePagerAdapter`

		public class PalettesPagerAdapter extends FragmentStatePagerAdapter implements IDrawableTitlePagerAdapter
		{

		...


			@Override
			public Drawable getDrawableTitle(int position)
			{
				// return a drawable for this page
				...
				return someDrawable;
			}
		}

Be aware that neither `DrawablePagerTabStrip` nor `DrawablePagerTitleStrip` will cache the `Drawable`s, so you better take care of that yourself. See [PalettesPagerAdapter.java](https://github.com/dmfs/color-picker/blob/master/src/org/dmfs/android/colorpicker/PalettesPagerAdapter.java) for an example.


## TODO

* dynamically determine the number of images in the title strip
* clean up code

## License

Licensed under Apache2

This work is based on the Android Support Library with the following copyright:
Copyright (C) 2012 The Android Open Source Project

Modifications:
Copyright (C) Marten Gajda 2014

