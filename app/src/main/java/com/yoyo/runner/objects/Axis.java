package com.yoyo.runner.objects;

import com.yoyo.runner.data.VertexArray;
import com.yoyo.runner.programs.ColorShaderProgram;

import static android.opengl.GLES20.GL_LINES;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glLineWidth;
import static com.yoyo.runner.Constants.BYTES_PER_FLOAT;

/**
 * Created by Yo on 2016/11/30.
 */

public class Axis {
    private static final int POSITION_COMPONENT_COUNT = 3;
    private static final int STRIDE = POSITION_COMPONENT_COUNT * BYTES_PER_FLOAT;

    private  static final float[] VERTEX_DATA = {
            // Order of coordinates: X, Y, R, G, B
            // Triangle Fan
            0f, 0f, 0f,
            0.1f, 0f, 0f,

            0f, 0f, 0f,
            0f, 0.1f, 0f,

            0f, 0f, 0f,
            0f, 0f, 0.1f
    };
    private final VertexArray vertexArray;

    public Axis(){
        vertexArray = new VertexArray(VERTEX_DATA);
        glLineWidth(10);
    }

    public void bindData(ColorShaderProgram colorShaderProgram){
        vertexArray.setVertexAttribPointer(0, colorShaderProgram.getPositionAttributeLocation(),
                POSITION_COMPONENT_COUNT,
                STRIDE);
    }

    public void drawX(){
        glDrawArrays(GL_LINES, 0, 2);
    }
    public void drawY(){
        glDrawArrays(GL_LINES, 2, 2);
    }
    public void drawZ(){
        glDrawArrays(GL_LINES, 4, 2);
    }
}
