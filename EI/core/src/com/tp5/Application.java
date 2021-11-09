package com.tp5;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.math.Intersector;

public class Application extends ApplicationAdapter {
    private FitViewport viewport;
    private PerspectiveCamera camera;
    private Pixmap pixels;
    private Texture textureWithPixels;
    private SpriteBatch spriteBatch;
    private Vector2 currentScreen;
    private Vector3 currentScene;
    private Vector3 tmpVector3;
    public Scene scene = new Scene();

    @Override
    public void create() {
        // Get screen dimensions, in pixels :
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();

        //Scene scene = new Scene();
        Ray ray = new Ray();

        // Create a camera with perspective view :
        camera = new PerspectiveCamera(50.0f, screenWidth, screenHeight);
        camera.position.set(0f, 0f, -10f);
        camera.lookAt(0, 0, 0);
        camera.near = 1f;
        camera.far = 500f;
        camera.update();

        // Create a viewport to convert coords of screen space into coords of scene
        // space.
        viewport = new FitViewport(screenWidth, screenHeight, camera);

        // Create an array of pixels, initialized with grey color :
        pixels = Pixmap.createFromFrameBuffer(0, 0, screenWidth, screenHeight);
        for (int y = 0; y < screenHeight; y++) {
            for (int x = 0; x < screenWidth; x++) {
                pixels.setColor(0.1f, 0.1f, 0.1f, 1f);
                pixels.drawPixel(x, y);
            }
        }

        //for each pixel we search for an intersection
        for(int i = 0; i < pixels.getWidth(); i++)
        {
            //we put all calculs here because the light will move here, in function of i

            // Calculate the minimal and maximal distance between the sphere and the Light
            //using the function dst in the Vector3 class to have the distance between the light and the center of the sphere
            float centerDistance = Vector3.dst(scene.light.position.x, scene.light.position.y, scene.light.position.z, scene.sphere.position.x, scene.sphere.position.y, scene.sphere.position.z);
            
            //We add the center of the sphere to have the maximal distance and we retire it to have the minimal distance with the light
            float maximalDistance = centerDistance + scene.sphere.raySphere;
            float minimalDistance = centerDistance - scene.sphere.raySphere;

            //Calculate the new position for the light... but it fails....
            //scene.light.position.x = scene.light.position.x + 10;

            for(int j = 0; j < pixels.getHeight(); j++)
            {
                Vector3 origin= camera.position;

                tmpVector3 = new Vector3();
                tmpVector3.set(i, j, 0);
                Vector3 positionPixelScreen = camera.unproject(tmpVector3);
                Vector3 direction = positionPixelScreen.sub(origin);

                ray.set(origin, direction);

                boolean verif;
                Vector3 intersection = new Vector3(0,0,0);
                //if there is an intersection then the value of intersection will take the value of the coordonate intersection
                verif = Intersector.intersectRaySphere(ray, scene.sphere.position, scene.sphere.raySphere, intersection);
                if(verif)
                {
                    //Calculate the distance between the light and the intersection
                    float distanceLightIntersection = Vector3.dst(scene.light.position.x, scene.light.position.y, scene.light.position.z, intersection.x, intersection.y, intersection.z);
                    float ratio;
                    if(distanceLightIntersection == maximalDistance)
                    {
                        ratio = 0;
                    }

                    else if(distanceLightIntersection == minimalDistance)
                    {
                        ratio = 1;
                    }

                    else
                    {
                        //with Start = 0, End = 1, Max = maximalDistance and Min = minimalDistance
                        float v = 1/(maximalDistance - minimalDistance);
                        float a = (1 + minimalDistance * v)/maximalDistance;
                        float b = -minimalDistance * v;
                        ratio = a * distanceLightIntersection + b;

                    }

                    //We applicate the ratio on the color
                    pixels.setColor(scene.sphere.color.x*ratio, scene.sphere.color.y*ratio, scene.sphere.color.z*ratio, 1f);
                    pixels.drawPixel(i, j);
                }

            }
        }

        // Add pixels in a Texture in order to render them :
        spriteBatch = new SpriteBatch();
        textureWithPixels = new Texture(pixels);

        // Initialize coords of the first pixel, in screen space :
        currentScreen = new Vector2(0, 0);

        // Others initializations :
        currentScene = new Vector3();
        tmpVector3 = new Vector3();
    }

    @Override
    public void render() {
        // If "ctrl + s" is pressed :
        if (Gdx.input.isKeyPressed(Keys.CONTROL_LEFT) && Gdx.input.isKeyPressed(Keys.S)) {
            // Save the pixels into a png file :
            savePixelsInPngFile();
        }

        // If "escape" is pressed :
        if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
            // Close th application :
            Gdx.app.exit();
        }

        // Reset the screen buffer colors :
        ScreenUtils.clear(0, 0, 0, 1);

        //move the light but it didn't work...
        //scene.light.position.x = scene.light.position.x + 10;

        // Process pixels color :
        processPixel();

        textureWithPixels.draw(pixels, 0, 0);

        // Render the texture with pixels :
        spriteBatch.begin();
        spriteBatch.draw(textureWithPixels, 0, 0);
        spriteBatch.end();
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        textureWithPixels.dispose();
        pixels.dispose();
    }

    /**
     * Compute the color of each screen pixels and store the results in the pixels
     * map.
     */
    private boolean processPixel() {
        boolean isOk = true;

        // Get color of current pixel :
        Vector3 color = getColor((int) currentScreen.x, (int) currentScreen.y);

        // Save color into pixels map :
        pixels.setColor(color.x, color.y, color.z, 1f);
        pixels.drawPixel((int) currentScreen.x, (int) currentScreen.y);

        return isOk;
    }

    /**
     * Wrire pixels in the png file "core/assets/render.png". If the file already
     * exists it will be overrided.
     */
    private boolean savePixelsInPngFile() {
        boolean isOk = true;

        // Create file :
        FileHandle file = Gdx.files.local("render.png");

        // Write pixels in file :
        PixmapIO.writePNG(file, pixels);

        return isOk;
    }

    /**
     * Return the color processed with path tracing and Phong method for the given pixel.
     */
    private Vector3 getColor(int xScreen, int yScreen)
    {
        Vector3 color = new Vector3(1.f, 0f, 0f); 

        // Get coords of current pixel, in scene space :
        tmpVector3.set(xScreen, yScreen, 0);
        currentScene = viewport.unproject(tmpVector3);

        // To be continued ...

        return color;
    }
}