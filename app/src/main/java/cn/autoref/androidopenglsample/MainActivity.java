package cn.autoref.androidopenglsample;

import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.autoref.androidopenglsample.renderer.TriangleRenderer;

public class MainActivity extends AppCompatActivity {
    private GLSurfaceView glSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        glSurfaceView = (GLSurfaceView) findViewById(R.id.glsurfaceview);
        //设置Context版本
        glSurfaceView.setEGLContextClientVersion(2);
        //设置Renderer用于绘图
        glSurfaceView.setRenderer(new TriangleRenderer());
        //设置调用requestRender()时才刷新View
        glSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    @Override
    protected void onResume() {
        super.onResume();
        glSurfaceView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        glSurfaceView.onPause();
    }
}
