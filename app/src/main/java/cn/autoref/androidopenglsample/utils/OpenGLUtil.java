package cn.autoref.androidopenglsample.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by niko on 2017/11/28.
 */
public class OpenGLUtil {

    /**
     * 检查系统是否支持OPENGL ES 2.0
     * @param context
     * @return
     */
    public static boolean isSupportGLES20(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();
        return configurationInfo.reqGlEsVersion >= 0x20000;
    }

    /**
     * 分配本地内存块，并把Java的数据复制到本地内存
     * @param vertexes 顶点坐标数组
     * @return 获取浮点形缓冲数据
     */
    public static FloatBuffer getFloatBuffer(float[] vertexes) {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(vertexes.length * 4);
        byteBuffer.order(ByteOrder.nativeOrder());
        FloatBuffer buffer = byteBuffer.asFloatBuffer();
        //写入数组
        buffer.put(vertexes);
        return buffer;
    }

    /**
     *
     * @param type 着色器类型：
     *             vertex shader：GLES20.GL_VERTEX_SHADER，
     *             fragment shader：GLES20.GL_FRAGMENT_SHADER
     * @param shaderCode shader代码
     * @return shaderId
     */
    public static int loadShader(int type, String shaderCode){
        int shaderId = GLES20.glCreateShader(type);
        // 添加、编译shader
        GLES20.glShaderSource(shaderId, shaderCode);
        GLES20.glCompileShader(shaderId);
        return shaderId;
    }
}
