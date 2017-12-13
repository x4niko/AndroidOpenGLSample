package cn.autoref.androidopenglsample.entities;

import android.content.Context;
import android.opengl.GLES20;

import java.nio.FloatBuffer;

import cn.autoref.androidopenglsample.utils.AssetsUtil;
import cn.autoref.androidopenglsample.utils.OpenGLUtil;

/**
 * Created by admin on 2017/12/1.
 */
public class TriangleEntity extends BaseShape {
    /*private final String vertexShaderCode =
            // This matrix member variable provides a hook to manipulate
            // the coordinates of the objects that use this vertex shader
            "uniform mat4 uMVPMatrix;" +
                    "attribute vec4 vPosition;" +
                    "void main() {" +
                    // the matrix must be included as a modifier of gl_Position
                    // Note that the uMVPMatrix factor *must be first* in order
                    // for the matrix multiplication product to be correct.
                    "  gl_Position = uMVPMatrix * vPosition;" +
                    "}";*/

    // 每个顶点有3个分量x，y，z
    private final int COORDS_PER_VERTEX = 3;
    // 逆时针方向排序
    private float triangleCoords[] = {
            0.0f,  0.5f, 0.0f,   // 顶部
            -0.5f, -0.5f, 0.0f,   // 左下
            0.5f, -0.5f, 0.0f    // 右下
    };

    private int mProgram;
    private FloatBuffer vertexBuffer;

    public TriangleEntity(Context context) {
        //获取浮点缓冲数据
        vertexBuffer = OpenGLUtil.getFloatBuffer(triangleCoords);

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

    public void onDraw() {
        // 使用Program
        GLES20.glUseProgram(mProgram);

        // 获取指向vertex shader的成员vPosition
        int mPositionLocation = GLES20.glGetAttribLocation(mProgram, "vPosition");
        // 启用顶点数组
        GLES20.glEnableVertexAttribArray(mPositionLocation);
        // 设置读取位置
        vertexBuffer.position(0);
        // 关联属性与顶点数据的数组
        GLES20.glVertexAttribPointer(mPositionLocation, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false,
                0, vertexBuffer);

        // 获取uniform的位置
        int mColorLocation = GLES20.glGetUniformLocation(mProgram, "vColor");
        // 更新着色器代码中的vColor值
        GLES20.glUniform4f(mColorLocation, 0.5f, 0.5f, 0.5f, 1f);
        // 需要读入的顶点数
        int vertexCount = triangleCoords.length / COORDS_PER_VERTEX;
        // 绘制三角形
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);
    }

    /**
     * pass in the calculated transformation matrix
     * @param mvpMatrix
     */
    public void onDraw(float[] mvpMatrix) {
        // 使用Program
        GLES20.glUseProgram(mProgram);

        int mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
        GLES20.glEnableVertexAttribArray(mPositionHandle);
        GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false,
                0, vertexBuffer);

        int mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
        // 更新着色器代码中的vColor值
        GLES20.glUniform4f(mColorHandle, 1f, 0f, 0f, 1f);

        // get handle to shape's transformation matrix
        int mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
        // Pass the projection and view transformation to the shader
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);

        // 绘制三角形
        int vertexCount = triangleCoords.length / COORDS_PER_VERTEX;
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);
        // 禁用指向三角形的顶点数组
        GLES20.glDisableVertexAttribArray(mPositionHandle);
    }
}
