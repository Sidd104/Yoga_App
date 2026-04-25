package com.example.yoga_app;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.google.mlkit.vision.pose.Pose;
import com.google.mlkit.vision.pose.PoseLandmark;

public class PoseOverlay extends View {

    private Pose pose;
    private Paint jointPaint;
    private Paint linePaint;

    private boolean bodyVisible = false;
    private BodyListener listener;

    public interface BodyListener {
        void onBodyDetected(boolean detected);
    }

    public void setBodyListener(BodyListener listener) {
        this.listener = listener;
    }

    public PoseOverlay(Context context, AttributeSet attrs) {
        super(context, attrs);

        jointPaint = new Paint();
        jointPaint.setColor(Color.GREEN);
        jointPaint.setStyle(Paint.Style.FILL);
        jointPaint.setStrokeWidth(12f);

        linePaint = new Paint();
        linePaint.setColor(Color.YELLOW);
        linePaint.setStrokeWidth(6f);
    }

    public void setPose(Pose pose) {
        this.pose = pose;

        boolean detected = pose != null &&
                pose.getAllPoseLandmarks().size() > 10;

        if (detected != bodyVisible) {
            bodyVisible = detected;
            if (listener != null) listener.onBodyDetected(bodyVisible);
        }

        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (pose == null) return;

        for (PoseLandmark landmark : pose.getAllPoseLandmarks()) {
            canvas.drawCircle(
                    landmark.getPosition().x,
                    landmark.getPosition().y,
                    10f,
                    jointPaint
            );
        }

        drawLine(canvas, PoseLandmark.LEFT_SHOULDER, PoseLandmark.RIGHT_SHOULDER);
        drawLine(canvas, PoseLandmark.LEFT_HIP, PoseLandmark.RIGHT_HIP);
        drawLine(canvas, PoseLandmark.LEFT_SHOULDER, PoseLandmark.LEFT_ELBOW);
        drawLine(canvas, PoseLandmark.LEFT_ELBOW, PoseLandmark.LEFT_WRIST);
        drawLine(canvas, PoseLandmark.RIGHT_SHOULDER, PoseLandmark.RIGHT_ELBOW);
        drawLine(canvas, PoseLandmark.RIGHT_ELBOW, PoseLandmark.RIGHT_WRIST);
        drawLine(canvas, PoseLandmark.LEFT_HIP, PoseLandmark.LEFT_KNEE);
        drawLine(canvas, PoseLandmark.LEFT_KNEE, PoseLandmark.LEFT_ANKLE);
        drawLine(canvas, PoseLandmark.RIGHT_HIP, PoseLandmark.RIGHT_KNEE);
        drawLine(canvas, PoseLandmark.RIGHT_KNEE, PoseLandmark.RIGHT_ANKLE);
    }

    private void drawLine(Canvas canvas, int start, int end) {
        PoseLandmark s = pose.getPoseLandmark(start);
        PoseLandmark e = pose.getPoseLandmark(end);
        if (s != null && e != null) {
            canvas.drawLine(
                    s.getPosition().x,
                    s.getPosition().y,
                    e.getPosition().x,
                    e.getPosition().y,
                    linePaint
            );
        }
    }
}