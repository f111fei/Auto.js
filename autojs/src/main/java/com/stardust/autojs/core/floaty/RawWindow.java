package com.stardust.autojs.core.floaty;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;
import android.view.MotionEvent;
import android.util.AttributeSet;

import com.stardust.app.GlobalAppContext;
import com.stardust.autojs.R;
import com.stardust.autojs.core.console.GlobalConsole;
import com.stardust.autojs.core.ui.inflater.inflaters.Exceptions;
import com.stardust.autojs.runtime.ScriptRuntime;
import com.stardust.autojs.runtime.exception.ScriptInterruptedException;
import com.stardust.automator.GlobalActionAutomator;
import com.stardust.concurrent.VolatileBox;
import com.stardust.concurrent.VolatileDispose;
import com.stardust.enhancedfloaty.FloatyService;
import com.stardust.enhancedfloaty.FloatyWindow;
import com.stardust.enhancedfloaty.WindowBridge;
import com.stardust.enhancedfloaty.util.WindowTypeCompat;

import androidx.annotation.RequiresApi;
import kotlin.internal.LowPriorityInOverloadResolution;

public class RawWindow extends FloatyWindow {

    public interface RawFloaty {

        View inflateWindowView(FloatyService service, mOwnView parent);
    }

    public class mOwnView extends FrameLayout {
        public mOwnView(Context context) {
            super(context);
        }

        public mOwnView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public mOwnView(FrameLayout layout) {
            super(layout.getContext());
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public mOwnView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr, 0);
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public mOwnView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
        }

        @Override
        public boolean dispatchTouchEvent(MotionEvent event) {
            int s = 2;
            s++;
            return super.dispatchTouchEvent(event);
        }

    }

    mOwnView mTestView;

    private VolatileDispose<RuntimeException> mInflateException = new VolatileDispose<>();
    private RawFloaty mRawFloaty;
    private View mContentView;

    public RawWindow(RawFloaty rawFloaty) {
        mRawFloaty = rawFloaty;
    }

    @Override
    public void onCreate(FloatyService floatyService, WindowManager windowManager) {
        try {
            super.onCreate(floatyService, windowManager);
        } catch (RuntimeException e) {
            mInflateException.setAndNotify(e);
            return;
        }

        getWindowView().setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                Toast.makeText(getWindowView().getContext(), "我是悬浮框", Toast.LENGTH_SHORT).show();

                return false;
            }
        });

        mInflateException.setAndNotify(Exceptions.NO_EXCEPTION);
    }

    @Override
    protected View onCreateView(FloatyService floatyService) {
        View  sasaw = View.inflate(floatyService, R.layout.raw_window, null);
        ViewGroup gggoup = (ViewGroup)(sasaw);
        mOwnView windowView = new mOwnView(GlobalAppContext.get());
        mContentView = mRawFloaty.inflateWindowView(floatyService, windowView);
        return windowView;
    }

    public RuntimeException waitForCreation() {
        return mInflateException.blockedGetOrThrow(ScriptInterruptedException.class);
    }

    public View getContentView() {
        return mContentView;
    }

    @Override
    protected WindowManager.LayoutParams onCreateWindowLayoutParams() {
        int flags =
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
                        | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                        | WindowManager.LayoutParams.FLAG_FULLSCREEN
                        | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            flags |= WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        }
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowTypeCompat.getWindowType(),
                flags,
                PixelFormat.TRANSLUCENT);
        layoutParams.gravity = Gravity.TOP | Gravity.START;
        return layoutParams;
    }

    public void disableWindowFocus() {
        WindowManager.LayoutParams windowLayoutParams = getWindowLayoutParams();
        windowLayoutParams.flags |= WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        updateWindowLayoutParams(windowLayoutParams);
    }

    public void requestWindowFocus() {
        WindowManager.LayoutParams windowLayoutParams = getWindowLayoutParams();
        windowLayoutParams.flags &= ~WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        updateWindowLayoutParams(windowLayoutParams);
        getWindowView().requestLayout();
    }

    public void setTouchable(boolean touchable) {
        WindowManager.LayoutParams windowLayoutParams = getWindowLayoutParams();
        if (touchable) {
            windowLayoutParams.flags &= ~WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
        } else {
            windowLayoutParams.flags |= WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
        }
        updateWindowLayoutParams(windowLayoutParams);
    }

}
