package cn.autoref.androidopenglsample.entities;

import android.content.Context;
import android.opengl.GLES20;

import java.nio.FloatBuffer;

import cn.autoref.androidopenglsample.utils.AssetsUtil;
import cn.autoref.androidopenglsample.utils.OpenGLUtil;

/**
 * Created by niko on 2017/12/11.
 */
public class LineEntity extends BaseShape {

    // 每个顶点有3个分量x，y，z
    private final int COORDS_PER_VERTEX = 3;
    // 顶点数组，每三个值为一个顶点
    private float[] vertexArray = {
            0.0f,  0.5f, 0.0f,
            -0.5f, -0.5f, 0.0f,
            0.5f, -0.5f, 0.0f,
            0.0f,  0.5f, 0.0f
    };

    private int mProgram;
    private FloatBuffer vertexBuffer;

    public LineEntity(Context context) {
        //获取浮点缓冲数据
        vertexBuffer = OpenGLUtil.getFloatBuffer(vertexArray);

        String vertexShaderCode = AssetsUtil.getAssetsString(context, "simple_vertex_shader.glsl");
        String fragmentShaderCode = AssetsUtil.getAssetsString(context, "simple_fragment_shader.glsl");
        // 加载、编译shader
        int vertexShaderId = OpenGLUtil.loadShader(GLES20.GL_VERTEX_SHADER,
                vertexShaderCode);
        int fragmentShaderId = OpenGLUtil.loadShader(GLES20.GL_FRAGMENT_SHADER,
                fragmentShaderCode);

        // 创建Program
        mProgram = GLES20.glCreateProgram();
        // 将顶点着色器添加到Program
        GLES20.glAttachShader(mProgram, vertexShaderId);
        // 将片段着色器添加到Program
        GLES20.glAttachShader(mProgram, fragmentShaderId);
        // 链接Program
        GLES20.glLinkProgram(mProgram);
    }

    @Override
    public void onDraw() {
        // 使用Program
        GLES20.glUseProgram(mProgram);

        // 获取属性vPosition的位置
        int mPositionLocation = GLES20.glGetAttribLocation(mProgram, "vPosition");
        // 启用顶点数组
        GLES20.glEnableVertexAttribArray(mPositionLocation);
        //设置读取位置
        vertexBuffer.position(0);
        // 关联属性与顶点数据的数组
        GLES20.glVertexAttribPointer(mPositionLocation, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false,
                0, vertexBuffer);

        // 获取uniform的位置
        int mColorLocation = GLES20.glGetUniformLocation(mProgram, "vColor");
        // 更新着色器代码中的vColor值
        GLES20.glUniform4f(mColorLocation, 1f, 0f, 0f, 1f);
        // 绘制红色直线
        GLES20.glDrawArrays(GLES20.GL_LINES, 0, 2);

        // 绘制绿色直线
        GLES20.glUniform4f(mColorLocation, 0f, 1f, 0f, 1f);
        GLES20.glDrawArrays(GLES20.GL_LINES, 1, 2);

        // 绘制蓝色直线
        GLES20.glUniform4f(mColorLocation, 0f, 0f, 1f, 1f);
        GLES20.glDrawArrays(GLES20.GL_LINES, 2, 2);
    }
}
