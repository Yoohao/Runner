package com.yoyo.runner.objects;

import com.yoyo.runner.data.VertexArray;
import com.yoyo.runner.programs.ColorShaderProgram;

import static android.opengl.GLES20.GL_LINES;
import static android.opengl.GLES20.GL_POINTS;
import static android.opengl.GLES20.GL_TRIANGLE_FAN;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glLineWidth;
import static com.yoyo.runner.Constants.BYTES_PER_FLOAT;

/**
 * Created by Yo on 2016/11/28.
 */

public class Table2 {
    private static final int POSITION_COMPONENT_COUNT = 2;
    private static final int STRIDE = POSITION_COMPONENT_COUNT * BYTES_PER_FLOAT;

    private  static final float[] VERTEX_DATA = {
            // Order of coordinates: X, Y, R, G, B
            // Triangle Fan
            0f,    1f,
            -0.5f, 0f,
    };
    private final VertexArray vertexArray;

    public Table2(){
        vertexArray = new VertexArray(VERTEX_DATA);
    }

    public void bindData(ColorShaderProgram colorShaderProgram){
        vertexArray.setVertexAttribPointer(0, colorShaderProgram.getPositionAttributeLocation(),
                POSITION_COMPONENT_COUNT,
                STRIDE);
    }

    public void draw(){
        glDrawArrays(GL_LINES, 0, 2);
    }
}
