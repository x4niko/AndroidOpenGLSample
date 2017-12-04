package cn.autoref.androidopenglsample.entities;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import cn.autoref.androidopenglsample.utils.ShaderUtil;

/**
 * Created by admin on 2017/12/1.
 */
public class Triangle {
    private final String vertexShaderCode =
            "attribute vec4 vPosition;" +
                    "void main() {" +
                    "  gl_Position = vPosition;" +
                    "}";

    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "void main() {" +
                    "  gl_FragColor = vColor;" +
                    "}";

    // 每个顶点的坐标数
    private final int COORDS_PER_VERTEX = 3;
    // 逆时针方向排序
    private float triangleCoords[] = {
            0.0f,  0.5f, 0.0f,   // 顶部
            -0.5f, -0.5f, 0.0f,   // 左下
            0.5f, -0.5f, 0.0f    // 右下
    };

    // 设置 R G B A 的值
    float color[] = { 0.5f, 0.5f, 0.5f, 1.0f };

    private int mProgram;
    private  int mPositionHandle;
    private int mColorHandle;
    private FloatBuffer vertexBuffer;

    private final int vertexCount = triangleCoords.length / COORDS_PER_VERTEX;
    // 每个顶点4字节
    private final int vertexStride = COORDS_PER_VERTEX * 4;

    public Triangle() {
        // 加载、编译shader
        int vertexShader = ShaderUtil.loadShader(GLES20.GL_VERTEX_SHADER,
                vertexShaderCode);
        int fragmentShader = ShaderUtil.loadShader(GLES20.GL_FRAGMENT_SHADER,
                fragmentShaderCode);

        // 创建Program，用来绘制shader
        mProgram = GLES20.glCreateProgram();
        // 将vertex shader添加到program
        GLES20.glAttachShader(mProgram, vertexShader);
        // 将fragment shader添加到program
        GLES20.glAttachShader(mProgram, fragmentShader);
        // 链接program
        GLES20.glLinkProgram(mProgram);

        // 初始化顶点字节缓冲区,每个float值占4个字节
        ByteBuffer bb = ByteBuffer.allocateDirect(triangleCoords.length * 4);
        // 使用设备硬件的本地字节序
        bb.order(ByteOrder.nativeOrder());
        // 创建浮点缓冲区
        vertexBuffer = bb.asFloatBuffer();
        // 将坐标值添加进浮点缓冲区
        vertexBuffer.put(triangleCoords);
        // 设置从第一个坐标值开始读
        vertexBuffer.position(0);
    }

    public void draw() {
        // 使用Program
        GLES20.glUseProgram(mProgram);

        // 获取指向vertex shader的成员vPosition的handle
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
        // 启用一个指向三角形的顶点数组的handle
        GLES20.glEnableVertexAttribArray(mPositionHandle);
        // 准备三角形的坐标数据
        GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false,
                vertexStride, vertexBuffer);
        // 获取指向fragment shader的成员vColor的handle
        mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");

        // 绘制三角形
        GLES20.glUniform4fv(mColorHandle, 1, color, 0);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);
        // 禁用指向三角形的顶点数组
        GLES20.glDisableVertexAttribArray(mPositionHandle);
    }
}
