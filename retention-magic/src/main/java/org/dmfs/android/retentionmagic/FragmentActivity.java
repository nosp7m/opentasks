/*
 * Copyright (C) 2013 Marten Gajda <marten@dmfs.org>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */

package org.dmfs.android.retentionmagic;

import android.content.SharedPreferences;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;


/**
 * A {@link androidx.fragment.app.FragmentActivity} that can retain field values.
 * 
 * @author Marten Gajda <marten@dmfs.org>
 */
public class FragmentActivity extends androidx.fragment.app.FragmentActivity
{
	private SharedPreferences mPrefs;


	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		mPrefs = getSharedPreferences(getPackageName() + ".sharedPrefences", 0);

		RetentionMagic.init(this, getIntent().getExtras());

		if (savedInstanceState == null)
		{
			RetentionMagic.init(this, mPrefs);
		}
		else
		{
			RetentionMagic.restore(this, savedInstanceState);
		}
	}


	@Override
	protected void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);
		RetentionMagic.store(this, outState);
	}


	@Override
	protected void onPause()
	{
		super.onPause();
		/*
		 * On older SDK version we have to store permanent data in onPause(), because there is no guarantee that onStop() will be called.
		 */
		if (VERSION.SDK_INT < VERSION_CODES.HONEYCOMB)
		{
			RetentionMagic.persist(this, mPrefs);
		}
	}


	@Override
	protected void onStop()
	{
		super.onStop();
		if (VERSION.SDK_INT >= VERSION_CODES.HONEYCOMB)
		{
			RetentionMagic.persist(this, mPrefs);
		}
	}

}
