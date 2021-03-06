package com.android.internal.view;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.CompletionInfo;
import android.view.inputmethod.CorrectionInfo;
import android.view.inputmethod.ExtractedTextRequest;
import android.view.inputmethod.InputConnection;
import com.android.internal.view.IInputContext.Stub;
import java.lang.ref.WeakReference;

public class IInputConnectionWrapper extends Stub {
    private static final int DO_BEGIN_BATCH_EDIT = 90;
    private static final int DO_CLEAR_META_KEY_STATES = 130;
    private static final int DO_COMMIT_COMPLETION = 55;
    private static final int DO_COMMIT_CORRECTION = 56;
    private static final int DO_COMMIT_TEXT = 50;
    private static final int DO_DELETE_SURROUNDING_TEXT = 80;
    private static final int DO_END_BATCH_EDIT = 95;
    private static final int DO_FINISH_COMPOSING_TEXT = 65;
    private static final int DO_GET_CURSOR_CAPS_MODE = 30;
    private static final int DO_GET_EXTRACTED_TEXT = 40;
    private static final int DO_GET_SELECTED_TEXT = 25;
    private static final int DO_GET_TEXT_AFTER_CURSOR = 10;
    private static final int DO_GET_TEXT_BEFORE_CURSOR = 20;
    private static final int DO_PERFORM_CONTEXT_MENU_ACTION = 59;
    private static final int DO_PERFORM_EDITOR_ACTION = 58;
    private static final int DO_PERFORM_PRIVATE_COMMAND = 120;
    private static final int DO_REPORT_FULLSCREEN_MODE = 100;
    private static final int DO_REQUEST_UPDATE_CURSOR_ANCHOR_INFO = 140;
    private static final int DO_SEND_KEY_EVENT = 70;
    private static final int DO_SET_COMPOSING_REGION = 63;
    private static final int DO_SET_COMPOSING_TEXT = 60;
    private static final int DO_SET_SELECTION = 57;
    static final String TAG = "IInputConnectionWrapper";
    private Handler mH = new MyHandler(this.mMainLooper);
    private WeakReference<InputConnection> mInputConnection;
    private Looper mMainLooper;

