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

package org.dmfs.tasks.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


/**
 * The PinTaskHandler simplifies the pinning and unpinning of tasks. Internally it manages the pin notification handling.
 *
 * @author Tobias Reinsch <tobias@dmfs.org>
 */
public class TaskNotificationHandler extends BroadcastReceiver
{

    /**
     * Receives the a notification when the data in the provider changed.
     */
    @Override
    public void onReceive(Context context, Intent intent)
    {
        TaskNotificationService.enqueueWork(context, intent);
    }
}
