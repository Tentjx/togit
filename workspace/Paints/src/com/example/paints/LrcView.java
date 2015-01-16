package com.example.paints;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.widget.TextView;

public class LrcView extends TextView {
	private Paint loseFocusPaint;
	private Paint getFocusPaint;
	private List<String> wordsList = new ArrayList<String>();
	private int mIndex = 0;
	private float mX = 0;
	private float mMiddleY = 0;
	private float mY = 0;
	private static final int DY = 50;

	public LrcView(Context context) {
		super(context);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		canvas.drawColor(Color.BLACK);
		Paint paint1 = loseFocusPaint;
		paint1.setTextAlign(Paint.Align.CENTER);

		Paint paint2 = getFocusPaint;
		paint2.setTextAlign(Paint.Align.CENTER);

		canvas.drawText((String) wordsList.get(mIndex), mX, mY, paint2);

		int alphaValue = 25;
		float tempY = mMiddleY;
		for (int i = mIndex - 1; i >= 0; i--) {
			tempY -= DY;
			if (tempY < 0) {
				break;

		}
			paint1.setColor(Color.argb(255 - alphaValue, 245, 245, 245));
			canvas.drawText(wordsList.get(i), mX, tempY, paint1);
			alphaValue += 25;
		}
		alphaValue = 25;
		tempY = mMiddleY;
		for (int i = mIndex + 1, len = wordsList.size(); i < len; i++) {
		tempY += DY;
		if (tempY > mY) {
		break;
		}
		paint1.setColor(Color.argb(255 - alphaValue, 245, 245, 245));
		canvas.drawText(wordsList.get(i), mX, tempY, paint1);
		alphaValue += 25;
		}
		mIndex++;
		}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// TODO Auto-generated method stub
		super.onSizeChanged(w, h, oldw, oldh);
		
		mX = w * 0.5f;
		mY = h;
		mMiddleY = h * 0.3f;
		}

	@SuppressLint("SdCardPath")
		private void init() throws IOException {
		setFocusable(true);

		LrcHandle lrcHandler = new LrcHandle();
		lrcHandler.readLRC("/storage/sdcard/baidu/丁当-痛快.lrc");
		wordsList = lrcHandler.getWords();

		loseFocusPaint = new Paint();
		loseFocusPaint.setAntiAlias(true);
		loseFocusPaint.setTextSize(22);
		loseFocusPaint.setColor(Color.WHITE);
		loseFocusPaint.setTypeface(Typeface.SERIF);

		getFocusPaint = new Paint();
		getFocusPaint.setAntiAlias(true);
		getFocusPaint.setColor(Color.YELLOW);
		getFocusPaint.setTextSize(30);
		getFocusPaint.setTypeface(Typeface.SANS_SERIF);
		}
	}
	

