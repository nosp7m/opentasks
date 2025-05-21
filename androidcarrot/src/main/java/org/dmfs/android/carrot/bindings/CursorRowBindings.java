package org.dmfs.android.carrot.bindings;

import android.database.Cursor;
import androidx.annotation.NonNull;

import au.com.codeka.carrot.Bindings;


/**
 * {@link Bindings} for a {@link Cursor} row. Note these {@link Bindings} will try to access the current cursor row, so make sure the cursor already points
 * to the correct row.
 * <p>
 * Note, you still need to close the cursor afterwards!
 *
 * @author Marten Gajda
 */
public final class CursorRowBindings implements Bindings
{
    private final Cursor mCursor;


    public CursorRowBindings(Cursor cursor)
    {
        mCursor = cursor;
    }


    @Override
    public Object resolve(@NonNull String key)
    {
        int column = mCursor.getColumnIndex(key);
        return column >= 0 ? mCursor.getString(column) : null;
    }


    @Override
    public boolean isEmpty()
    {
        return mCursor.getColumnCount() == 0;
    }
}
