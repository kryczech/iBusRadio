/* This file is part of iBusRadio.

    iBusRadio is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    iBusRadio is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with iBusRadio.  If not, see <http://www.gnu.org/licenses/>.
    
*/

package me.bniles.ibus.radio;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.TextView;

public class MessageReceiver extends BroadcastReceiver {
	
	private static final String TAG = MessageReceiver.class.getSimpleName();
	private String tempstring;
	// private String messageType;
	private byte[] messageData;
	private byte[] tempbytes;
	
	private Activity act;
	private TextView stationTextView;
	
	public MessageReceiver(Activity act) {
		this.act = act;
	}
	
	public void onReceive(Context context, Intent intent) {
		// messageType = intent.getStringExtra("MessageType");
		messageData = intent.getByteArrayExtra("MessageData");
		int lastindex;

		// if (messageType.equals("stationText")) {
		if ((messageData[0] == 0x68) && (messageData[2] == 0x3b) && (messageData[3] == 0x23) && (messageData[4] == 0x62) && (messageData[5] == 0x10)) {
			lastindex = messageData.length - 2;
			while (messageData[lastindex] == 0x20) {
				lastindex--;
			}
			tempbytes = new byte[lastindex - 5];
			for (int i = 0; i < tempbytes.length; i++) {
				tempbytes[i] = messageData[i + 6];
			}

			try {
				tempstring = new String(tempbytes, "UTF-8");
			} catch (Exception e) {
				Log.e(TAG, "error encoding intent extra");
			}
			stationTextView = (TextView) act.findViewById(R.id.TextView_stationtext);
			act.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					stationTextView.setText(tempstring);
				}
			});
			// Toast.makeText(context, "Broadcast Intent processed by receiver." + tempstring, Toast.LENGTH_LONG).show();
			// abortBroadcast();
		}
		abortBroadcast();
		// Log.i(TAG, "" + t.getTimeInMillis() + ": sending response broadcast intent");
		// Intent outintent = new Intent();
		// intent.setAction("com.ebookfrenzy.AnswerBroadcast");
		// Log.i(TAG, "HelloIOIOService: Sending intent.");
		// context.sendBroadcast(outintent);
	}

}
