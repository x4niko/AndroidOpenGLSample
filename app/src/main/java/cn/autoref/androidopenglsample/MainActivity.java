package cn.autoref.androidopenglsample;

import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import cn.autoref.androidopenglsample.renderer.AutorefRenderer;

public class MainActivity extends AppCompatActivity {
    private GLSurfaceView glSurfaceView;
    private AutorefRenderer renderer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        glSurfaceView = (GLSurfaceView) findViewById(R.id.glsurfaceview);
        //设置Context版本
        glSurfaceView.setEGLContextClientVersion(2);
        //设置Renderer用于绘图
        renderer = new AutorefRenderer(this);
        glSurfaceView.setRenderer(renderer);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        renderer.setCurrentEffect(item.getItemId());
        glSurfaceView.requestRender();
        return true;
    }
}
