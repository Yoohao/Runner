package com.yoyo.runner.objects;

import com.yoyo.runner.data.VertexArray;
import com.yoyo.runner.programs.ColorShaderProgram;
import com.yoyo.runner.util.Geometry;

import java.util.List;
import com.yoyo.runner.objects.ObjectBuilder.GeneratedData;
import com.yoyo.runner.objects.ObjectBuilder.DrawCommand;
/**
 * Created by Yo on 2016/11/28.
 */

public class Puck {
    private static final int POSITION_COMPONENT_COUNT = 3;

    public final float radius, height;

    private final VertexArray vertexArray;
    private final List<DrawCommand> drawList;

    public Puck(float radius, float height, int numPointsAroundPuck) {
        GeneratedData generatedData = ObjectBuilder.createPuck(new Geometry.Cylinder(
                new Geometry.Point(0f, 0f, 0f), radius, height), numPointsAroundPuck);
        this.radius = radius;
        this.height = height;

        vertexArray = new VertexArray(generatedData.vertexData);
        drawList = generatedData.drawList;
    }

    public void bindData(ColorShaderProgram colorProgram) {
        vertexArray.setVertexAttribPointer(0,
                colorProgram.getPositionAttributeLocation(),
                POSITION_COMPONENT_COUNT, 0);
    }
    public void draw() {
        for (DrawCommand drawCommand : drawList) {
            drawCommand.draw();
        }
    }
}
