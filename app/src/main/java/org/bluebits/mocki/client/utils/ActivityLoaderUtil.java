/**
 * 
 */
package org.bluebits.mocki.client.utils;

import android.content.Context;
import android.content.Intent;

/**
 * @author satyajit
 *
 */
public class ActivityLoaderUtil {
	
	public static <T> void load(Context context, Class<T> activityClazz) {
		Intent intent = new Intent(context, activityClazz);
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		context.startActivity(intent);
	} 
}