    class MyHandler extends Handler {
        MyHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message msg) {
            IInputConnectionWrapper.this.executeMessage(msg);
        }
    }

    static class SomeArgs {
        Object arg1;
        Object arg2;
        IInputContextCallback callback;
        int seq;

        SomeArgs() {
        }
    }

    public IInputConnectionWrapper(Looper mainLooper, InputConnection conn) {
        this.mInputConnection = new WeakReference(conn);
        this.mMainLooper = mainLooper;
    }

    public boolean isActive() {
        return true;
    }

    public void getTextAfterCursor(int length, int flags, int seq, IInputContextCallback callback) {
        dispatchMessage(obtainMessageIISC(10, length, flags, seq, callback));
    }

    public void getTextBeforeCursor(int length, int flags, int seq, IInputContextCallback callback) {
        dispatchMessage(obtainMessageIISC(20, length, flags, seq, callback));
    }

    public void getSelectedText(int flags, int seq, IInputContextCallback callback) {
        dispatchMessage(obtainMessageISC(25, flags, seq, callback));
    }

    public void getCursorCapsMode(int reqModes, int seq, IInputContextCallback callback) {
        dispatchMessage(obtainMessageISC(30, reqModes, seq, callback));
    }

    public void getExtractedText(ExtractedTextRequest request, int flags, int seq, IInputContextCallback callback) {
        dispatchMessage(obtainMessageIOSC(40, flags, request, seq, callback));
    }

    public void commitText(CharSequence text, int newCursorPosition) {
        dispatchMessage(obtainMessageIO(50, newCursorPosition, text));
    }

    public void commitCompletion(CompletionInfo text) {
        dispatchMessage(obtainMessageO(55, text));
    }

    public void commitCorrection(CorrectionInfo info) {
        dispatchMessage(obtainMessageO(56, info));
    }

    public void setSelection(int start, int end) {
        dispatchMessage(obtainMessageII(57, start, end));
    }

    public void performEditorAction(int id) {
        dispatchMessage(obtainMessageII(58, id, 0));
    }

    public void performContextMenuAction(int id) {
        dispatchMessage(obtainMessageII(59, id, 0));
    }

    public void setComposingRegion(int start, int end) {
        dispatchMessage(obtainMessageII(63, start, end));
    }

    public void setComposingText(CharSequence text, int newCursorPosition) {
        dispatchMessage(obtainMessageIO(60, newCursorPosition, text));
    }

    public void finishComposingText() {
        dispatchMessage(obtainMessage(65));
    }

    public void sendKeyEvent(KeyEvent event) {
        dispatchMessage(obtainMessageO(70, event));
    }

    public void clearMetaKeyStates(int states) {
        dispatchMessage(obtainMessageII(130, states, 0));
    }

    public void deleteSurroundingText(int leftLength, int rightLength) {
        dispatchMessage(obtainMessageII(80, leftLength, rightLength));
    }

    public void beginBatchEdit() {
        dispatchMessage(obtainMessage(90));
    }

    public void endBatchEdit() {
        dispatchMessage(obtainMessage(95));
    }

    public void reportFullscreenMode(boolean enabled) {
        int i;
        if (enabled) {
            i = 1;
        } else {
            i = 0;
        }
        dispatchMessage(obtainMessageII(100, i, 0));
    }

    public void performPrivateCommand(String action, Bundle data) {
        dispatchMessage(obtainMessageOO(120, action, data));
    }

    public void requestUpdateCursorAnchorInfo(int cursorUpdateMode, int seq, IInputContextCallback callback) {
        dispatchMessage(obtainMessageISC(140, cursorUpdateMode, seq, callback));
    }

    void dispatchMessage(Message msg) {
        if (Looper.myLooper() == this.mMainLooper) {
            executeMessage(msg);
            msg.recycle();
            return;
        }
        this.mH.sendMessage(msg);
    }

    void executeMessage(Message msg) {
        boolean z = true;
        SomeArgs args;
        InputConnection ic;
        switch (msg.what) {
            case 10:
                args = msg.obj;
                try {
                    ic = (InputConnection) this.mInputConnection.get();
                    if (ic == null || !isActive()) {
                        Log.w(TAG, "getTextAfterCursor on inactive InputConnection");
                        args.callback.setTextAfterCursor(null, args.seq);
                        return;
                    }
                    args.callback.setTextAfterCursor(ic.getTextAfterCursor(msg.arg1, msg.arg2), args.seq);
                    return;
                } catch (RemoteException e) {
                    Log.w(TAG, "Got RemoteException calling setTextAfterCursor", e);
                    return;
                }
            case 20:
                args = (SomeArgs) msg.obj;
                try {
                    ic = (InputConnection) this.mInputConnection.get();
                    if (ic == null || !isActive()) {
                        Log.w(TAG, "getTextBeforeCursor on inactive InputConnection");
                        args.callback.setTextBeforeCursor(null, args.seq);
                        return;
                    }
                    args.callback.setTextBeforeCursor(ic.getTextBeforeCursor(msg.arg1, msg.arg2), args.seq);
                    return;
                } catch (RemoteException e2) {
                    Log.w(TAG, "Got RemoteException calling setTextBeforeCursor", e2);
                    return;
                }
            case 25:
                args = (SomeArgs) msg.obj;
                try {
                    ic = (InputConnection) this.mInputConnection.get();
                    if (ic == null || !isActive()) {
                        Log.w(TAG, "getSelectedText on inactive InputConnection");
                        args.callback.setSelectedText(null, args.seq);
                        return;
                    }
                    args.callback.setSelectedText(ic.getSelectedText(msg.arg1), args.seq);
                    return;
                } catch (RemoteException e22) {
                    Log.w(TAG, "Got RemoteException calling setSelectedText", e22);
                    return;
                }
            case 30:
                args = (SomeArgs) msg.obj;
                try {
                    ic = (InputConnection) this.mInputConnection.get();
                    if (ic == null || !isActive()) {
                        Log.w(TAG, "getCursorCapsMode on inactive InputConnection");
                        args.callback.setCursorCapsMode(0, args.seq);
                        return;
                    }
                    args.callback.setCursorCapsMode(ic.getCursorCapsMode(msg.arg1), args.seq);
                    return;
                } catch (RemoteException e222) {
                    Log.w(TAG, "Got RemoteException calling setCursorCapsMode", e222);
                    return;
                }
            case 40:
                args = (SomeArgs) msg.obj;
                try {
                    ic = (InputConnection) this.mInputConnection.get();
                    if (ic == null || !isActive()) {
                        Log.w(TAG, "getExtractedText on inactive InputConnection");
                        args.callback.setExtractedText(null, args.seq);
                        return;
                    }
                    args.callback.setExtractedText(ic.getExtractedText((ExtractedTextRequest) args.arg1, msg.arg1), args.seq);
                    return;
                } catch (RemoteException e2222) {
                    Log.w(TAG, "Got RemoteException calling setExtractedText", e2222);
                    return;
                }
            case 50:
                ic = (InputConnection) this.mInputConnection.get();
                if (ic == null || !isActive()) {
                    Log.w(TAG, "commitText on inactive InputConnection");
                    return;
                } else {
                    ic.commitText((CharSequence) msg.obj, msg.arg1);
                    return;
                }
            case 55:
                ic = (InputConnection) this.mInputConnection.get();
                if (ic == null || !isActive()) {
                    Log.w(TAG, "commitCompletion on inactive InputConnection");
                    return;
                } else {
                    ic.commitCompletion((CompletionInfo) msg.obj);
                    return;
                }
            case 56:
                ic = (InputConnection) this.mInputConnection.get();
                if (ic == null || !isActive()) {
                    Log.w(TAG, "commitCorrection on inactive InputConnection");
                    return;
                } else {
                    ic.commitCorrection((CorrectionInfo) msg.obj);
                    return;
                }
            case 57:
                ic = (InputConnection) this.mInputConnection.get();
                if (ic == null || !isActive()) {
                    Log.w(TAG, "setSelection on inactive InputConnection");
                    return;
                } else {
                    ic.setSelection(msg.arg1, msg.arg2);
                    return;
                }
            case 58:
                ic = (InputConnection) this.mInputConnection.get();
                if (ic == null || !isActive()) {
                    Log.w(TAG, "performEditorAction on inactive InputConnection");
                    return;
                } else {
                    ic.performEditorAction(msg.arg1);
                    return;
                }
            case 59:
                ic = (InputConnection) this.mInputConnection.get();
                if (ic == null || !isActive()) {
                    Log.w(TAG, "performContextMenuAction on inactive InputConnection");
                    return;
                } else {
                    ic.performContextMenuAction(msg.arg1);
                    return;
                }
            case 60:
                ic = (InputConnection) this.mInputConnection.get();
                if (ic == null || !isActive()) {
                    Log.w(TAG, "setComposingText on inactive InputConnection");
                    return;
                } else {
                    ic.setComposingText((CharSequence) msg.obj, msg.arg1);
                    return;
                }
            case 63:
                ic = (InputConnection) this.mInputConnection.get();
                if (ic == null || !isActive()) {
                    Log.w(TAG, "setComposingRegion on inactive InputConnection");
                    return;
                } else {
                    ic.setComposingRegion(msg.arg1, msg.arg2);
                    return;
                }
            case 65:
                ic = (InputConnection) this.mInputConnection.get();
                if (ic == null) {
                    Log.w(TAG, "finishComposingText on inactive InputConnection");
                    return;
                } else {
                    ic.finishComposingText();
                    return;
                }
            case 70:
                ic = (InputConnection) this.mInputConnection.get();
                if (ic == null || !isActive()) {
                    Log.w(TAG, "sendKeyEvent on inactive InputConnection");
                    return;
                } else {
                    ic.sendKeyEvent((KeyEvent) msg.obj);
                    return;
                }
            case 80:
                ic = (InputConnection) this.mInputConnection.get();
                if (ic == null || !isActive()) {
                    Log.w(TAG, "deleteSurroundingText on inactive InputConnection");
                    return;
                } else {
                    ic.deleteSurroundingText(msg.arg1, msg.arg2);
                    return;
                }
            case 90:
                ic = (InputConnection) this.mInputConnection.get();
                if (ic == null || !isActive()) {
                    Log.w(TAG, "beginBatchEdit on inactive InputConnection");
                    return;
                } else {
                    ic.beginBatchEdit();
                    return;
                }
            case 95:
                ic = (InputConnection) this.mInputConnection.get();
                if (ic == null || !isActive()) {
                    Log.w(TAG, "endBatchEdit on inactive InputConnection");
                    return;
                } else {
                    ic.endBatchEdit();
                    return;
                }
            case 100:
                ic = (InputConnection) this.mInputConnection.get();
                if (ic == null) {
                    Log.w(TAG, "reportFullscreenMode on inexistent InputConnection");
                    return;
                }
                if (msg.arg1 != 1) {
                    z = false;
                }
                ic.reportFullscreenMode(z);
                return;
            case 120:
                ic = (InputConnection) this.mInputConnection.get();
                if (ic == null || !isActive()) {
                    Log.w(TAG, "performPrivateCommand on inactive InputConnection");
                    return;
                }
                args = (SomeArgs) msg.obj;
                ic.performPrivateCommand((String) args.arg1, (Bundle) args.arg2);
                return;
            case 130:
                ic = (InputConnection) this.mInputConnection.get();
                if (ic == null || !isActive()) {
                    Log.w(TAG, "clearMetaKeyStates on inactive InputConnection");
                    return;
                } else {
                    ic.clearMetaKeyStates(msg.arg1);
                    return;
                }
            case 140:
                args = (SomeArgs) msg.obj;
                try {
                    ic = (InputConnection) this.mInputConnection.get();
                    if (ic == null || !isActive()) {
                        Log.w(TAG, "requestCursorAnchorInfo on inactive InputConnection");
                        args.callback.setRequestUpdateCursorAnchorInfoResult(false, args.seq);
                        return;
                    }
                    args.callback.setRequestUpdateCursorAnchorInfoResult(ic.requestCursorUpdates(msg.arg1), args.seq);
                    return;
                } catch (RemoteException e22222) {
                    Log.w(TAG, "Got RemoteException calling requestCursorAnchorInfo", e22222);
                    return;
                }
            default:
                Log.w(TAG, "Unhandled message code: " + msg.what);
                return;
        }
    }

    Message obtainMessage(int what) {
        return this.mH.obtainMessage(what);
    }

    Message obtainMessageII(int what, int arg1, int arg2) {
        return this.mH.obtainMessage(what, arg1, arg2);
    }

    Message obtainMessageO(int what, Object arg1) {
        return this.mH.obtainMessage(what, 0, 0, arg1);
    }

    Message obtainMessageISC(int what, int arg1, int seq, IInputContextCallback callback) {
        SomeArgs args = new SomeArgs();
        args.callback = callback;
        args.seq = seq;
        return this.mH.obtainMessage(what, arg1, 0, args);
    }

    Message obtainMessageIISC(int what, int arg1, int arg2, int seq, IInputContextCallback callback) {
        SomeArgs args = new SomeArgs();
        args.callback = callback;
        args.seq = seq;
        return this.mH.obtainMessage(what, arg1, arg2, args);
    }

    Message obtainMessageOSC(int what, Object arg1, int seq, IInputContextCallback callback) {
        SomeArgs args = new SomeArgs();
        args.arg1 = arg1;
        args.callback = callback;
        args.seq = seq;
        return this.mH.obtainMessage(what, 0, 0, args);
    }

    Message obtainMessageIOSC(int what, int arg1, Object arg2, int seq, IInputContextCallback callback) {
        SomeArgs args = new SomeArgs();
        args.arg1 = arg2;
        args.callback = callback;
        args.seq = seq;
        return this.mH.obtainMessage(what, arg1, 0, args);
    }

    Message obtainMessageIO(int what, int arg1, Object arg2) {
        return this.mH.obtainMessage(what, arg1, 0, arg2);
    }

    Message obtainMessageOO(int what, Object arg1, Object arg2) {
        SomeArgs args = new SomeArgs();
        args.arg1 = arg1;
        args.arg2 = arg2;
        return this.mH.obtainMessage(what, 0, 0, args);
    }
}
