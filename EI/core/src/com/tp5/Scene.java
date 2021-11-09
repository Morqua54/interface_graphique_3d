package com.tp5;

//import java.util.ArrayList;

import com.badlogic.gdx.math.Vector3;

public class Scene {
    //public ArrayList<Sphere3D> listSphere;
    public Sphere3D sphere;
    public Light light;

    Scene()
    {
        //listSphere = new ArrayList<Sphere3D>();
        //listSphere.add(new Sphere3D("sphere1", 1, 0, 0, 0, new Vector3 (2,1,10), 4, new Vector3(0,1,1)));
        //listSphere.add(new Sphere3D("sphere2", 1, 0, 0, 0, new Vector3 (-4,1,8), 4, new Vector3(0.6f,0.2f,0.1f)));

        sphere = new Sphere3D("sphere", 1, 0, 0, 0, new Vector3 (1,1,10), 4, new Vector3(0,0,1));
        light = new Light("lampe", new Vector3(0,1,6));
    }
}
