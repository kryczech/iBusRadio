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
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;

public class IBusRadio extends Activity {

	private Button buttonMode;
	private Button buttonTuneRight;
	private Button buttonTuneLeft;
	private Button buttonVolumeUp;
	private Button buttonVolumeDown;
	private Button buttonVolumePress;
	private Button button1;
	private Button button2;
	private Button button3;
	private Button button4;
	private Button button5;
	private Button button6;
	private Button buttonTone;
	private Button buttonSelect;
	private Button buttonNavLeft;
	private Button buttonNavRight;
	private Button buttonNavPress;
	private Button buttonAM;
	private Button buttonFM;
	private Button buttonPhone;
	
	private BroadcastReceiver messageReceiver;
	private boolean isReceiverRegistered;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        isReceiverRegistered = false;
        
        // handleInputMessage = new HandleInputMessage(messageQueues, this);
    	// handleInputMessage.start();

        buttonMode = (Button)findViewById(R.id.Button_mode);
        buttonTuneRight = (Button)findViewById(R.id.Button_tuneright);
        buttonTuneLeft = (Button)findViewById(R.id.Button_tuneleft);
        buttonVolumeUp = (Button)findViewById(R.id.Button_volumeup);
    	buttonVolumeDown = (Button)findViewById(R.id.Button_volumedown);
    	buttonVolumePress = (Button)findViewById(R.id.Button_volpress);
    	button1 = (Button)findViewById(R.id.Button_1);
    	button2 = (Button)findViewById(R.id.Button_2);
    	button3 = (Button)findViewById(R.id.Button_3);
    	button4 = (Button)findViewById(R.id.Button_4);
    	button5 = (Button)findViewById(R.id.Button_5);
    	button6 = (Button)findViewById(R.id.Button_6);
    	buttonTone = (Button)findViewById(R.id.Button_tone);
    	buttonSelect = (Button)findViewById(R.id.Button_select);
    	buttonNavLeft = (Button)findViewById(R.id.Button_navleft);
    	buttonNavRight = (Button)findViewById(R.id.Button_navright);
    	buttonNavPress = (Button)findViewById(R.id.Button_navpress);
    	buttonAM = (Button)findViewById(R.id.Button_am);
    	buttonFM = (Button)findViewById(R.id.Button_fm);
    	buttonPhone = (Button)findViewById(R.id.Button_phone);
    	
