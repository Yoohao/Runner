package com.yoyo.runner;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.yoyo.runner.objects.Axis;
import com.yoyo.runner.objects.Cube;
import com.yoyo.runner.objects.Mallet;
import com.yoyo.runner.objects.Map;
import com.yoyo.runner.objects.Puck;
import com.yoyo.runner.objects.Table;
import com.yoyo.runner.programs.ColorShaderProgram;
import com.yoyo.runner.programs.TextureShaderProgram;
import com.yoyo.runner.util.Geometry;
import com.yoyo.runner.util.MatrixHelper;
import com.yoyo.runner.util.TextureHelper;
import com.yoyo.runner.util.Geometry.Point;
import com.yoyo.runner.util.Geometry.Ray;
import com.yoyo.runner.util.Geometry.Sphere;
import com.yoyo.runner.util.Geometry.Plane;
import com.yoyo.runner.util.Geometry.Vector;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glViewport;
import static android.opengl.Matrix.invertM;
import static android.opengl.Matrix.multiplyMM;
import static android.opengl.Matrix.multiplyMV;
import static android.opengl.Matrix.orthoM;
import static android.opengl.Matrix.rotateM;
import static android.opengl.Matrix.setIdentityM;
import static android.opengl.Matrix.setLookAtM;
import static android.opengl.Matrix.translateM;

/**
 * Created by Yo on 2016/11/21.
 */

public class RunnerRenderer implements GLSurfaceView.Renderer {

    public float RATIO;

    //private final FloatBuffer vertexData;
    private final Context context;

    private final float[] projectionMatrix = new float[16];
    private final float[] modelMatrix = new float[16];
    private final float[] viewMatrix = new float[16];
    private final float[] viewProjectionMatrix = new float[16];
    private final float[] modelViewProjectionMatrix = new float[16];
    private final float[] invertedViewProjectionMatrix = new float[16];

    private static Handler handler = new Handler();

    private Map map;
    private Axis axis;



    private float x = 0;
    private float y = 0;

    private float r, g, b;

    private float Rotate = 0;
    private int deltaZ = 0;

    private boolean GAME_OVER = false;
    private boolean SHOW = false;

    private boolean MOVE = false;
    private boolean TURN = false;
    private int DIR = 0;
    private int count = 0;
    private float SPEED = 0.05f;

    private TextureShaderProgram textureProgram;
    private ColorShaderProgram colorProgram;

    private int texture;

    private void setBlack() {
        r = g = b = 0f;
    }

    private void setWhite() {
        r = g = b = 1f;
    }

    private void setFanColor() {
        r = 62f / 255f;
        g = 166f / 255f;
        b = 248f / 255f;
    }

    private void resetAll(){
        GAME_OVER = false;
        SHOW = false;
        x = y = Rotate = 0f;
        MOVE = false;
        TURN = false;
        DIR = map.RandomDir();
        count = deltaZ = 0;
        SPEED = 0.05f;

        setLookAtM(viewMatrix, 0, 0f, 0f, 0.25f, 0f, 0f, 0f, 0f, 1f, 0f);
        rotateM(viewMatrix, 0, 90f, 1f, 0f, 0f);
    }

    public RunnerRenderer(Context context){
        this.context = context;
        setBlack();
    }

