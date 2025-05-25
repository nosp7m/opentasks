/*
 * Copyright 2017 dmfs GmbH
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
 */

package app.tasksandlists.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import app.tasksandlists.R;
import app.tasksandlists.model.ContentSet;
import app.tasksandlists.model.FieldDescriptor;
import app.tasksandlists.model.adapters.IntegerFieldAdapter;
import app.tasksandlists.model.layout.LayoutOptions;


/**
 * Widget to display Integer values.
 *
 * @author Arjun Naik <arjun@arjunnaik.in>
 */
public class PercentageFieldView extends AbstractFieldView
{
    private IntegerFieldAdapter mAdapter;
    private TextView mText;
    private ProgressBar mProgress;


    public PercentageFieldView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }


    public PercentageFieldView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }


    public PercentageFieldView(Context context)
    {
        super(context);
    }


    @Override
    protected void onFinishInflate()
    {
        super.onFinishInflate();
        mText = (TextView) findViewById(R.id.text);
        mProgress = (ProgressBar) findViewById(R.id.percentage_progress_bar);
    }


    @Override
    public void setFieldDescription(FieldDescriptor descriptor, LayoutOptions layoutOptions)
    {
        super.setFieldDescription(descriptor, layoutOptions);
        mAdapter = (IntegerFieldAdapter) descriptor.getFieldAdapter();
    }


    @Override
    public void onContentChanged(ContentSet contentSet)
    {
        if (mValues != null && mAdapter.get(mValues) != null)
        {
            int percentage = mAdapter.get(mValues);
            mProgress.setProgress(percentage);
            mText.setText(Integer.toString(percentage) + "%");
            setVisibility(View.VISIBLE);
        }
        else
        {
            setVisibility(View.GONE);
        }
    }

}
