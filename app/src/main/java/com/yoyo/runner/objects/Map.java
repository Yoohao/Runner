package com.yoyo.runner.objects;

import android.util.Log;

import com.yoyo.runner.data.VertexArray;
import com.yoyo.runner.programs.ColorShaderProgram;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL;

import static android.opengl.GLES20.GL_LINES;
import static android.opengl.GLES20.GL_TRIANGLE_FAN;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glGetFloatv;
import static android.opengl.GLES20.glLineWidth;
import static com.yoyo.runner.Constants.BYTES_PER_FLOAT;
import static com.yoyo.runner.Constants.MAP_SIZE;
import static java.lang.Math.floor;
import static java.lang.Math.random;

/**
 * Created by Yo on 2016/12/3.
 */

public class Map {
    private static final int POSITION_COMPONENT_COUNT = 3;
    private static final int STRIDE = POSITION_COMPONENT_COUNT * BYTES_PER_FLOAT;
    private int DIR = 0;
    private static final int[] op = new int[MAP_SIZE];

    private static final float[] VERTEX_DATA_R = {
            -0.2f, -5.5f, -0.15f,//0
            -0.2f, -5.5f, 0.15f,
            5.25f, -5.5f, 0.15f,
            5.25f, -5.5f, -0.15f,
            -0.2f, -5.5f, -0.15f,//4

            0.2f, -5f, -0.15f,//5
            0.2f, -5f, 0.15f,
            5.25f, -5f, 0.15f,
            5.25f, -5f, -0.15f,
            0.2f, -5f, -0.15f,//9

            0.2f, 0f, 0.15f, //10
            0.2f, 0f, -0.15f,
            0.2f, -5f, -0.15f,
            0.2f, -5f, 0.15f,
            0.2f, 0f, 0.15f,

            -0.2f, 0f, 0.15f, //15
            -0.2f, 0f, -0.15f,
            -0.2f, -5.5f, -0.15f,
            -0.2f, -5.5f, 0.15f,
            -0.2f, 0f, 0.15f,

            -0.2f, -5.5f, 0.15f, //20
            -0.2f, -5.5f, -0.15f,

            0.2f, -5f, -0.15f, //22
            0.2f, -5f, 0.15f,


    };
    private static final float[] VERTEX_DATA_L = {
            0.2f, -5.5f, -0.15f,
            0.2f, -5.5f, 0.15f,
            -5.25f, -5.5f, 0.15f,
            -5.25f, -5.5f, -0.15f,
            0.2f, -5.5f, -0.15f,

            -0.2f, -5f, -0.15f,
            -0.2f, -5f, 0.15f,
            -5.25f, -5f, 0.15f,
            -5.25f, -5f, -0.15f,
            -0.2f, -5f, -0.15f,

            0.2f, 0f, 0.15f,
            0.2f, 0f, -0.15f,
            0.2f, -5.5f, -0.15f,
            0.2f, -5.5f, 0.15f,
            0.2f, 0f, 0.15f,

            -0.2f, 0f, 0.15f,
            -0.2f, 0f, -0.15f,
            -0.2f, -5f, -0.15f,
            -0.2f, -5f, 0.15f,
            -0.2f, 0f, 0.15f,

            0.2f, -5.5f, 0.15f, //20
            0.2f, -5.5f, -0.15f,

            -0.2f, -5f, 0.15f, //20
            -0.2f, -5f, -0.15f,
    };

    private VertexArray vertexArray;

    public int RandomDir(){
        DIR = (int)(Math.random() * 100f) % 2 + 1;
        Log.w("Map", "DIR: " + String.valueOf(DIR));
        return DIR;
    }

    public Map(){
        vertexArray = new VertexArray(DIR == 1 ? VERTEX_DATA_R : VERTEX_DATA_L);
    }

    public void bindData(ColorShaderProgram colorShaderProgram){
        vertexArray = new VertexArray(DIR == 1 ? VERTEX_DATA_R : VERTEX_DATA_L);
        vertexArray.setVertexAttribPointer(0, colorShaderProgram.getPositionAttributeLocation(),
                POSITION_COMPONENT_COUNT,
                STRIDE);
    }
    public void drawLine(){

        glLineWidth(10f);
       // glDrawArrays(GL_LINES, 20, 2);
        //glDrawArrays(GL_LINES, 22, 2);

        for(int i = 0 ; i < 4; i++){
            glDrawArrays(GL_LINES, i, 2);
            glDrawArrays(GL_LINES, i+5, 2);
            glDrawArrays(GL_LINES, i+10, 2);
            glDrawArrays(GL_LINES, i+15, 2);
        }/*
        if( n >= 5.25)
            for(int i = 0 ; i < 4; i++){
                glDrawArrays(GL_LINES, i, 2);
                glDrawArrays(GL_LINES, i+5, 2);
             }*/
    }

    public void drawLine_v(){
        glLineWidth(5f);
        glDrawArrays(GL_LINES, 20, 2);
        glDrawArrays(GL_LINES, 22, 2);
    }

    public void drawTriangleFan(){
        glDrawArrays(GL_TRIANGLE_FAN, 0, 3);
        glDrawArrays(GL_TRIANGLE_FAN, 2, 3);
        glDrawArrays(GL_TRIANGLE_FAN, 5, 3);
        glDrawArrays(GL_TRIANGLE_FAN, 7, 3);
        glDrawArrays(GL_TRIANGLE_FAN, 10, 3);
        glDrawArrays(GL_TRIANGLE_FAN, 12, 3);
        glDrawArrays(GL_TRIANGLE_FAN, 15, 3);
        glDrawArrays(GL_TRIANGLE_FAN, 17, 3);
    }
}