    public void handleTouchPress(float normalizedX, float normalizedY){
        if(normalizedY > 0){
            if(normalizedX > 0 && !GAME_OVER ) {
                if(!MOVE && count == 0){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "Level: 1", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                MOVE = !MOVE;
            }
            else if(normalizedX < 0){
                if(GAME_OVER){
                    resetAll();
                }
            }
        }
        else if (MOVE){
            if (y * SPEED >= 4.65f && y * SPEED < 6f && !TURN) {
                if(normalizedX > 0 && DIR == 1){
                    Rotate = 10f;
                    deltaZ = 90;
                    TURN = true;
                    y = 5.25f / SPEED;
                }
                else if(normalizedX < 0 && DIR == 2){
                    Rotate = -10f;
                    deltaZ = 270;
                    TURN = true;
                    y = 5.25f / SPEED;
                }
                else {  //Wrong direction
                    GAME_OVER = true;
                    MOVE = false;
                }
            }
            else { // turn around at wrong place or turn around twice
                GAME_OVER = true;
                MOVE = false;
            }
        }
    }

    public void handleTouchDrag(float normalizedX, float normalizedY){
    }



    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        glClearColor(220f / 255f, 220f / 255f, 220f / 255f, 0.5f);

        map = new Map();
        DIR = map.RandomDir();
        axis = new Axis();

        textureProgram = new TextureShaderProgram(context);
        colorProgram = new ColorShaderProgram(context);

        texture = TextureHelper.loadTexture(context, R.drawable.a);
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        glViewport(0, 0, width, height);
        /*
        RATIO = width > height ? (float) width / (float) height : (float) height / (float) width;

        if (width > height)
            orthoM(projectionMatrix, 0, -RATIO, RATIO, -1f, 1f, -1f, 1f);
        else
            orthoM(projectionMatrix, 0, -1f, 1f, -RATIO, RATIO, -1f, 1f);*/

        MatrixHelper.perspectiveM(projectionMatrix, 90, (float)width / (float)height, 0f, 10f);
        setLookAtM(viewMatrix, 0, 0f, 0f, 0.25f, 0f, 0f, 0f, 0f, 1f, 0f);
        rotateM(viewMatrix, 0, 90f, 1f, 0f, 0f);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        // Clear the rendering surface.
        glClear(GL_COLOR_BUFFER_BIT);

        if( y * SPEED >= 6f){  // out of range
            GAME_OVER = true;
            MOVE = false;
        }

        if((Math.abs(x)) * SPEED > 0f /*&& (Math.abs(x)) * SPEED >= 6.2f*/){
            TURN = false;
            deltaZ = 0;

            x = 0f;
            if(count % 5 != 0){
                y = (int)(Math.random() * 1000f) % (int)( count / 5 <= 5 ? 4f / SPEED : 2.5 / SPEED);
                Log.w("RandomY", String.valueOf(y) + " " + String.valueOf(y * SPEED) + " " + String.valueOf(SPEED) );
            }
            else y = 0f;
            setLookAtM(viewMatrix, 0, 0f, 0f, 0.01f, 0f, 0f, 0f, 0f, 1f, 0f);
            rotateM(viewMatrix, 0, 90f, 1f, 0f, 0f);

            DIR = map.RandomDir();

            count += 1;

            if(count % 5 == 0 && SPEED < 0.25) {
                SPEED += 0.025;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "Level: " + String.valueOf(count / 5 + 1), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }


        if(Rotate != 0f){
            rotateM(viewMatrix, 0, (90f/10f) * (Rotate > 0 ? -1f : 1f), 0f, 0f, 1f);
            Rotate += Rotate > 0 ? -1f : 1f;
        }
        // Update the viewProjection matrix, and create an inverted matrix for
        // touch picking.
        multiplyMM(viewProjectionMatrix, 0, projectionMatrix, 0,
                viewMatrix, 0);
        invertM(invertedViewProjectionMatrix, 0, viewProjectionMatrix, 0);

        positionTableInScene();
        colorProgram.useProgram();
        map.bindData(colorProgram);


        if(MOVE) setBlack();
        else setWhite();
        colorProgram.setUniforms(modelViewProjectionMatrix, r, g, b);
        map.drawLine();

        setFanColor();
        colorProgram.setUniforms(modelViewProjectionMatrix, r, g, b);
        map.drawTriangleFan();

        if(MOVE) setBlack();
        else setWhite();
        colorProgram.setUniforms(modelViewProjectionMatrix, r, g, b);
        map.drawLine_v();

        if(GAME_OVER && !SHOW){
            SHOW = !SHOW;
            handler.post(new Runnable() {
                @Override
                public void run() {
                    if(GAME_OVER)
                        Toast.makeText(context, "Game Over! Please try again!", Toast.LENGTH_LONG).show();
                }
            });
        }

        /*
        colorProgram.setUniforms(modelViewProjectionMatrix, 1f, 0f, 0f);
        axis.bindData(colorProgram);
        axis.drawX();
        colorProgram.setUniforms(modelViewProjectionMatrix, 0f, 0f, 1f);
        axis.drawY();
        colorProgram.setUniforms(modelViewProjectionMatrix, 0f, 1f, 0f);
        axis.drawZ();
        */
    }


    private void positionTableInScene() {
        // The table is defined in terms of X & Y coordinates, so we rotate it
        // 90 degrees to lie flat on the XZ plane.
        setIdentityM(modelMatrix, 0);

        if(MOVE && Rotate == 0){
            if(deltaZ == 0 || deltaZ == 180){
                if(!TURN)
                    y += 1f;
            }
            else if (deltaZ == 90 || deltaZ == 270)
                x += deltaZ == 90 ? -1f : 1f;

            //Log.w("Renderer", String.valueOf(DIR) + " " + String.valueOf(y * SPEED) + " " +String.valueOf(y));
        }

        translateM(modelMatrix, 0, x * SPEED, y * SPEED >= 6 ? 5f : y * SPEED, 0f);

        multiplyMM(modelViewProjectionMatrix, 0, viewProjectionMatrix,
                0, modelMatrix, 0);
    }

    // The mallets and the puck are positioned on the same plane as the table.
    private void positionObjectInScene(float x, float y, float z) {
        setIdentityM(modelMatrix, 0);
        translateM(modelMatrix, 0, x, y, z);
        multiplyMM(modelViewProjectionMatrix, 0, viewProjectionMatrix,
                0, modelMatrix, 0);
    }
}