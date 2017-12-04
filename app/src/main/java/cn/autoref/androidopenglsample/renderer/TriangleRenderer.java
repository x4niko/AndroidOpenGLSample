package cn.autoref.androidopenglsample.renderer;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import cn.autoref.androidopenglsample.entities.Triangle;

/**
 * Created by niko on 2017/11/28.
 */

public class TriangleRenderer implements GLSurfaceView.Renderer {
    private Triangle mTriangle;

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        mTriangle = new Triangle();
        //设置背景色（r,g,b,a），白色不透明
        GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        //绘制窗口
        GLES20.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        //重绘背景色
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        mTriangle.draw();
    }
}
