package cn.autoref.androidopenglsample.utils;

import android.opengl.GLES20;

/**
 * Created by niko on 2017/11/28.
 */
public class ShaderUtil {

    public static int loadShader(int type, String shaderCode){
        //vertex shader类型：GLES20.GL_VERTEX_SHADER
        //fragment shader类型：GLES20.GL_FRAGMENT_SHADER
        int shader = GLES20.glCreateShader(type);

        // 添加、编译shader
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);
        return shader;
    }
}