    	OnTouchListener touchTripleAction = new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN ) {
					broadcastOutput(Integer.parseInt(v.getTag().toString()));
					return false;
				} else if (event.getAction() == MotionEvent.ACTION_UP ) {
					broadcastOutput(Integer.parseInt(v.getTag().toString()) + 2);
					return false;
				} 

				return false;

			}
		};

		OnLongClickListener longTripleAction = new OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				broadcastOutput(Integer.parseInt(v.getTag().toString()) + 1);
				return true;
			}
		};
		
		OnClickListener clickSingleAction = new OnClickListener() {
			@Override
			public void onClick(View v) {
				broadcastOutput(Integer.parseInt(v.getTag().toString()));
			}
		};

		// Set the tags of the buttons. This is important - they must be in the same order as in the Arduino sketch.
		button1.setTag("0");
		button2.setTag("3");
		button3.setTag("6");
		button4.setTag("9");
		button5.setTag("12");
		button6.setTag("15");
		
		buttonTuneLeft.setTag("18");
		buttonTuneRight.setTag("21");
		
		buttonMode.setTag("24");
		
		buttonTone.setTag("27");
		buttonSelect.setTag("30");
		
		buttonVolumeUp.setTag("33");
		buttonVolumeDown.setTag("34");
		buttonVolumePress.setTag("35");
		
		buttonNavRight.setTag("38");
		buttonNavLeft.setTag("39");
		buttonNavPress.setTag("40");
		
		buttonFM.setTag("43");
		buttonAM.setTag("46");
		
		buttonPhone.setTag("49");
		
		button1.setOnTouchListener(touchTripleAction);
		button1.setOnLongClickListener(longTripleAction);
		button2.setOnTouchListener(touchTripleAction);
		button2.setOnLongClickListener(longTripleAction);
		button3.setOnTouchListener(touchTripleAction);
		button3.setOnLongClickListener(longTripleAction);
		button4.setOnTouchListener(touchTripleAction);
		button4.setOnLongClickListener(longTripleAction);
		button5.setOnTouchListener(touchTripleAction);
		button5.setOnLongClickListener(longTripleAction);
		button6.setOnTouchListener(touchTripleAction);
		button6.setOnLongClickListener(longTripleAction);
		
		buttonTuneLeft.setOnTouchListener(touchTripleAction);
		buttonTuneLeft.setOnLongClickListener(longTripleAction);
		buttonTuneRight.setOnTouchListener(touchTripleAction);
		buttonTuneRight.setOnLongClickListener(longTripleAction);
		
		buttonMode.setOnTouchListener(touchTripleAction);
		buttonMode.setOnLongClickListener(longTripleAction);
		
		buttonTone.setOnTouchListener(touchTripleAction);
		buttonTone.setOnLongClickListener(longTripleAction);
		buttonSelect.setOnTouchListener(touchTripleAction);
		buttonSelect.setOnLongClickListener(longTripleAction);
        
		// Volume buttons don't have a press release, so just set a normal onClick listener. 
        buttonVolumeUp.setOnClickListener(clickSingleAction);
        buttonVolumeDown.setOnClickListener(clickSingleAction);
        buttonVolumePress.setOnTouchListener(touchTripleAction);
        buttonVolumePress.setOnLongClickListener(longTripleAction);
        
        buttonNavLeft.setOnClickListener(clickSingleAction);
        buttonNavRight.setOnClickListener(clickSingleAction);
        buttonNavPress.setOnTouchListener(touchTripleAction);
        buttonNavPress.setOnLongClickListener(longTripleAction);
        
        buttonFM.setOnTouchListener(touchTripleAction);
        buttonFM.setOnLongClickListener(longTripleAction);
		
        buttonAM.setOnTouchListener(touchTripleAction);
        buttonAM.setOnLongClickListener(longTripleAction);
        
        buttonPhone.setOnTouchListener(touchTripleAction);
        buttonPhone.setOnLongClickListener(longTripleAction);
        
    }

    @Override
    protected void onResume() {
    	super.onResume();
    	final IntentFilter theFilter = new IntentFilter();
    	theFilter.addAction("me.bniles.ibus.inputMessageBroadcast");
    	theFilter.setPriority(99);
    	messageReceiver = new MessageReceiver(this);
    	/* messageReceiver = new BroadcastReceiver() {

    		@Override
    		public void onReceive(Context context, Intent intent) {
    			Toast.makeText(context, "Broadcast Intent processed by receiver.", Toast.LENGTH_LONG).show();
    			abortBroadcast();
    		}
    	}; */

    	if (! isReceiverRegistered) {
    		registerReceiver(messageReceiver, theFilter);
    		isReceiverRegistered = true;
    	}
    }

    @Override
    protected void onPause() {
    	super.onPause();
    	if (isReceiverRegistered) {
    		try {
    			unregisterReceiver(messageReceiver);
    		} catch (IllegalArgumentException e) {
    			// Do nothing.
    		}
    		isReceiverRegistered = false;
    	}
    }

    // Use this broadcastOutput to send a literal byte stream. Set messageType to 0x00.
    /* private void broadcastOutput(byte[] outputMessage) {
    	Intent intent = new Intent();
		intent.setAction("me.bniles.ibus.addOutputMessage");
		intent.putExtra("iBusMessageType", 255);
		intent.putExtra("iBusOutputMessage", outputMessage);
		sendOrderedBroadcast(intent, null);
    } */
    
    private void broadcastOutput(int messageType) {
    	Intent intent = new Intent();
    	intent.setAction("me.bniles.ibus.addOutputMessage");
		intent.putExtra("iBusMessageType", messageType);
		sendOrderedBroadcast(intent, null);
    }
}