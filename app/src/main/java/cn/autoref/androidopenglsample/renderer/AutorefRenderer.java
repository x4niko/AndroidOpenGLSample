package cn.autoref.androidopenglsample.renderer;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.Log;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import cn.autoref.androidopenglsample.R;
import cn.autoref.androidopenglsample.entities.BaseShape;
import cn.autoref.androidopenglsample.entities.LineEntity;
import cn.autoref.androidopenglsample.entities.TriangleEntity;
import cn.autoref.androidopenglsample.entities.VertexEntity;

/**
 * Created by niko on 2017/11/28.
 */
public class AutorefRenderer implements GLSurfaceView.Renderer {
    private final String TAG = AutorefRenderer.class.getSimpleName();
    private Context context;
    private BaseShape shapeEntity;

    // mMVPMatrix是"Model View Projection Matrix"的缩写
    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];

    public AutorefRenderer(Context context) {
        this.context = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        Log.e(TAG, "onSurfaceCreated");
        //设置背景色（r,g,b,a），白色不透明
        GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        Log.e(TAG, "onSurfaceChanged");
        //设置OpenGL场景的大小
        GLES20.glViewport(0, 0, width, height);

//        float aspectRatio = 0f;
//        aspectRatio = (float)width/(float)height;
//        Matrix.frustumM(mProjectionMatrix, 0, -aspectRatio, aspectRatio, -1f, 1f, 3f, 7f);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        Log.e(TAG, "onDrawFrame");
        //清除屏幕
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        // Set the camera position (View matrix)
//        Matrix.setLookAtM(mViewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
        // Calculate the projection and view transformation
//        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);

        shapeEntity = getShape();
        if (null != shapeEntity) {
            shapeEntity.onDraw();
        }
    }

    private BaseShape getShape() {
        switch (type) {
            case R.id.vertex:
                return new VertexEntity(context);
            case R.id.line:
                return new LineEntity(context);
            case R.id.triangle:
                return new TriangleEntity(context);
            default:
                return null;
        }
    }

    private int type = R.id.vertex;
    public void setCurrentEffect(int type) {
        this.type = type;
    }
}
