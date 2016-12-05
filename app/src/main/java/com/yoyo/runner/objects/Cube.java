package com.yoyo.runner.objects;

import com.yoyo.runner.data.VertexArray;
import com.yoyo.runner.programs.ColorShaderProgram;

import static android.opengl.GLES20.GL_LINES;
import static android.opengl.GLES20.GL_TRIANGLE_FAN;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glLineWidth;
import static com.yoyo.runner.Constants.BYTES_PER_FLOAT;

/**
 * Created by Yo on 2016/11/30.
 */

public class Cube {
    private static final int POSITION_COMPONENT_COUNT = 3;
    private static final int STRIDE = POSITION_COMPONENT_COUNT * BYTES_PER_FLOAT;

    private  static final float[] VERTEX_DATA = {
            0.25f, 10f, 0.25f,
            -0.25f, 10f, 0.25f,
            -0.25f, -10f, 0.25f,
            0.25f, -10f, 0.25f,
            0.25f, 10f, 0.25f,

            0.25f, 0.25f, -0.25f,
            -0.25f, 0.25f, -0.25f,
            -0.25f, -0.25f, -0.25f,
            0.25f, -0.25f, -0.25f,
            0.25f, 0.25f, -0.25f,

            0.25f, 0.25f, 0.25f,
            0.25f, 0.25f, -0.25f,
            -0.25f, 0.25f, 0.25f,
            -0.25f, 0.25f, -0.25f,
            -0.25f, -0.25f, 0.25f,
            -0.25f, -0.25f, -0.25f,
            0.25f, -0.25f, 0.25f,
            0.25f, -0.25f, -0.25f
    };
    private final VertexArray vertexArray;

    public Cube(){
        vertexArray = new VertexArray(VERTEX_DATA);
    }

    public void bindData(ColorShaderProgram colorShaderProgram){
        vertexArray.setVertexAttribPointer(0, colorShaderProgram.getPositionAttributeLocation(),
                POSITION_COMPONENT_COUNT,
                STRIDE);
    }

    public void draw(){
        glLineWidth(10);
        for(int i = 0; i<4; i++){
            glDrawArrays(GL_LINES, i, 2);
            glDrawArrays(GL_LINES, i+5, 2);
        }

        for(int i = 0; i<4; i++){
            glDrawArrays(GL_LINES, 10 + i*2, 2);
        }
    }
}
