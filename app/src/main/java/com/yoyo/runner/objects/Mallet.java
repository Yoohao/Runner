package com.yoyo.runner.objects;

import com.yoyo.runner.data.VertexArray;
import com.yoyo.runner.programs.ColorShaderProgram;
import com.yoyo.runner.util.Geometry;

import java.util.List;

import static android.opengl.GLES20.GL_POINTS;
import static android.opengl.GLES20.glDrawArrays;
import static com.yoyo.runner.Constants.BYTES_PER_FLOAT;

/**
 * Created by Yo on 2016/11/27.
 */

public class Mallet {
    private static final int POSITION_COMPONENT_COUNT = 3;

    public final float radius;
    public final float height;

    private  static final float[] VERTEX_DATA = {
            0f, -0.4f, 0f, 0f, 1f,
            0f, 0.4f, 1f, 0f, 0f
    };
    private final VertexArray vertexArray;
    private final List<ObjectBuilder.DrawCommand> drawList;

    /*
    public Mallet(){
        vertexArray = new VertexArray(VERTEX_DATA);
    }*/

    public Mallet(float radius, float height, int numPointsAroundMallet){
        ObjectBuilder.GeneratedData generatedData = ObjectBuilder.createMallet(new Geometry.Point(0f, 0f, 0f)
                , radius, height, numPointsAroundMallet);

        this.radius = radius;
        this.height = height;

        vertexArray = new VertexArray(generatedData.vertexData);
        drawList = generatedData.drawList;
    }

    public void bindData(ColorShaderProgram colorShaderProgram){
        /*
        vertexArray.setVertexAttribPointer(0, colorShaderProgram.getPositionAttributeLocation(),
                POSITION_COMPONENT_COUNT,
                STRIDE);
        vertexArray.setVertexAttribPointer(POSITION_COMPONENT_COUNT, colorShaderProgram.getColorAttributeLocation(),
                COLOR_COMPONENT_COUNT, STRIDE);*/

        vertexArray.setVertexAttribPointer(0, colorShaderProgram.getPositionAttributeLocation(),
                POSITION_COMPONENT_COUNT, 0);
    }

    public void draw(){
        //glDrawArrays(GL_POINTS, 0, 2);
        for(ObjectBuilder.DrawCommand drawCommand : drawList){
            drawCommand.draw();
        }
    }
}